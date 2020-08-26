package com.uinetworks.ecar.service;

import java.util.List;

import com.uinetworks.ecar.model.CarVO;
import com.uinetworks.ecar.model.CodeVO;
import com.uinetworks.ecar.model.DriveGPSVO;
import com.uinetworks.ecar.model.DriveInfoVO;
import com.uinetworks.ecar.model.NoticeVO;
import com.uinetworks.ecar.model.TokenVO;
import com.uinetworks.ecar.model.MemberVO;
import com.uinetworks.ecar.model.VersionVO;

public interface APIService {
	public VersionVO versionSelect();
	
	public void driverInsert(MemberVO memberVO);
	public MemberVO driverSelect(MemberVO memberVO);
	public MemberVO driverCheck(MemberVO memberVO);
	
	public TokenVO tokenSelect(TokenVO tokenVO);
	public void tokenUpdate(TokenVO tokenVO);
	public void tokenInsert(TokenVO tokenVO);
	
	public List<CodeVO> agencyList();
	public List<CodeVO> purposeList(String groupCodeId);
	public List<CarVO> carList();
	
	public void driveStartInsert(DriveInfoVO driveInfoVO);
	public void carStatusStart(String carNumber);
	public void driveEndInsert(DriveInfoVO driveInfoVO);
	public void carStatusEnd(String driveKey);
	public void DriveGPSInsert(DriveGPSVO driveGPSVO);
	
	public List<NoticeVO> noticeList(NoticeVO noticeVO);
	public NoticeVO noticeDetail(NoticeVO noticeVO);
	public NoticeVO noticePopup(NoticeVO noticeVO);
	
	public void pushInsert(TokenVO tokenVO);
	public void pushOnOff(TokenVO tokenVO);
}
