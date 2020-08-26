package com.uinetworks.ecar.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_DEFAULT)
public class NoticeVO {
	private int seq;
	private String title;
	private String content;
	@JsonFormat(pattern = "yy-MM-dd HH:mm", timezone = "Asia/Seoul")
	private Timestamp date;
	private String userId;
	private int limit;
	private int offset;
	private String sessionId;
}


