package cn.edu.pku.sei.tsr.dbinsight.commons.resource;

/**
 * Created by Icing on 2017/2/26.
 */
public interface Apple extends Resource {
    @Override
    default String getType() {
        return Apple.class.getSimpleName();
    }

    String getName();

    Apple setName(String name);
}
