package com.uinetworks.ecar.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class SurveyVO {
	private String surveyTitle;
	private String surveyVersion;
	private String surveyQuestion;
	private String surveyAnswer;
	private String userId;
	private String clear;
}
