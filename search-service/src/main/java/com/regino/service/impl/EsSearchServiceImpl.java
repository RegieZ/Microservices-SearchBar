package com.regino.service.impl;

import com.google.common.collect.Lists;
import com.regino.pojo.Question;
import com.regino.repository.QuestionRepository;
import com.regino.service.EsSearchService;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EsSearchServiceImpl implements EsSearchService {

    @Autowired
    private QuestionRepository questionRepository;

    //新增文档
    @Override
    public void addQuestion(Question question) {
        //数据保存（有则修改，无则添加）
        questionRepository.save(question);
    }

    //term查询
    @Override
    public Map<String, Object> search(String title) {
        Map<String, Object> resultMap = new HashMap<>();
        //1.构建queryBuilder对象
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("title", title);
        //2.使用questionRepository完成调用
        Iterable<Question> questions = questionRepository.search(queryBuilder);
        //3.解析结果
        resultMap.put("questionList", Lists.newArrayList(questions));
        return resultMap;
    }
}
