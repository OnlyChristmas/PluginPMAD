package cn.edu.pku.sei.tsr.dbinsight.essentials.api.registry;

import java.util.Map;
import java.util.Set;

/**
 * Created by Shawn on 2017/3/1.
 */
public interface Registry<T> {
    Class<? extends T> lookup(String key);
    void register(String key, Class<? extends T> clazz);
    Set<Map.Entry<String, Class<? extends T>>> entrySet();
}
