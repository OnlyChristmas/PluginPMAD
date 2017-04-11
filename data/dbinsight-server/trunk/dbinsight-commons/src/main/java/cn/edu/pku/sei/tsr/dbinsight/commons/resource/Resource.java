package cn.edu.pku.sei.tsr.dbinsight.commons.resource;

/**
 * Created by Icing on 2017/2/27.
 */
public interface Resource extends ResourceReference {

    String getId();

    Resource setId(String id);

    String getType();

    /**
     * 形式化方法，没有实际意义。
     * @param type 无用的传入参数，Type类型在资源创建后不能随意改变。
     * @return
     */
    default Resource setType(String type) {
        return this;
    }

    String getState();

    Resource setState(String state);
}
