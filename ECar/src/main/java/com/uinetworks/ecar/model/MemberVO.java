package com.uinetworks.ecar.model;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class MemberVO {
	private String userid;
	private String password;
	private String name;
	private String agencyCode;
	private String service;
	private String address;
	private String addressDetail;
	private String zipCode;
	private String mobile;
	private String enabled;
	private String authorization;
	
	private Date createDatetime;
	private Date updateDatetime;
	private List<AuthVO> authList;
}
