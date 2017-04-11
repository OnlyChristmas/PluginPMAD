package cn.edu.pku.sei.tsr.dbinsight.essentials.api.service;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.ResourceReference;

import java.util.List;

/**
 * Created by 唐爽 on 2017/3/7.
 */
public interface BaseService<B> {

    <T extends B> T getResource(String id, Class<T> clazz);

    <T extends B> T getResource(ResourceReference resourceRef, Class<T> clazz);

    B getResource(String id);

    B getResource(ResourceReference resourceRef);

    List<B> getResourceListById(List<String> ids);

    List<B> getResourceListByRef(List<ResourceReference> resourceRefs);

    void save(List<B> resources);

    void save(B resource);
}
