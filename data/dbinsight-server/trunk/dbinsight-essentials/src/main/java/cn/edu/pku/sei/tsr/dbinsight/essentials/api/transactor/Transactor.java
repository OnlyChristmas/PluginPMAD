package cn.edu.pku.sei.tsr.dbinsight.essentials.api.transactor;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.task.TaskContext;
import cn.edu.pku.sei.tsr.dbinsight.commons.resource.task.TaskRuntime;

/**
 * Created by Shawn on 2017/3/1.
 */
public interface Transactor {
    TaskRuntime init(TaskContext taskContext);
    TaskRuntime resume(TaskContext taskContext);
    void step(TaskContext taskContext, TaskRuntime taskRuntime);
    void stop(TaskContext taskContext, TaskRuntime taskRuntime);
}
