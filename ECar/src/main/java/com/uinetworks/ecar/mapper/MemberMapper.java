package com.uinetworks.ecar.mapper;

import java.util.List;

import com.uinetworks.ecar.model.AuthVO;
import com.uinetworks.ecar.model.MemberVO;

public interface MemberMapper {
	public List<MemberVO> readList();
	
	public MemberVO read(String userid);
	
	public int insertMember(MemberVO member);
	
	public int insertAuth(AuthVO auth);
	
	public int updateMember(MemberVO member);
	
	public int updateAuth(AuthVO auth);
	
	public int deleteMember(String userid);
	
	public int deleteAuth(String userid);
}
