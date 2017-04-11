package cn.edu.pku.sei.tsr.dbinsight.commons.resource;

/**
 * 用于存放对资源的引用信息，包含被引用资源的id和资源类型type
 * Created by Shawn on 2017/2/28.
 */
public interface ResourceReference {

    String getId();

    ResourceReference setId(String id);

    String getType();

    ResourceReference setType(String type);

}
