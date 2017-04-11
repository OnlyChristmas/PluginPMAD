package cn.edu.pku.sei.tsr.dbinsight.commons.util;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.data.SqlDataChunkSource;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Created by 唐爽 on 2017/3/2.
 */
public class JdbcUtil {
    public static JdbcTemplate getJdbcTemplate(SqlDataChunkSource sqlDataChunkSource) {
        return new JdbcTemplate(getDataSource(sqlDataChunkSource));
    }

    public static DataSource getDataSource(SqlDataChunkSource sqlDataChunkSource) {
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
        properties.setMaxIdle(5);
        properties.setRemoveAbandoned(true);
        return new DataSource(properties);
    }

    public static String wrapNames(List<String> names) {
        List<String> safeNames = names.stream().map(name -> name.replaceAll("`", ""))
                .collect(Collectors.toList());
        return "`" + String.join("`,`", safeNames) + "`";
    }

    public static String wrapNames(String...names) {
        return wrapNames(Arrays.asList(names));
    }

    public static String wrapNames(List<String> nameList, String...names) {
        List<String> tempList =  Arrays.asList(names);
        tempList.addAll(nameList);
        return wrapNames(tempList);
    }

}
