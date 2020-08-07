package com.regino.service;

import com.regino.config.FeignLogConfig;
import com.regino.pojo.Question;
import com.regino.service.impl.SystemServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/*
    value：所要调用的服务应用名称
    fallback：注解中指定熔断类
    configuration：可省略
 */
@FeignClient(value = "search-service",
        fallback = SystemServiceImpl.class,
        configuration = FeignLogConfig.class)
//@RequestMapping("search") //从原先的search/addQuestion提出来后，需要先加载"search-service"，否则报错
public interface SystemService {

    /*
        @RequestMapping 和 @GetMapping 和 @PostMapping 区别：
            @GetMapping：组合注解，是@RequestMapping(method = RequestMethod.GET)的缩写
            @PostMapping：组合注解，是@RequestMapping(method = RequestMethod.POST)的缩写
        Spring4.3中引进了｛@GetMapping、@PostMapping、@PutMapping、@DeleteMapping、@PatchMapping｝
            来帮助简化常用的HTTP方法的映射 并更好地表达被注解方法的语义
     */
    @PostMapping("search/addQuestion")
    String addQuestion(@RequestBody Question question);
}
