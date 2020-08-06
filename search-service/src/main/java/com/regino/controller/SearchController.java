package com.regino.controller;

import com.regino.pojo.Question;
import com.regino.service.EsSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api("搜索controller")
@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired
    private EsSearchService searchService;

    @ApiOperation("根据问题搜索答案")
    @ApiImplicitParam(name = "title", value = "问题")
    @PostMapping("/answer")
    public Map<String, Object> answer(String title){
        Map<String, Object> resultMap = searchService.search(title);
        return resultMap;
    }

    @ApiOperation("新增问题")
    @PostMapping("addQuestion")
    public String addQuestion(@RequestBody Question question){
        try {
            searchService.addQuestion(question);
            return "添加问题成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "添加问题失败";
        }
    }
}