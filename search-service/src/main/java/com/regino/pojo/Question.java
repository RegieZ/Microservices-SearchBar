package com.regino.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.lang.annotation.Documented;

/*
    创建es：1.实体类加注解@Document @ID @Field，2.创建仓库
 */
@Data
@Document(indexName = "question", type = "question") //ES索引库名（对应MySQL中的数据库，建议项目名）与类型名（对应MySQL中的表，建议实体类名）
@ApiModel
public class Question {

    @ApiModelProperty(value = "问题id")
    @Id //如果使用的是sde 对象中的id等同于 _id
    private String id;

    @ApiModelProperty(value = "问题")
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;

    @ApiModelProperty(value = "答案")
    @Field(type = FieldType.Text, analyzer = "ik_max_word") //答案可以分词，也可以不分词
    private String answer;

}