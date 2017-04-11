package cn.edu.pku.sei.tsr.dbinsight.persistence.elasticsearch.repository.data;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.ResourceState;
import cn.edu.pku.sei.tsr.dbinsight.essentials.api.repository.data.DataChunkMetaRepository;
import cn.edu.pku.sei.tsr.dbinsight.persistence.elasticsearch.document.data.DataChunkMetaEs;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Created by Icing on 2017/2/27.
 */
@Repository
@ConditionalOnProperty(name = "dbinsight.persistence.type", havingValue = "elasticsearch", matchIfMissing = true)
public interface DataChunkMetaEsRepository extends DataChunkMetaRepository<DataChunkMetaEs>, ElasticsearchRepository<DataChunkMetaEs, String> {
    @Override
    default DataChunkMetaEs newOne() {
        return new DataChunkMetaEs().setId(UUID.randomUUID().toString()).setState(ResourceState.Created);
    }
}
