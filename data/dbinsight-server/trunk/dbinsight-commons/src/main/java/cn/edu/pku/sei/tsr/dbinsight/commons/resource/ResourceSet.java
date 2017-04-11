package cn.edu.pku.sei.tsr.dbinsight.commons.resource;

/**
 * Created by Shawn on 2017/2/28.
 */
public interface ResourceSet extends Resource {
    @Override
    default String getType() {
        return ResourceSet.class.getSimpleName();
    }

    Iterable<? extends ResourceReference> getResourceRefs();

//    ResourceSet setResourceRefs(Iterable<? extends ResourceReference> resourceRefs);

}
