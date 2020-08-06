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

@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired
    private EsSearchService searchService;

    @PostMapping("/answer")
    public Map<String, Object> answer(String title){
        Map<String, Object> resultMap = searchService.search(title);
        return resultMap;
    }

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