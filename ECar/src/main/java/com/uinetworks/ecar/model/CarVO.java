package com.uinetworks.ecar.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class CarVO {
	
	private String carNumber;
	private String carStatus;
	private String sessionId;
	
}
