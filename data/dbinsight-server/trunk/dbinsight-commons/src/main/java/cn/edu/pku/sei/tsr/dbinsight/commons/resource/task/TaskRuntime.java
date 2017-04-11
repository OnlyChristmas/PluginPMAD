package cn.edu.pku.sei.tsr.dbinsight.commons.resource.task;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.Resource;

import java.util.List;

/**
 * Created by 唐爽 on 2017/3/2.
 */
public interface TaskRuntime {
    List<Resource> getOutputs();
    TaskRuntime setOutputs(List<Resource> outputs);
}
