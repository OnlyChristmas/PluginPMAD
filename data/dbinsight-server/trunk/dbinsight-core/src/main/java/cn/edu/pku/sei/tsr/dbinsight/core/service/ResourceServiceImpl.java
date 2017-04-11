package cn.edu.pku.sei.tsr.dbinsight.core.service;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.Resource;
import cn.edu.pku.sei.tsr.dbinsight.commons.resource.ResourceReference;
import cn.edu.pku.sei.tsr.dbinsight.commons.resource.ResourceState;
import cn.edu.pku.sei.tsr.dbinsight.commons.util.SpringUtil;
import cn.edu.pku.sei.tsr.dbinsight.essentials.api.Instancer;
import cn.edu.pku.sei.tsr.dbinsight.essentials.api.registry.InstancerRegistry;
import cn.edu.pku.sei.tsr.dbinsight.essentials.api.registry.ResourceServiceRegistry;
import cn.edu.pku.sei.tsr.dbinsight.essentials.api.service.BaseService;
import cn.edu.pku.sei.tsr.dbinsight.essentials.api.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by 唐爽 on 2017/3/7.
 */
@Service
@ConditionalOnMissingBean(ResourceService.class)
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private InstancerRegistry instancerRegistry;

    @Autowired
    private ResourceServiceRegistry resourceServiceRegistry;

    @Override
    public <T> T newInstance(Class<T> clazz) {
        Instancer<T> instancer = SpringUtil.getBean(instancerRegistry.lookup(clazz.getSimpleName()));
        return instancer.newOne();
    }

    @Override
    public <T extends Resource> T newResource(Class<T> clazz) {
        return initResource(newInstance(clazz));
    }

    @Override
    public <T extends Resource> T initResource(T resource) {
        return (T) resource.setId(UUID.randomUUID().toString()).setState(ResourceState.Created);
    }

    @Override
    public ResourceReference getReference(Resource resource) {
        return newInstance(ResourceReference.class).setId(resource.getId()).setType(resource.getType());
    }

    @Override
    public List<ResourceReference> getReferenceList(List<Resource> resources) {
        return resources.stream().map(this::getReference).collect(Collectors.toList());
    }

    @Override
    public ResourceReference getReference(String id) {
        return getReference(getResource(id));
    }

    @Override
    public List<ResourceReference> getReferenceListById(List<String> ids) {
        return getReferenceList(getResourceListById(ids));
    }

    @Override
    public <T extends Resource> T getResource(String id, Class<T> clazz) {
        BaseService service = SpringUtil.getBean(resourceServiceRegistry.lookup(clazz.getSimpleName()));
        return (T) service.getResource(id, clazz);
    }

    @Override
    public <T extends Resource> T getResource(ResourceReference resourceRef, Class<T> clazz) {
        BaseService service = SpringUtil.getBean(resourceServiceRegistry.lookup(clazz.getSimpleName()));
        return (T) service.getResource(resourceRef, clazz);
    }

    @Override
    public Resource getResource(String id) {
        for (Map.Entry<String, Class<? extends BaseService<? extends Resource>>> entry : resourceServiceRegistry.entrySet()) {
            BaseService service = SpringUtil.getBean(entry.getValue());
            Resource resource = (Resource) service.getResource(id);
            if (resource != null)
                return resource;
        }
        return null;
    }

    @Override
    public Resource getResource(ResourceReference resourceRef) {
        return null;
    }

    @Override
    public List<Resource> getResourceListById(List<String> ids) {
        return null;
    }

    @Override
    public List<Resource> getResourceListByRef(List<ResourceReference> resourceRefs) {
        return null;
    }

    @Override
    public void save(List<Resource> resources) {

    }

    @Override
    public void save(Resource resource) {

    }
}
