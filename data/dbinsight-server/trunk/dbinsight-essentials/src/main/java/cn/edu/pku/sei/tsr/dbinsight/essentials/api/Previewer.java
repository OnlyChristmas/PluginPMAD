package cn.edu.pku.sei.tsr.dbinsight.essentials.api;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.Resource;

/**
 * Created by Shawn on 2017/3/1.
 */
public interface Previewer extends Registered {

    Object getPreview(Resource resource);

}
