package cn.edu.pku.sei.tsr.dbinsight.essentials.api;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.task.TaskContext;
import cn.edu.pku.sei.tsr.dbinsight.essentials.api.transactor.Transactor;

/**
 * 请保证实现作为原形，每一个处理任务都会生成新的Processor
 * Created by 唐爽 on 2017/3/2.
 */
public interface TaskProcessor {
    Transactor getTransactor();
    TaskProcessor setTransactor(Transactor transactor);
    TaskContext getContext();
    TaskProcessor setContext(TaskContext context);
    void start();
    void stop();
}
