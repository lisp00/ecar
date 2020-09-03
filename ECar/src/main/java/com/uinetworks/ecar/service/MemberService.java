package com.uinetworks.ecar.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.uinetworks.ecar.model.AuthVO;
import com.uinetworks.ecar.model.MemberVO;

public interface MemberService {
	public void register(MemberVO member, AuthVO auth);
	
	public boolean modify(MemberVO member, AuthVO auth);
	
	public MemberVO getMember(String userid);
	
	public List<MemberVO> getMemberList();
	
	public boolean delete(String userid); 
	
}
