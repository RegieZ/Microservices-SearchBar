package com.regino.service;

import com.regino.pojo.Question;

import java.util.Map;

public interface EsSearchService {
    
    Map<String, Object> search(String title);

    void addQuestion(Question question);
}
