package com.bridgelabz.fundoo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LabelDto {

	@ApiModelProperty(notes = "Name of the label", name = "label name")

	private String labelName;

}