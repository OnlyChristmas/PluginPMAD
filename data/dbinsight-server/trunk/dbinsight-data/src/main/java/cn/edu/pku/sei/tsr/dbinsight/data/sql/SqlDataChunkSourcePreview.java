package cn.edu.pku.sei.tsr.dbinsight.data.sql;

import java.util.List;

/**
 * Created by Shawn on 2017/3/1.
 */
public class SqlDataChunkSourcePreview {

    private String id;
    private String type;
    private String state;

    private List<TablePreview> tablePreviews;

    public String getId() {
        return id;
    }

    public SqlDataChunkSourcePreview setId(String id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public SqlDataChunkSourcePreview setType(String type) {
        this.type = type;
        return this;
    }

    public String getState() {
        return state;
    }

    public SqlDataChunkSourcePreview setState(String state) {
        this.state = state;
        return this;
    }

    public List<TablePreview> getTablePreviews() {
        return tablePreviews;
    }

    public SqlDataChunkSourcePreview setTablePreviews(List<TablePreview> tablePreviews) {
        this.tablePreviews = tablePreviews;
        return this;
    }

    public static class TablePreview {

        private String tableName;
        private String remarks;
        private List<String> columnNames;
        private List<List<String>> sampleRows;

        public String getTableName() {
            return tableName;
        }

        public TablePreview setTableName(String tableName) {
            this.tableName = tableName;
            return this;
        }

        public List<String> getColumnNames() {
            return columnNames;
        }

        public TablePreview setColumnNames(List<String> columnNames) {
            this.columnNames = columnNames;
            return this;
        }

        public List<List<String>> getSampleRows() {
            return sampleRows;
        }

        public TablePreview setSampleRows(List<List<String>> sampleRows) {
            this.sampleRows = sampleRows;
            return this;
        }

        public String getRemarks() {
            return remarks;
        }

        public TablePreview setRemarks(String remarks) {
            this.remarks = remarks;
            return this;
        }
    }
}
