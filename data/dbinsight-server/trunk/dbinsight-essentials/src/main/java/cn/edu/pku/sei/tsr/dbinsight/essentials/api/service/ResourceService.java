package cn.edu.pku.sei.tsr.dbinsight.essentials.api.service;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.Resource;
import cn.edu.pku.sei.tsr.dbinsight.commons.resource.ResourceReference;

import java.util.List;

/**
 * 资源服务接口，提供资源的获取和资源引用的处理。
 * Created by 唐爽 on 2017/3/2.
 */
public interface ResourceService extends BaseService<Resource> {

    <T> T newInstance(Class<T> clazz);

    <T extends Resource> T newResource(Class<T> clazz);

    <T extends Resource> T initResource(T resource);

    ResourceReference getReference(Resource resource);

    List<ResourceReference> getReferenceList(List<Resource> resources);

    ResourceReference getReference(String id);

    List<ResourceReference> getReferenceListById(List<String> ids);

    //start from base service

    <T extends Resource> T getResource(String id, Class<T> clazz);

    <T extends Resource> T getResource(ResourceReference resourceRef, Class<T> clazz);

    Resource getResource(String id);

    Resource getResource(ResourceReference resourceRef);

    List<Resource> getResourceListById(List<String> ids);

    List<Resource> getResourceListByRef(List<ResourceReference> resourceRefs);

    void save(List<Resource> resources);

    void save(Resource resource);
}
