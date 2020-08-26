package com.uinetworks.ecar.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
public class VersionVO {
	private boolean updated;
	private int version;
	private String upUrl;
	private String upTitle;
	private String upInfo;
	private boolean pop;
	private String popUrl;
}
