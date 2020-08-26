package com.uinetworks.ecar.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uinetworks.ecar.mapper.MemberMapper;
import com.uinetworks.ecar.model.AuthVO;
import com.uinetworks.ecar.model.MemberVO;
import com.uinetworks.ecar.security.CustomUserDetailsService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class MemberServiceImpl implements MemberService {
	
	@Setter(onMethod_ = @Autowired)
	private MemberMapper mapper;
	
	@Override
	@Transactional
	public void register(MemberVO member, AuthVO auth) {
		mapper.insertMember(member);
		mapper.insertAuth(auth);
		
	}

	@Override
	@Transactional
	public boolean modify(MemberVO member, AuthVO auth) {
		mapper.updateMember(member);
		return mapper.updateAuth(auth) == 1;
	}

	@Override
	public MemberVO getMember(String userid) {
		return mapper.read(userid);
	}

	@Override
	public List<MemberVO> getMemberList() {
		return mapper.readList();
	}

	@Override
	@Transactional
	public boolean delete(String userid) {
		mapper.deleteAuth(userid);
		return mapper.deleteMember(userid) == 1;
	}
	
}
