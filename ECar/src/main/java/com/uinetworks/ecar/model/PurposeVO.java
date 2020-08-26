package com.uinetworks.ecar.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class PurposeVO {
	private String purposeNum;
	private String purposeCategory;
	private String service;
	private String agencyName;
	private String sessionId;
}
