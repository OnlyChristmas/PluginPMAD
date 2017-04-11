package cn.edu.pku.sei.tsr.dbinsight.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Shawn on 2016/11/11.
 */
//@Entity
public class DataChunkMeta {

    @Id
    @GeneratedValue(generator = "chunk-uuid")
    @GenericGenerator(name = "chunk-uuid", strategy = "uuid2")
    @Column(length = 40)
    private String id;

    @Column(nullable = false, unique = true)
    private String chunkName;

    @Column
    private String chunkDescription;

    @Column
    private Integer instanceNum;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createDate;

    public enum DataChunkState {
        Processing,
        Available,
        Deleted
    }

    @Enumerated(EnumType.STRING)
    private DataChunkState state;

    @Column
    private String ossKey;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = AbstractDataChunkRaw.class)
    private List<AbstractDataChunkRaw> raws;

    public String getId() {
        return id;
    }

    public DataChunkMeta setId(String id) {
        this.id = id;
        return this;
    }

    public String getChunkName() {
        return chunkName;
    }

    public DataChunkMeta setChunkName(String chunkName) {
        this.chunkName = chunkName;
        return this;
    }

    public String getChunkDescription() {
        return chunkDescription;
    }

    public DataChunkMeta setChunkDescription(String chunkDescription) {
        this.chunkDescription = chunkDescription;
        return this;
    }

    public Integer getInstanceNum() {
        return instanceNum;
    }

    public DataChunkMeta setInstanceNum(Integer instanceNum) {
        this.instanceNum = instanceNum;
        return this;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public DataChunkMeta setCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public DataChunkState getState() {
        return state;
    }

    public DataChunkMeta setState(DataChunkState state) {
        this.state = state;
        return this;
    }

    public String getOssKey() {
        return ossKey;
    }

    public DataChunkMeta setOssKey(String ossKey) {
        this.ossKey = ossKey;
        return this;
    }

    public List<AbstractDataChunkRaw> getRaws() {
        return raws;
    }

    public DataChunkMeta setRaws(List<AbstractDataChunkRaw> raws) {
        this.raws = raws;
        return this;
    }
}
