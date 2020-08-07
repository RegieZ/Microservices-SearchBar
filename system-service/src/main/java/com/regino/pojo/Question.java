package com.regino.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class Question {

    @ApiModelProperty(value = "id")
    private String id;
    //问题
    @ApiModelProperty(value = "问题")
    private String title;
    //答案
    @ApiModelProperty(value = "答案")
    private String answer;
}

