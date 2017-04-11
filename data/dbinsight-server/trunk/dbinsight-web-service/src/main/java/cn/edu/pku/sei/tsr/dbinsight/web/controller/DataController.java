package cn.edu.pku.sei.tsr.dbinsight.web.controller;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.Apple;
import cn.edu.pku.sei.tsr.dbinsight.commons.resource.Resource;
import cn.edu.pku.sei.tsr.dbinsight.commons.resource.ResourceReference;
import cn.edu.pku.sei.tsr.dbinsight.commons.resource.data.DataChunkSource;
import cn.edu.pku.sei.tsr.dbinsight.commons.resource.data.SqlDataChunkSource;
import cn.edu.pku.sei.tsr.dbinsight.commons.util.BeanCopyUtil;
import cn.edu.pku.sei.tsr.dbinsight.essentials.api.Previewer;
import cn.edu.pku.sei.tsr.dbinsight.essentials.api.repository.AppleRepository;
import cn.edu.pku.sei.tsr.dbinsight.essentials.api.repository.data.DataChunkSourceRepository;
import cn.edu.pku.sei.tsr.dbinsight.essentials.api.repository.data.SqlDataChunkSourceRepository;
import cn.edu.pku.sei.tsr.dbinsight.web.model.SqlDataChunkSourceVO;
import cn.edu.pku.sei.tsr.dbinsight.data.sql.SqlDataChunkSourcePreviewer;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by 唐爽 on 2016/11/10.
 */
@RestController
@RequestMapping("/data")
public class DataController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello world!";
    }

    @Autowired
    private AppleRepository appleRepository;

    @RequestMapping(method = RequestMethod.POST, value = "/apple")
    public Apple createApple(@RequestParam String name) {
        Apple apple = appleRepository.newOne()
                .setName(name);
        appleRepository.save(apple);
        return apple;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/apple")
    public Apple getAppleById(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String name
    ) {
        if (id != null)
            return appleRepository.findOne(id);
        if (name != null)
            return appleRepository.findByName(name);
        return null;
    }

    @Autowired
    private SqlDataChunkSourceRepository sqlDataChunkSourceRepository;

    @Autowired
    private DataChunkSourceRepository dataChunkSourceRepository;


    @RequestMapping(method = RequestMethod.POST, value = "/dataSources/sql")
    public SqlDataChunkSource createSqlDataChunkSource(
            @RequestBody SqlDataChunkSourceVO sqlDataChunkSourceVO
    ) {
        SqlDataChunkSource sqlDataChunkSource = sqlDataChunkSourceRepository.newOne()
                .setDriverClassName(sqlDataChunkSourceVO.getDriverClassName())
                .setUrl(sqlDataChunkSourceVO.getUrl())
                .setUsername(sqlDataChunkSourceVO.getUsername())
                .setPassword(sqlDataChunkSourceVO.getPassword());
        sqlDataChunkSourceRepository.save(sqlDataChunkSource);
        return sqlDataChunkSource;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/dataSources/{id}")
    @ApiImplicitParam(name = "id", paramType = "path")
    public ResourceReference getDataChunkSource(
            @PathVariable String id
    ) {
        return dataChunkSourceRepository.findOne(id);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/dataSources")
    public List<DataChunkSource> getDataChunkSourceList() {
        return dataChunkSourceRepository.findAll();
    }

    @Autowired
    private Previewer previewer;

    @RequestMapping(method = RequestMethod.GET, value = "/preview/{id}")
    @ApiImplicitParam(name = "id", paramType = "path")
    public Object getDataChunkPreview(
            @PathVariable String id
    ) {
        Resource resource = sqlDataChunkSourceRepository.findOne(id);
        return previewer.getPreview(resource);
    }
}
