package cn.edu.pku.sei.tsr.dbinsight.persistence.elasticsearch.repository.data;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.ResourceState;
import cn.edu.pku.sei.tsr.dbinsight.essentials.api.repository.data.SqlDataChunkSourceRepository;
import cn.edu.pku.sei.tsr.dbinsight.persistence.elasticsearch.document.data.SqlDataChunkSourceEs;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Created by Icing on 2017/2/28.
 */
@Repository
@ConditionalOnProperty(name = "dbinsight.persistence.type", havingValue = "elasticsearch", matchIfMissing = true)
public interface SqlDataChunkSourceEsRepository extends SqlDataChunkSourceRepository<SqlDataChunkSourceEs>, ElasticsearchRepository<SqlDataChunkSourceEs, String> {
    @Override
    default SqlDataChunkSourceEs newOne() {
        return new SqlDataChunkSourceEs().setId(UUID.randomUUID().toString()).setState(ResourceState.Created);
    }
}
