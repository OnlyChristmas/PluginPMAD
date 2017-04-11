package cn.edu.pku.sei.tsr.dbinsight.persistence.elasticsearch.repository;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.ResourceState;
import cn.edu.pku.sei.tsr.dbinsight.essentials.api.repository.AppleRepository;
import cn.edu.pku.sei.tsr.dbinsight.persistence.elasticsearch.document.AppleEs;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Created by Icing on 2017/2/27.
 */
@Repository
@ConditionalOnProperty(name = "dbinsight.persistence.type", havingValue = "elasticsearch", matchIfMissing = true)
public interface AppleEsRepository extends AppleRepository<AppleEs>, ElasticsearchRepository<AppleEs, String> {
    @Override
    default AppleEs newOne() {
        return new AppleEs().setId(UUID.randomUUID().toString()).setState(ResourceState.Created);
    }
}
