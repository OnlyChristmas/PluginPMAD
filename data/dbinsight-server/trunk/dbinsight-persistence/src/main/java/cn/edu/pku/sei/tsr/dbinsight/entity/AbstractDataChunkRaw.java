package cn.edu.pku.sei.tsr.dbinsight.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Icing on 2016/11/12.
 */
//@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "rawType", discriminatorType = DiscriminatorType.STRING)
public abstract class AbstractDataChunkRaw {

    @Id
    @GeneratedValue(generator = "raw-uuid")
    @GenericGenerator(name = "raw-uuid", strategy = "uuid2")
    @Column(length = 40)
    private String id;

    public String getId() {
        return id;
    }

    public AbstractDataChunkRaw setId(String id) {
        this.id = id;
        return this;
    }
}
