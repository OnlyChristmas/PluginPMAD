package cn.edu.pku.sei.tsr.dbinsight.data.importer;

import cn.edu.pku.sei.tsr.dbinsight.commons.lang.InternalServerErrorException;
import cn.edu.pku.sei.tsr.dbinsight.commons.resource.data.SqlDataChunkSource;
import cn.edu.pku.sei.tsr.dbinsight.commons.resource.task.SqlTopicsDCImportContext;
import cn.edu.pku.sei.tsr.dbinsight.commons.resource.task.TaskContext;
import cn.edu.pku.sei.tsr.dbinsight.commons.resource.task.TaskRuntime;
import cn.edu.pku.sei.tsr.dbinsight.commons.resource.task.TaskStatus;
import cn.edu.pku.sei.tsr.dbinsight.commons.topics.resource.data.TopicsDataChunk;
import cn.edu.pku.sei.tsr.dbinsight.commons.util.JdbcUtil;
import cn.edu.pku.sei.tsr.dbinsight.commons.util.SpringUtil;
import cn.edu.pku.sei.tsr.dbinsight.essentials.api.service.ResourceService;
import cn.edu.pku.sei.tsr.dbinsight.essentials.api.transactor.data.DataChunkImporter;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 唐爽 on 2017/3/2.
 */
public class SqlTopicsDCImporter implements DataChunkImporter {

    @Autowired
    private ResourceService resourceService;

    private static final int stepLimit = 100;

    @Override
    public TaskRuntime init(TaskContext taskContext) {
        SqlTopicsDCImportContext context = (SqlTopicsDCImportContext) taskContext;
        SqlDataChunkSource dataChunkSource = resourceService
                .getResource(context.getDataChunkSourceRef(), SqlDataChunkSource.class);

        SqlTopicsDCImportRT runtime = new SqlTopicsDCImportRT()
                .setOutputs(new ArrayList<>())
                .setHelperMap(new HashMap<>());

        try {
            DataSource dataSource = JdbcUtil.getDataSource(dataChunkSource);
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection
                    .prepareStatement("SELECT "
                            + JdbcUtil.wrapNames(context.getWordColumnNames(), context.getIdColumnName())
                            + " FROM "
                            + JdbcUtil.wrapNames(context.getTableName()));
            ResultSet resultSet = statement.executeQuery();
            runtime.setDataSource(dataSource)
                    .setConnection(connection)
                    .setStatement(statement)

                    .setResultSet(resultSet)
                    .setRuntimeCount(0);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e);
        }
        //异步查询总行数
        getTotalCount(context, runtime);

        for (int i = 0; i < context.getWordColumnNames().size(); i++) {
            TopicsDataChunk topicsDataChunk = resourceService.newResource(TopicsDataChunk.class);
            topicsDataChunk.setDocuments(new ArrayList<>())
                    .setImportTaskRef(resourceService.getReference(context))
                    .setOutputOrdinal(i);
            runtime.getOutputs().add(topicsDataChunk);
            TopicsDataChunkHelper helper = SpringUtil.getBean(TopicsDataChunkHelper.class)
                    .setDataChunk(topicsDataChunk);
            runtime.getHelperMap().put(context.getWordColumnNames().get(i), helper);
        }

        return runtime;
    }

    @Override
    public TaskRuntime resume(TaskContext taskContext) {
        SqlTopicsDCImportContext context = (SqlTopicsDCImportContext) taskContext;
        SqlDataChunkSource dataChunkSource = resourceService
                .getResource(context.getDataChunkSourceRef(), SqlDataChunkSource.class);

        SqlTopicsDCImportRT runtime = new SqlTopicsDCImportRT()
                .setOutputs(resourceService.getResourceListByRef(context.getTopicsDataChunkRefs()))
                .setHelperMap(new HashMap<>());

        for (int i = 0; i < runtime.getOutputs().size(); i++) {
            TopicsDataChunkHelper helper = SpringUtil.getBean(TopicsDataChunkHelper.class)
                    .setDataChunk((TopicsDataChunk) runtime.getOutputs().get(i));
            runtime.getHelperMap().put(context.getWordColumnNames().get(i), helper);
        }

        try {
            DataSource dataSource = JdbcUtil.getDataSource(dataChunkSource);
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT "
                    + JdbcUtil.wrapNames(context.getWordColumnNames(), context.getIdColumnName())
                    + " FROM "
                    + JdbcUtil.wrapNames(context.getTableName())
                    , ResultSet.TYPE_SCROLL_INSENSITIVE
                    , ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery();

            //移动游标到已读的下一行
            resultSet.absolute(context.getReadCount() + 1);
            runtime.setDataSource(dataSource)
                    .setConnection(connection)
                    .setStatement(statement)
                    .setResultSet(resultSet)
                    .setRuntimeCount(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return runtime;
    }

    @Async
    private void getTotalCount(SqlTopicsDCImportContext context, SqlTopicsDCImportRT runtime) {
        try {
            Connection connection = runtime.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT (1) FROM "
                    + JdbcUtil.wrapNames(context.getTableName()));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int totalCount = resultSet.getInt(1);
                context.setTotalCount(totalCount);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            //ignore
        }
    }

    @Override
    public void step(TaskContext taskContext, TaskRuntime taskRuntime) {
        SqlTopicsDCImportContext context = (SqlTopicsDCImportContext) taskContext;
        SqlTopicsDCImportRT runtime = (SqlTopicsDCImportRT) taskRuntime;

        ResultSet resultSet = runtime.getResultSet();
        int stepCount = 0;

        try {
            while (stepCount < stepLimit && resultSet.next()) {
                for (String wordColumnName : context.getWordColumnNames()) {
                    runtime.getHelperMap().get(wordColumnName)
                            .putWord(resultSet.getString(context.getIdColumnName()),
                                    resultSet.getString(wordColumnName));
                }
                stepCount ++ ;
                runtime.setRuntimeCount(runtime.getRuntimeCount() + 1);
            }
            //表读完了
            if (stepCount < stepLimit)
                context.setStatus(TaskStatus.Ending);
        } catch (SQLException e) {
            throw new InternalServerErrorException(e);
        }
    }

    @Override
    public void stop(TaskContext taskContext, TaskRuntime taskRuntime) {
        SqlTopicsDCImportContext context = (SqlTopicsDCImportContext) taskContext;
        SqlTopicsDCImportRT runtime = (SqlTopicsDCImportRT) taskRuntime;

        try {
            runtime.getResultSet().close();
            runtime.getStatement().close();
            runtime.getConnection().close();
            runtime.getDataSource().close();
        } catch (SQLException e) {
            throw new InternalServerErrorException(e);
        }

        runtime.getHelperMap().values().forEach(TopicsDataChunkHelper::close);
        context.setReadCount(context.getReadCount() + runtime.getRuntimeCount());
    }
}
