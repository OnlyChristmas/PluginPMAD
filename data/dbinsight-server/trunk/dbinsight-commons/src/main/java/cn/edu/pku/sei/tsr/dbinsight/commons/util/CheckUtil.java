package cn.edu.pku.sei.tsr.dbinsight.commons.util;

import cn.edu.pku.sei.tsr.dbinsight.commons.lang.ConflictException;

import java.util.Arrays;

/**
 * Created by 唐爽 on 2017/3/2.
 */
public class CheckUtil {
    public static void in(String str, String... expects) {
        if (!ifIn(str, expects))
            throw new ConflictException(String.format("have '%s', expects %s", str, Arrays.toString(expects)));
    }

    public static boolean ifIn(String str, String... expects) {
        if (expects.length == 0)
            return false;

        if (str == null) {
            for (String expect : expects) {
                if (expect == null) return true;
            }
            return false;
        }

        for (String expect : expects) {
            if (str.equals(expect)) return true;
        }
        return false;
    }

}
