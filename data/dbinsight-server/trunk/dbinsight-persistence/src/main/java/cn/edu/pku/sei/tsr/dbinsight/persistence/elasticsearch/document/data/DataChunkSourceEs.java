package cn.edu.pku.sei.tsr.dbinsight.persistence.elasticsearch.document.data;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.data.DataChunkSource;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by Icing on 2017/2/28.
 */
@Document(indexName = "data_chunk_source", type = "default", replicas = 0, refreshInterval = "-1")
public class DataChunkSourceEs implements DataChunkSource {

    @Id
    private String id;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String state;

//    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String type;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public DataChunkSourceEs setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public DataChunkSourceEs setState(String state) {
        this.state = state;
        return this;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public DataChunkSourceEs setType(String type) {
        this.type = type;
        return this;
    }
}
