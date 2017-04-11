package cn.edu.pku.sei.tsr.dbinsight.data.sql;

import cn.edu.pku.sei.tsr.dbinsight.commons.lang.InternalServerErrorException;
import cn.edu.pku.sei.tsr.dbinsight.commons.lang.UnprocessableEntityException;
import cn.edu.pku.sei.tsr.dbinsight.commons.resource.Resource;
import cn.edu.pku.sei.tsr.dbinsight.commons.resource.data.SqlDataChunkSource;
import cn.edu.pku.sei.tsr.dbinsight.essentials.api.Previewer;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shawn on 2017/3/1.
 */
@Component
public class SqlDataChunkSourcePreviewer implements Previewer {

    @Override
    public String getReg() {
        return SqlDataChunkSource.class.getSimpleName();
    }

    @Override
    public Object getPreview(Resource resource) {
        if (!(resource instanceof SqlDataChunkSource))
            throw new UnprocessableEntityException("Unacceptable resource " + resource.getClass());

        try {
            return new SqlDataChunkSourcePreviewBuilder((SqlDataChunkSource) resource)
                    .build();
        } catch (SQLException e) {
            throw new InternalServerErrorException(e);
        }
    }

    private static class SqlDataChunkSourcePreviewBuilder {

        private static int fetchLimit = 10;

        private SqlDataChunkSource sqlDataChunkSource;
        private DataSource dataSource;
        private JdbcTemplate jdbcTemplate;
        private SqlDataChunkSourcePreview preview;

        public SqlDataChunkSourcePreviewBuilder(SqlDataChunkSource sqlDataChunkSource) {
            this.sqlDataChunkSource = sqlDataChunkSource;
        }

        public SqlDataChunkSourcePreview build() throws SQLException {
            init();
            try {
                DatabaseMetaData metaData = dataSource.getConnection().getMetaData();

                //查询所有数据表和视图信息
                String[] types = {"TABLE", "VIEW"};
                ResultSet rs = metaData.getTables(null, null, "%", types);
                while(rs.next()) {
                    SqlDataChunkSourcePreview.TablePreview tablePreview = new SqlDataChunkSourcePreview.TablePreview()
                            .setTableName(rs.getString("TABLE_NAME"))
                            .setRemarks(rs.getString("REMARKS"))
                            .setColumnNames(new ArrayList<>())
                            .setSampleRows(new ArrayList<>());
                    preview.getTablePreviews().add(tablePreview);
                }

                //查询每一张表的列信息以及取样
                for (SqlDataChunkSourcePreview.TablePreview tablePreview : preview.getTablePreviews()) {
                    rs = metaData.getColumns(null, null, tablePreview.getTableName(), "%");
                    while(rs.next())
                        tablePreview.getColumnNames().add(rs.getString("COLUMN_NAME"));

                    SqlRowSet rowSet = jdbcTemplate.queryForRowSet("select * from `" + tablePreview.getTableName() + "` limit " + fetchLimit);
                    while(rowSet.next()) {
                        List<String> sampleRow = new ArrayList<>();
                        for (String columnName : tablePreview.getColumnNames()) {
                            sampleRow.add(rowSet.getString(columnName));
                        }
                        tablePreview.getSampleRows().add(sampleRow);
                    }
                }
                return preview;
            } finally {
                close();
            }
        }

        private void init() {
            PoolProperties properties = new PoolProperties();
            properties.setUrl(sqlDataChunkSource.getUrl());
            properties.setDriverClassName(sqlDataChunkSource.getDriverClassName());
            properties.setUsername(sqlDataChunkSource.getUsername());
            properties.setPassword(sqlDataChunkSource.getPassword());
            properties.setJmxEnabled(false);
            properties.setTestOnBorrow(true);
            properties.setValidationQuery("select 1");
            properties.setTestOnReturn(false);
            properties.setValidationInterval(30_000);
            properties.setMaxActive(10);
            properties.setInitialSize(2);
            properties.setMinIdle(2);
            properties.setRemoveAbandoned(true);
            dataSource = new DataSource(properties);
            jdbcTemplate = new JdbcTemplate(dataSource);

            preview = new SqlDataChunkSourcePreview()
                    .setId(sqlDataChunkSource.getId())
                    .setState(sqlDataChunkSource.getState())
                    .setType(sqlDataChunkSource.getType())
                    .setTablePreviews(new ArrayList<>());
        }

        public void close() {
            dataSource.close();
        }
    }

}
