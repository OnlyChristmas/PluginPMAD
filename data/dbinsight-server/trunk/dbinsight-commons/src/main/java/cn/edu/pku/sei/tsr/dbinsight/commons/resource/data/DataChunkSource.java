package cn.edu.pku.sei.tsr.dbinsight.commons.resource.data;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.Resource;

/**
 * Created by Icing on 2017/2/27.
 */
public interface DataChunkSource extends Resource {

    @Override
    default String getType() {
        return DataChunkSource.class.getSimpleName();
    }
}
