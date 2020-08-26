package com.uinetworks.ecar.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class DriveInfoVO {
	private String driveKey;
	private String name;
	private String carNumber;
	private String purposeCode;
	private String agencyCode;
	private String startLocation;
	private String endLocation;
	@JsonFormat(pattern = "yy-MM-dd HH:mm", timezone = "Asia/Seoul")
	private Timestamp startTime;
	@JsonFormat(pattern = "yy-MM-dd HH:mm", timezone = "Asia/Seoul")
	private Timestamp endTime;
	private Double distance;
	private String sessionId;
}
