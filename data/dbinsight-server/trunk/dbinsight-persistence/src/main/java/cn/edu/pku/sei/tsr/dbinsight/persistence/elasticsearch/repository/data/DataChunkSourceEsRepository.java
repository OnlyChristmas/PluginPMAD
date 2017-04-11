package cn.edu.pku.sei.tsr.dbinsight.persistence.elasticsearch.repository.data;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.ResourceState;
import cn.edu.pku.sei.tsr.dbinsight.commons.resource.data.DataChunkSource;
import cn.edu.pku.sei.tsr.dbinsight.essentials.api.repository.data.DataChunkSourceRepository;
import cn.edu.pku.sei.tsr.dbinsight.persistence.elasticsearch.document.data.DataChunkSourceEs;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Created by Icing on 2017/2/28.
 */
@Repository
@ConditionalOnProperty(name = "dbinsight.persistence.type", havingValue = "elasticsearch", matchIfMissing = true)
public interface DataChunkSourceEsRepository extends DataChunkSourceRepository<DataChunkSourceEs>, ElasticsearchRepository<DataChunkSourceEs, String> {
    @Override
    default DataChunkSourceEs newOne() {
        return new DataChunkSourceEs()
                .setId(UUID.randomUUID().toString())
                .setState(ResourceState.Created)
                .setType(DataChunkSource.class.getSimpleName());
    }
}
