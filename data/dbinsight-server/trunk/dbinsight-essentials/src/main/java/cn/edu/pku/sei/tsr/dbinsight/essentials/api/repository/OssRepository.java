package cn.edu.pku.sei.tsr.dbinsight.essentials.api.repository;

/**
 * 用于存储字节信息到文件，例如保存生成的算法输入数据
 * Created by Icing on 2016/12/23.
 */
public interface OssRepository {
    void save(String key, byte[] bytes);

    byte[] get(String key);

    void delete(String key);
}
