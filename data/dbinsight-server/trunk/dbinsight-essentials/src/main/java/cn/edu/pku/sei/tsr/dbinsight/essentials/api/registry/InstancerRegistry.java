package cn.edu.pku.sei.tsr.dbinsight.essentials.api.registry;

import cn.edu.pku.sei.tsr.dbinsight.essentials.api.Instancer;

import java.util.Map;
import java.util.Set;

/**
 * Created by 唐爽 on 2017/3/7.
 */
public interface InstancerRegistry extends Registry<Instancer> {
    @Override
    Class<? extends Instancer> lookup(String key);

    @Override
    void register(String key, Class<? extends Instancer> clazz);

    @Override
    Set<Map.Entry<String, Class<? extends Instancer>>> entrySet();
}
