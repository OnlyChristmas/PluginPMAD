package cn.edu.pku.sei.tsr.dbinsight.commons.resource.data;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.Resource;
import cn.edu.pku.sei.tsr.dbinsight.commons.resource.ResourceReference;

/**
 * Created by Icing on 2017/2/27.
 */
public interface DataChunk extends Resource {
    @Override
    default String getType() {
        return DataChunk.class.getSimpleName();
    }

    ResourceReference getImportTaskRef();

    DataChunk setImportTaskRef(ResourceReference importTaskRef);

    int getOutputOrdinal();

    DataChunk setOutputOrdinal(int outputOrdinal);
}
