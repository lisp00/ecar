package com.uinetworks.ecar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uinetworks.ecar.mapper.APIMapper;
import com.uinetworks.ecar.model.CarVO;
import com.uinetworks.ecar.model.CodeVO;
import com.uinetworks.ecar.model.DriveGPSVO;
import com.uinetworks.ecar.model.DriveInfoVO;
import com.uinetworks.ecar.model.NoticeVO;
import com.uinetworks.ecar.model.TokenVO;
import com.uinetworks.ecar.model.MemberVO;
import com.uinetworks.ecar.model.VersionVO;

@Service
public class APIServiceImpl implements APIService {

	@Autowired
	APIMapper eCarMapper;

	@Override
	public VersionVO versionSelect() {
		
		return eCarMapper.versionSelect();
	}

	@Override
	public void driverInsert(MemberVO memberVO) {
		
		eCarMapper.driverInsert(memberVO);
	}

	@Override
	public MemberVO driverSelect(MemberVO memberVO){
		
		return eCarMapper.driverSelect(memberVO);
	}

	@Override
	public MemberVO driverCheck(MemberVO memberVO) {
		
		return eCarMapper.driverCheck(memberVO);
	}

	@Override
	public TokenVO tokenSelect(TokenVO tokenVO) {
		
		return eCarMapper.tokenSelect(tokenVO);
	}

	@Override
	public void tokenUpdate(TokenVO tokenVO) {
		
		eCarMapper.tokenUpdate(tokenVO);
	}

	@Override
	public void tokenInsert(TokenVO tokenVO) {
		
		eCarMapper.tokenInsert(tokenVO);
	}

	@Override
	public List<CodeVO> agencyList() {
		
		return eCarMapper.agencyList();
	}

	@Override
	public List<CodeVO> purposeList(String groupCodeId) {
		
		return eCarMapper.purposeList(groupCodeId);
	}

	@Override
	public List<CarVO> carList() {

		return eCarMapper.carList();
	}

	@Override
	public void driveStartInsert(DriveInfoVO driveInfoVO) {
		
		eCarMapper.driveStartInsert(driveInfoVO);
	}
	
	@Override
	public void carStatusStart(String carNumber) {
		
		eCarMapper.carStatusStart(carNumber);
	}

	@Override
	public void driveEndInsert(DriveInfoVO driveInfoVO) {
		
		eCarMapper.driveEndInsert(driveInfoVO);
	}

	@Override
	public void carStatusEnd(String driveKey) {
		
		eCarMapper.carStatusEnd(driveKey);
	}

	@Override
	public void DriveGPSInsert(DriveGPSVO driveGPSVO) {
		
		eCarMapper.DriveGPSInsert(driveGPSVO);
	}

	@Override
	public List<NoticeVO> noticeList(NoticeVO noticeVO) {
		
		return eCarMapper.noticeList(noticeVO);
	}

	@Override
	public NoticeVO noticeDetail(NoticeVO noticeVO) {
		
		return eCarMapper.noticeDetail(noticeVO);
	}

	@Override
	public NoticeVO noticePopup(NoticeVO noticeVO) {
		
		return eCarMapper.noticePopup(noticeVO);
	}

	@Override
	public void pushInsert(TokenVO tokenVO) {
		
		eCarMapper.pushInsert(tokenVO);
	}

	@Override
	public void pushOnOff(TokenVO tokenVO) {
		
		eCarMapper.pushOnOff(tokenVO);
	}

	
}
