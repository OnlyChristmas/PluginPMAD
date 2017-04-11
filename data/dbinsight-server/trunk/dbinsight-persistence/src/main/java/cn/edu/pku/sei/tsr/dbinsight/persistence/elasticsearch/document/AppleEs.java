package cn.edu.pku.sei.tsr.dbinsight.persistence.elasticsearch.document;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.Apple;
import cn.edu.pku.sei.tsr.dbinsight.commons.resource.ResourceState;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by Icing on 2017/2/27.
 */
@Document(indexName = "apple", replicas = 0, refreshInterval = "-1")
public class AppleEs implements Apple {

    @Id
    private String id;

    private String name;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String state;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public AppleEs setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public AppleEs setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public AppleEs setState(String state) {
        this.state = state;
        return this;
    }
}
