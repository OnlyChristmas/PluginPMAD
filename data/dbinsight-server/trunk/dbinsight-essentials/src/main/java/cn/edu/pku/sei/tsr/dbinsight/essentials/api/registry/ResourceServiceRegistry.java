package cn.edu.pku.sei.tsr.dbinsight.essentials.api.registry;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.Resource;
import cn.edu.pku.sei.tsr.dbinsight.essentials.api.service.BaseService;

import java.util.Map;
import java.util.Set;

/**
 * Created by 唐爽 on 2017/3/9.
 */
public interface ResourceServiceRegistry extends Registry<BaseService<? extends Resource>> {

    @Override
    Class<? extends BaseService<? extends Resource>> lookup(String key);

    @Override
    Set<Map.Entry<String, Class<? extends BaseService<? extends Resource>>>> entrySet();

    @Override
    void register(String key, Class<? extends BaseService<? extends Resource>> clazz);
}
