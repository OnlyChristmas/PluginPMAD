package cn.edu.pku.sei.tsr.dbinsight.core.registry;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.Resource;
import cn.edu.pku.sei.tsr.dbinsight.essentials.api.registry.ResourceServiceRegistry;
import cn.edu.pku.sei.tsr.dbinsight.essentials.api.service.BaseService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by 唐爽 on 2017/3/9.
 */
@Service
@ConditionalOnMissingBean(ResourceServiceRegistry.class)
public class ResourceServiceRegistryImpl implements ResourceServiceRegistry {

    private Map<String, Class<? extends BaseService<? extends Resource>>> map = new HashMap<>();

    @Override
    public Class<? extends BaseService<? extends Resource>> lookup(String key) {
        if (!map.containsKey(key))
            throw new IllegalArgumentException("key <"+key+"> doesn't exist.");
        return map.get(key);
    }

    @Override
    public Set<Map.Entry<String, Class<? extends BaseService<? extends Resource>>>> entrySet() {
        return map.entrySet();
    }

    @Override
    public void register(String key, Class<? extends BaseService<? extends Resource>> clazz) {
        if (map.containsKey(key))
            throw new IllegalArgumentException("key <"+key+"> already exists.");
        map.put(key, clazz);
    }
}
