import cn.edu.pku.sei.tsr.dbinsight.persistence.oss.repository.FileOssRepository;

import java.util.Arrays;

/**
 * Created by Icing on 2016/12/23.
 */
public class TestMain {
    public static void main(String[] args) {
        FileOssRepository oss = new FileOssRepository("oss", true);

        byte[] bytes = "haha".getBytes();
        oss.save("asdasdasdadsdasd/haha.txt", bytes);
        System.out.println(Arrays.toString(oss.get("asdasdasdadsdasd/haha.txt")));
    }
}
