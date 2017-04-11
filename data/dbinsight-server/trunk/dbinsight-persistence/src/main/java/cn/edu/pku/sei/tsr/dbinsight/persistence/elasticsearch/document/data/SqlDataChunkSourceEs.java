package cn.edu.pku.sei.tsr.dbinsight.persistence.elasticsearch.document.data;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.data.SqlDataChunkSource;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by Icing on 2017/2/28.
 */
@Document(indexName = "data_chunk_source", type = "default", replicas = 0, refreshInterval = "-1")
public class SqlDataChunkSourceEs implements SqlDataChunkSource {
    @Id
    private String id;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String state;

    private String driverClassName;
    private String url;
    private String username;
    private String password;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public SqlDataChunkSourceEs setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public SqlDataChunkSourceEs setState(String state) {
        this.state = state;
        return this;
    }

    @Override
    public String getDriverClassName() {
        return driverClassName;
    }

    @Override
    public SqlDataChunkSourceEs setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
        return this;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public SqlDataChunkSourceEs setUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public SqlDataChunkSourceEs setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public SqlDataChunkSourceEs setPassword(String password) {
        this.password = password;
        return this;
    }
}
