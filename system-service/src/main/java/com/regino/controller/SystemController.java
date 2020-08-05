package com.regino.controller;

import com.regino.pojo.Question;
import com.regino.service.SystemService;
import com.regino.util.IDWorker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("system")
@Api(tags = "添加问题controller")
public class SystemController {

    @Qualifier("search-service")
    @Autowired
    private SystemService systemService;

    @ApiOperation("添加问题接口")
    @PostMapping("/addQuestion")
    public String addQuestion(@RequestBody Question question) {
        IDWorker idWorker = new IDWorker(0, 0);
        question.setId(idWorker.nextId());
        String result = question.getTitle() + systemService.addQuestion(question);
        return result;
    }
}
