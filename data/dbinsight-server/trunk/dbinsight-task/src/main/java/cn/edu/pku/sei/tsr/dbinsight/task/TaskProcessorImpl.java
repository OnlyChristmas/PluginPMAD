package cn.edu.pku.sei.tsr.dbinsight.task;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.task.TaskContext;
import cn.edu.pku.sei.tsr.dbinsight.commons.resource.task.TaskRuntime;
import cn.edu.pku.sei.tsr.dbinsight.commons.resource.task.TaskStatus;
import cn.edu.pku.sei.tsr.dbinsight.commons.util.CheckUtil;
import cn.edu.pku.sei.tsr.dbinsight.essentials.api.TaskProcessor;
import cn.edu.pku.sei.tsr.dbinsight.essentials.api.service.ResourceService;
import cn.edu.pku.sei.tsr.dbinsight.essentials.api.transactor.Transactor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

/**
 * Created by 唐爽 on 2017/3/2.
 */
public class TaskProcessorImpl implements TaskProcessor {

    private Transactor transactor;
    private TaskContext context;
    private TaskRuntime runtime;

    private boolean stopped = false;

    @Autowired
    private ResourceService resourceService;

    @Override
    public void start() {
        CheckUtil.in(context.getStatus(), TaskStatus.Waiting, TaskStatus.Suspended);
        process();
    }

    @Override
    synchronized public void stop() {
        if (!stopped)
            stopped = true;
    }

    synchronized private void checkStop() {
        if (stopped)
            context.setStatus(TaskStatus.Stopping);
    }

    @Async
    public void process() {

        //初始化Runtime
        if (TaskStatus.Waiting.equals(context.getStatus())) {
            resourceService.save(context.setStatus(TaskStatus.Initializing));
            runtime = transactor.init(context);
//            resourceService.save(runtime.getOutputs());
        } else {
            resourceService.save(context.setStatus(TaskStatus.Resuming));
            runtime = transactor.resume(context);
        }

        //开始运行任务
        resourceService.save(context.setStatus(TaskStatus.Running));
        while (TaskStatus.Running.equals(context.getStatus())) {
            checkStop();
            transactor.step(context, runtime);
            resourceService.save(context);
        }

        //任务停止运行
        transactor.stop(context, runtime);
        resourceService.save(runtime.getOutputs());
        if (TaskStatus.Stopping.equals(context.getStatus())) {
            context.setStatus(TaskStatus.Suspended);
        } else if (TaskStatus.Ending.equals(context.getStatus())) {
            context.setStatus(TaskStatus.Finished);
        }
        resourceService.save(context);
    }

    @Override
    public Transactor getTransactor() {
        return transactor;
    }

    @Override
    public TaskProcessorImpl setTransactor(Transactor transactor) {
        this.transactor = transactor;
        return this;
    }

    @Override
    public TaskContext getContext() {
        return context;
    }

    @Override
    public TaskProcessorImpl setContext(TaskContext context) {
        this.context = context;
        return this;
    }


}
