package cn.edu.pku.sei.tsr.dbinsight.persistence.elasticsearch.document;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.ResourceReference;

/**
 * Created by Shawn on 2017/2/28.
 */
public class ResourceReferenceEs implements ResourceReference {

    private String id;
    private String type;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public ResourceReferenceEs setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public ResourceReferenceEs setType(String type) {
        this.type = type;
        return this;
    }
}
