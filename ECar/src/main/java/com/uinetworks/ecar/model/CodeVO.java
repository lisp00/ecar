package com.uinetworks.ecar.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class CodeVO {
	
	private String groupCodeId;
	private String codeId;
	private String groupCodeName;
	private String codeName;
	private String usingYn;
	
}
