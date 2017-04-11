package cn.edu.pku.sei.tsr.dbinsight.persistence.oss.repository;

import cn.edu.pku.sei.tsr.dbinsight.commons.util.hash.HashEncoder;
import cn.edu.pku.sei.tsr.dbinsight.commons.lang.InternalServerErrorException;
import cn.edu.pku.sei.tsr.dbinsight.essentials.api.repository.OssRepository;

import java.io.*;

/**
 * Created by Icing on 2016/12/23.
 */
public class FileOssRepository implements OssRepository {

    private final String rootDir;
    private final boolean useHash;

    public FileOssRepository(String rootDir, boolean useHash) {
        this.rootDir = rootDir;
        this.useHash = useHash;
    }

    @Override
    public void save(String key, byte[] bytes) {
        String dir = getFullDir(key);
        File file = new File(dir);
        file.getParentFile().mkdirs();
        try (OutputStream outputStream = new FileOutputStream(dir)) {
            outputStream.write(bytes);
        } catch (IOException e) {
            throw new InternalServerErrorException("保存本地文件出错", e);
        }
    }

    @Override
    public byte[] get(String key) {
        String dir = getFullDir(key);
        File file = new File(dir);
        if (!file.exists())
            return new byte[0];
        try (InputStream inputStream = new FileInputStream(file)) {
            byte[] bytes = new byte[(int) file.length()];
            int nReadbytes = inputStream.read(bytes);
            return bytes;
        } catch (IOException e) {
            throw new InternalServerErrorException("读取本地文件出错", e);
        }
    }

    @Override
    public void delete(String key) {
        String dir = getFullDir(key);
        File file = new File(dir);
        if (file.exists())
            file.delete();
    }

    private String getFullDir(String key) {
        if (useHash) {
            key = HashEncoder.sha1(key);
        }
        return rootDir + "/" + key;
    }

}
