package cn.edu.pku.sei.tsr.dbinsight.commons.resource.task;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.Resource;

/**
 * Created by 唐爽 on 2017/3/2.
 */
public interface TaskContext extends Resource {
    String getStatus();

    TaskContext setStatus(String status);

    String getTransactorId();

    TaskContext setTransactorId(String transactorId);

    Double getProgress();

    TaskContext setProgress(Double progress);
}
