package cn.edu.pku.sei.tsr.dbinsight.commons.topics.resource.data;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.data.DataChunk;

import java.util.List;

/**
 * Created by Shawn on 2017/2/28.
 */
public interface TopicsDataChunk extends DataChunk {
    List<TopicsDocument> getDocuments();

    TopicsDataChunk setDocuments(List<TopicsDocument> documents);
}
