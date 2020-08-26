package com.uinetworks.ecar.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class AgencyVO {
	private String agencyCode;
	private String agencyName;
	private String agencyTel;
	private String agencyAddr;
}
