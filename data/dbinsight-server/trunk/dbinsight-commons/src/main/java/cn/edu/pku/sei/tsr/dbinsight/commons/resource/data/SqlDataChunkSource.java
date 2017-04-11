package cn.edu.pku.sei.tsr.dbinsight.commons.resource.data;

/**
 * Created by Icing on 2017/2/27.
 */
public interface SqlDataChunkSource extends DataChunkSource {

    String getDriverClassName();

    SqlDataChunkSource setDriverClassName(String jdbcDriver);

    String getUrl();

    SqlDataChunkSource setUrl(String url);

    String getUsername();

    SqlDataChunkSource setUsername(String username);

    String getPassword();

    SqlDataChunkSource setPassword(String password);

    @Override
    default String getType() {
        return SqlDataChunkSource.class.getSimpleName();
    }

}
