package cn.edu.pku.sei.tsr.dbinsight.persistence;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Icing on 2017/2/27.
 */
@Configuration
@ConfigurationProperties(prefix = "dbinsight.persistence")
public class PersistenceAutoConfiguration {
    private String type = "elasticsearch";

    @Configuration
    @ConditionalOnProperty(name = "dbinsight.persistence.type", havingValue = "elasticsearch", matchIfMissing = true)
    @Import({
            ElasticsearchAutoConfiguration.class,
            ElasticsearchDataAutoConfiguration.class,
    })
    @EnableElasticsearchRepositories("cn.edu.pku.sei.tsr.dbinsight.persistence.elasticsearch")
    public class EnableElasticsearch {}

    @Configuration
    @ConditionalOnProperty(name = "dbinsight.persistence.type", havingValue = "jpa")
    @Import({
            JpaRepositoriesAutoConfiguration.class,
            HibernateJpaAutoConfiguration.class
    })
    @EnableJpaRepositories("cn.edu.pku.sei.tsr.dbinsight.persistence.jpa")
    public class EnableJpa {}

}
