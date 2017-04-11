package cn.edu.pku.sei.tsr.dbinsight.web.controller.v1;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.data.SqlDataChunkSource;
import cn.edu.pku.sei.tsr.dbinsight.web.model.SqlDataChunkSourceVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 唐爽 on 2017/3/7.
 */
@RestController
@RequestMapping("/v1/dataChunkSource")
public class DataChunkSourceController {



    @RequestMapping(value = "/sql", method = RequestMethod.POST)
    public SqlDataChunkSource addSqlDataChunkSource(
            @RequestBody SqlDataChunkSourceVO sqlDataChunkSourceVO
    ) {
        return null;
    }

}
