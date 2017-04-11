package cn.edu.pku.sei.tsr.dbinsight.persistence.elasticsearch.document.data;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.data.DataChunkMeta;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.util.List;

/**
 * Created by Icing on 2017/2/27.
 */
@Document(indexName = "data_chunk_meta", replicas = 0, refreshInterval = "-1")
public class DataChunkMetaEs implements DataChunkMeta {

    @Id
    private String id;
    private String name;
    private String description;

    @Field(type = FieldType.Nested)
    private List<String> tags;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String state;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public DataChunkMetaEs setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DataChunkMetaEs setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public DataChunkMetaEs setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public List<String> getTags() {
        return tags;
    }

    @Override
    public DataChunkMetaEs setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public DataChunkMetaEs setState(String state) {
        this.state = state;
        return this;
    }
}
