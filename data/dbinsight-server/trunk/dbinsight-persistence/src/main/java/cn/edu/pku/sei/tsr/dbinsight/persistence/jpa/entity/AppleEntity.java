package cn.edu.pku.sei.tsr.dbinsight.persistence.jpa.entity;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.Apple;
import cn.edu.pku.sei.tsr.dbinsight.commons.resource.ResourceState;

import javax.persistence.*;

/**
 * Created by Icing on 2017/2/26.
 */
@Entity
public class AppleEntity implements Apple {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    private String state;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public AppleEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getId() {
        return id;
    }

    public AppleEntity setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public AppleEntity setState(String state) {
        this.state = state;
        return this;
    }
}
