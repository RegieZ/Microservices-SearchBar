package com.regino.repository;

import com.regino.pojo.Question;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/*
    以上服务搭建好以后可以直接运行项目，项目在启动时springboot会自动检测当前索引库是否存在，不存在则自动创建
    ElasticsearchCrudRepository只有增删改查
 */
public interface QuestionRepository extends ElasticsearchRepository<Question, String> { //参数2为Question中id的类型
}
