package cn.edu.pku.sei.tsr.dbinsight.core.registry;

import cn.edu.pku.sei.tsr.dbinsight.commons.lang.InternalServerErrorException;
import cn.edu.pku.sei.tsr.dbinsight.essentials.api.Instancer;
import cn.edu.pku.sei.tsr.dbinsight.essentials.api.registry.InstancerRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by 唐爽 on 2017/3/7.
 */
@Service
@ConditionalOnMissingBean(InstancerRegistry.class)
public class InstancerRegistryImpl implements InstancerRegistry {

    private Map<String, Class<? extends Instancer>> map = new HashMap<>();

    @Override
    public Class<? extends Instancer> lookup(String key) {
        if (!map.containsKey(key))
            throw new IllegalArgumentException("key <"+key+"> doesn't exist.");
        return map.get(key);
    }

    @Override
    public void register(String key, Class<? extends Instancer> clazz) {
        if (map.containsKey(key))
            throw new IllegalArgumentException("key <"+key+"> already exists.");
        map.put(key, clazz);
    }

    @Override
    public Set<Map.Entry<String, Class<? extends Instancer>>> entrySet() {
        return map.entrySet();
    }
}
