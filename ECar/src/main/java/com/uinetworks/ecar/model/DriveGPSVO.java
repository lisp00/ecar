package com.uinetworks.ecar.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class DriveGPSVO {
	private String driveKey;
	private int gpsId;
	private Double latitude;
	private Double longitude;
	@JsonFormat(pattern = "yy-MM-dd HH:mm", timezone = "Asia/Seoul")
	private Timestamp receiveTime;
	private Double speed;
	private String sessionId;
}
