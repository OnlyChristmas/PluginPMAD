package cn.edu.pku.sei.tsr.dbinsight.persistence.oss;

import cn.edu.pku.sei.tsr.dbinsight.essentials.api.repository.OssRepository;
import cn.edu.pku.sei.tsr.dbinsight.persistence.oss.repository.FileOssRepository;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Icing on 2016/12/23.
 */
@Configuration
@ConditionalOnMissingBean(OssRepository.class)
@ConfigurationProperties(prefix = "oss.file")
public class FileOssRepositoryAutoConfiguration {
    @NotBlank
    private String rootDir = "oss";
    private boolean useHash = false;

    @Bean
    public FileOssRepository fileOssRepository() {
        return new FileOssRepository(rootDir, useHash);
    }
}
