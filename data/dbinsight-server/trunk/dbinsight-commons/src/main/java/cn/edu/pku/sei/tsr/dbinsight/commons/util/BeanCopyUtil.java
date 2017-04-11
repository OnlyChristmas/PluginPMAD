package cn.edu.pku.sei.tsr.dbinsight.commons.util;

import org.springframework.cglib.beans.BeanCopier;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shawn on 2017/3/1.
 */
public class BeanCopyUtil {

    private static Map<String, BeanCopier> beanCopierMap = new HashMap<>();

    public static void copy(Object source, Object target) {
        String key = generateKey(source.getClass(), target.getClass());
        beanCopierMap.computeIfAbsent(key, k -> BeanCopier.create(source.getClass(), target.getClass(), false));
        beanCopierMap.get(key).copy(source, target, null);
    }

    private static String generateKey(Class<?> class1, Class<?> class2) {
        return class1.toString() + class2.toString();
    }

}
