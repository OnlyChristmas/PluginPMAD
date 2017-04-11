package cn.edu.pku.sei.tsr.dbinsight.data.importer;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.Resource;
import cn.edu.pku.sei.tsr.dbinsight.commons.resource.task.TaskRuntime;
import org.apache.tomcat.jdbc.pool.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 * Created by 唐爽 on 2017/3/2.
 */
public class SqlTopicsDCImportRT implements TaskRuntime {

    private List<Resource> outputs;
    private DataSource dataSource;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private int runtimeCount;
    private Map<String, TopicsDataChunkHelper> helperMap;

    public DataSource getDataSource() {
        return dataSource;
    }

    public SqlTopicsDCImportRT setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    public Connection getConnection() {
        return connection;
    }

    public SqlTopicsDCImportRT setConnection(Connection connection) {
        this.connection = connection;
        return this;
    }

    public Statement getStatement() {
        return statement;
    }

    public SqlTopicsDCImportRT setStatement(Statement statement) {
        this.statement = statement;
        return this;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public SqlTopicsDCImportRT setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
        return this;
    }

    @Override
    public List<Resource> getOutputs() {
        return outputs;
    }

    @Override
    public SqlTopicsDCImportRT setOutputs(List<Resource> outputs) {
        this.outputs = outputs;
        return this;
    }

    public int getRuntimeCount() {
        return runtimeCount;
    }

    public SqlTopicsDCImportRT setRuntimeCount(int runtimeCount) {
        this.runtimeCount = runtimeCount;
        return this;
    }

    public Map<String, TopicsDataChunkHelper> getHelperMap() {
        return helperMap;
    }

    public SqlTopicsDCImportRT setHelperMap(Map<String, TopicsDataChunkHelper> helperMap) {
        this.helperMap = helperMap;
        return this;
    }
}
