package cn.edu.pku.sei.tsr.dbinsight.commons.util;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.Resource;

/**
 * Created by Shawn on 2017/3/1.
 */
public class TypeUtil {

    public static String getType(Class<? extends Resource> clazz) {
        return clazz.getSimpleName();
    }

}
