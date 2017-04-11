package cn.edu.pku.sei.tsr.dbinsight.commons.resource.data;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.Resource;

import java.util.List;

/**
 * Created by Icing on 2017/2/27.
 */
public interface DataChunkMeta extends Resource {

    String getName();

    DataChunkMeta setName(String name);

    String getDescription();

    DataChunkMeta setDescription(String description);

    List<String> getTags();

    DataChunkMeta setTags(List<String> tags);

    @Override
    default String getType() {
        return DataChunkMeta.class.getSimpleName();
    }
}
