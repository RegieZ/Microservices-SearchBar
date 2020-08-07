package com.regino.controller;

import com.regino.pojo.Question;
import com.regino.service.SystemService;
import com.regino.util.IDWorker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController //组合注解，包括@Controller，@ResponseBody等
@RequestMapping("system")
@Api(tags = "后台相关接口")
public class SystemController {

    @Autowired
    private SystemService systemService;

    /**
     * 版本1
     *
     * @param title
     * @param answer
     * @return
     */
    @PostMapping("/addQuestion/v1/{title}/{answer}")
    @ApiOperation("添加问题的接口v1")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "问题"),
            @ApiImplicitParam(name = "answer", value = "答案")
    })
    public String addQuestionV1(@PathVariable String title, @PathVariable String answer) {
        Question question = new Question();
        question.setId(UUID.randomUUID().toString());
        question.setTitle(title);
        question.setAnswer(answer);
        String msg = systemService.addQuestion(question);
        return msg;
    }

    /**
     * 版本2
     *
     * @param title
     * @param answer
     * @return
     */
    @PostMapping("/addQuestion/v2")
    @ApiOperation("添加问题的接口v2")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "问题"),
            @ApiImplicitParam(name = "answer", value = "答案")
    })
    public String addQuestionV2(@RequestParam String title, @RequestParam String answer) {
        Question question = new Question();
        question.setId(UUID.randomUUID().toString());
        question.setTitle(title);
        question.setAnswer(answer);
        String msg = systemService.addQuestion(question);
        return msg;
    }

    /*
        http://127.0.0.1:9091/system/addQuestion/v3

        postman 选中body--->raw--->json格式--->请求体中编写json请求
            {
            "title":"Who are you?",
             "answer":"Saul."
            }
     */
    /**
     * 版本3
     *
     * @param question
     * @return
     */
    @PostMapping("/addQuestion/v3")
    @ApiOperation("添加问题的接口v3")
    public String addQuestionV3(@RequestBody Question question) {
        question.setId(UUID.randomUUID().toString());
        String msg = systemService.addQuestion(question);
        return msg;
    }

    /**
     * 版本4
     *
     * @param question
     * @return
     */
    @ApiOperation("添加问题接口v4")
    @PostMapping("addQuestion/v4")
    public String addQuestion(@RequestBody Question question) {
        IDWorker idWorker = new IDWorker(0, 0);
        question.setId(String.valueOf(idWorker.nextId()));
        String result = question.getTitle() + systemService.addQuestion(question);
        return result;
    }
}
