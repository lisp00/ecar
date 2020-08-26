package com.uinetworks.ecar.model;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class UserVO {
	private String userid;
	private String password;
	private String name;
	private String mobile;
	private String zipCode;
	private String address;
	private String addressDetail;
	private String code;
	private String enabled;
	private String service;
	private String authorization;
	
	private Date createDatetime;
	private Date updateDatetime;
	private List<AuthVO> authList;	
}
