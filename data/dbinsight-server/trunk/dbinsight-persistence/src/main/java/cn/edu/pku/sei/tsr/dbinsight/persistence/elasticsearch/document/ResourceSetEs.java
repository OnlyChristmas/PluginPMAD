package cn.edu.pku.sei.tsr.dbinsight.persistence.elasticsearch.document;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.ResourceReference;
import cn.edu.pku.sei.tsr.dbinsight.commons.resource.ResourceSet;
import cn.edu.pku.sei.tsr.dbinsight.commons.resource.ResourceState;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Shawn on 2017/2/28.
 */
@Document(indexName = "resource_set", replicas = 0, refreshInterval = "-1")
public class ResourceSetEs implements ResourceSet {

    @Id
    private String id;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String state;

    @Field(type = FieldType.Nested)
    private List<ResourceReferenceEs> resourceRefs;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public ResourceSetEs setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public ResourceSetEs setState(String state) {
        this.state = state;
        return this;
    }

    @Override
    public List<ResourceReferenceEs> getResourceRefs() {
        return resourceRefs;
    }

    public ResourceSetEs setResourceRefs(List<ResourceReferenceEs> resourceRefs) {
        this.resourceRefs = resourceRefs;
        return this;
    }
}
