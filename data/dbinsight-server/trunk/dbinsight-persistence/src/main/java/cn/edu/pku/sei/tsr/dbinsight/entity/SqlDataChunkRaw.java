package cn.edu.pku.sei.tsr.dbinsight.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Icing on 2016/11/12.
 */
//@Entity
@DiscriminatorValue("sql")
public class SqlDataChunkRaw extends AbstractDataChunkRaw {

    @Column(nullable = false)
    private String tableName;

    @Column(nullable = false)
    private String idColumnName;

    @Column(nullable = false)
    private String featureColumnName;

    @Column(nullable = false)
    private String valueColumnName;

    public String getTableName() {
        return tableName;
    }

    public SqlDataChunkRaw setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String getIdColumnName() {
        return idColumnName;
    }

    public SqlDataChunkRaw setIdColumnName(String idColumnName) {
        this.idColumnName = idColumnName;
        return this;
    }

    public String getFeatureColumnName() {
        return featureColumnName;
    }

    public SqlDataChunkRaw setFeatureColumnName(String featureColumnName) {
        this.featureColumnName = featureColumnName;
        return this;
    }

    public String getValueColumnName() {
        return valueColumnName;
    }

    public SqlDataChunkRaw setValueColumnName(String valueColumnName) {
        this.valueColumnName = valueColumnName;
        return this;
    }
}
