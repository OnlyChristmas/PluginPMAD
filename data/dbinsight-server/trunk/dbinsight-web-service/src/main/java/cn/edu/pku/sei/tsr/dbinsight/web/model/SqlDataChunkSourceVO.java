package cn.edu.pku.sei.tsr.dbinsight.web.model;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.data.SqlDataChunkSource;

/**
 * Created by 唐爽 on 2017/3/1.
 */
public class SqlDataChunkSourceVO implements SqlDataChunkSource {

    private String id;
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
    public SqlDataChunkSourceVO setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public SqlDataChunkSourceVO setState(String state) {
        this.state = state;
        return this;
    }

    @Override
    public String getDriverClassName() {
        return driverClassName;
    }

    @Override
    public SqlDataChunkSourceVO setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
        return this;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public SqlDataChunkSourceVO setUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public SqlDataChunkSourceVO setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public SqlDataChunkSourceVO setPassword(String password) {
        this.password = password;
        return this;
    }
}
