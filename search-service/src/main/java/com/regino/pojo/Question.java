package com.regino.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.lang.annotation.Documented;

@Data
@Document(indexName = "question", type = "question")
@ApiModel
public class Question {

    @ApiModelProperty(value = "问题id")
    @Id
    private Long id;

    @ApiModelProperty(value = "问题")
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;

    @ApiModelProperty(value = "答案")
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String answer;

}