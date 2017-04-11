import cn.edu.pku.sei.tsr.dbinsight.commons.util.BeanCopyUtil;
import cn.edu.pku.sei.tsr.dbinsight.persistence.elasticsearch.document.data.SqlDataChunkSourceEs;
import cn.edu.pku.sei.tsr.dbinsight.web.model.SqlDataChunkSourceVO;
import org.springframework.beans.BeanUtils;

/**
 * Created by Shawn on 2017/3/1.
 */
public class TestMain {
    public static void main(String[] args) {
        SqlDataChunkSourceVO sqlDataChunkSourceVO = new SqlDataChunkSourceVO();
        SqlDataChunkSourceEs sqlDataChunkSourceEs = new SqlDataChunkSourceEs().setDriverClassName("123");
//        BeanUtils.copyProperties(sqlDataChunkSourceVO, sqlDataChunkSourceEs);
        BeanCopyUtil.copy(sqlDataChunkSourceEs, sqlDataChunkSourceVO);
        System.out.println(sqlDataChunkSourceVO.getDriverClassName());
    }
}
