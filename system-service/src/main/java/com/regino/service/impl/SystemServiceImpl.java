package com.regino.service.impl;

import com.regino.pojo.Question;
import com.regino.service.SystemService;
import org.springframework.stereotype.Component;

/*
    @Service or @Component
 */
@Component
public class SystemServiceImpl implements SystemService {

    //熔断方法
    @Override
    public String addQuestion(Question question) {
        return "远程调用失败，启用默认方法";
    }
}
