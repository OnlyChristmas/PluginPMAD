package cn.edu.pku.sei.tsr.dbinsight.commons.resource.task;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.ResourceReference;

import java.util.List;

/**
 * Created by 唐爽 on 2017/3/2.
 */
public interface SqlTopicsDCImportContext extends TaskContext {
    ResourceReference getDataChunkSourceRef();

    SqlTopicsDCImportContext setDataChunkSourceRef(ResourceReference dataChunkSourceRef);

    List<ResourceReference> getTopicsDataChunkRefs();

    SqlTopicsDCImportContext setTopicsDataChunkRef(List<ResourceReference> topicsDataChunkRefs);

    String getTableName();

    String getIdColumnName();

    //优化，单表多列同时处理
    List<String> getWordColumnNames();

    int getReadCount();

    SqlTopicsDCImportContext setReadCount(int readCount);

    int getTotalCount();

    SqlTopicsDCImportContext setTotalCount(int totalCount);
}
