package cn.edu.pku.sei.tsr.dbinsight.commons.resource.task;

/**
 * Created by 唐爽 on 2017/3/2.
 */
public interface TaskStatus {
    /**
     * 等待中(已创建但还未初始化)
     */
    String Waiting = "waiting";
    /**
     * 初始化中
     */
    String Initializing = "initializing";
    /**
     * 正在运行
     */
    String Running = "running";
    /**
     * 正在停止
     */
    String Stopping = "stopping";
    /**
     * 已暂停
     */
    String Suspended = "suspended";
    /**
     * 正在恢复
     */
    String Resuming = "resuming";
    /**
     * 正在结束
     */
    String Ending = "ending";
    /**
     * 已完成
     */
    String Finished = "finished";
}
