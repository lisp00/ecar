package com.uinetworks.ecar.model;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class MemberVO {
	private String userid;
	private String password;
	private String name;
	private String code;
	private String service;
	private String address;
	private String addressDetail;
	private String zipCode;
	private String mobile;
	private String enabled;
	private String token;
	
	private Date createDatetime;
	private Date updateDatetime;
	private List<AuthVO> authList;
}
