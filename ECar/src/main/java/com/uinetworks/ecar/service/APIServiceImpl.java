package com.uinetworks.ecar.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.uinetworks.ecar.mapper.APIMapper;
import com.uinetworks.ecar.mapper.MemberMapper;
import com.uinetworks.ecar.model.AuthVO;
import com.uinetworks.ecar.model.CarVO;
import com.uinetworks.ecar.model.CodeVO;
import com.uinetworks.ecar.model.CustomUser;
import com.uinetworks.ecar.model.DriveGPSVO;
import com.uinetworks.ecar.model.DriveInfoVO;
import com.uinetworks.ecar.model.LoginDTO;
import com.uinetworks.ecar.model.NoticeVO;
import com.uinetworks.ecar.model.TokenVO;
import com.uinetworks.ecar.model.MemberVO;
import com.uinetworks.ecar.model.VersionVO;
import com.uinetworks.ecar.security.CustomUserDetailsService;
import com.uinetworks.ecar.security.JwtTokenProvider;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class APIServiceImpl implements APIService {

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Setter(onMethod_= @Autowired)
	CustomUserDetailsService customUserDetailsService;

	@Autowired
	APIMapper eCarMapper;

	@Setter(onMethod_= @Autowired)
	MemberMapper memberMapper;

	@Setter(onMethod_= @Autowired)
	BCryptPasswordEncoder encoder;

	@Override
	public VersionVO versionSelect() {

		return eCarMapper.versionSelect();
	}

	@Override
	public ResponseEntity<Map<String, Object>> login(LoginDTO loginDTO) {
		log.info("Service -> login()");
		CustomUser user = (CustomUser) customUserDetailsService.loadUserByUsername(loginDTO.getUserId());
		Map<String, Object> result = new HashMap<String, Object>();

		if(user != null && encoder.matches(loginDTO.getUserPw(), user.getPassword())) {
			Collection<? extends GrantedAuthority> authoritiesCollection = new HashSet<>(user.getAuthorities());
			ArrayList<? extends GrantedAuthority> authoritiesList = new ArrayList<>(authoritiesCollection);

			String token = jwtTokenProvider.createToken(user.getUsername(), authoritiesList);
			log.info("Generated Token : " + user.getUsername() + "/" + token);
			if (user.getMember().getAuthorization().equals("0")) {
				result.put("code", 202);
				result.put("error", false);
				result.put("msg", "가입승인 대기중 또는 거절");
				result.put("Authorization", token);
				return new ResponseEntity<>(result, HttpStatus.ACCEPTED);

			} else {
				result.put("code", 200);
				result.put("error", false);
				result.put("msg", "OK");
				result.put("accesstoken", token);
				return new ResponseEntity<>(result, HttpStatus.OK);
			}
		}
		else {
			result.put("code", 201);
			result.put("error", true);
			result.put("msg", "아이디 또는 비밀번호 불일치");
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		}
	}

	@Override
	public ResponseEntity<Map<String, Object>> loginCheck(HttpServletRequest request) {
		boolean check = false;
		log.info("LoginCheck in : " + request);
		String userId = null;
		String token = jwtTokenProvider.resolveToken(request);
		if(jwtTokenProvider.validateToken(token)) {
			userId = jwtTokenProvider.getUserId(token);
		} else {
			log.info("token invalidation");
		}
		CustomUser user = (CustomUser) customUserDetailsService.loadUserByUsername(userId);
		user.getMember().getAuthorization();

		Map<String, Object> result = new HashMap<String, Object>();

		if(check==false) {
			result.put("code", 202);
			result.put("msg", "Invalid Session");

			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}

		boolean survey = true;
		if(survey == false) {
			result.put("code", 201);
			result.put("msg", "ok");
			result.put("survey", survey);

			return new ResponseEntity<>(result, HttpStatus.CREATED);
		} else {
			result.put("code", 200);
			result.put("msg", "ok");
			result.put("survey", survey);

			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}	

	@Override
	@Transactional
	public ResponseEntity<Map<String,Object>> driverInsert(MemberVO memberVO) {
		log.info("in driverInsert");
		Map<String, Object> result = new HashMap<String, Object>();
		AuthVO auth = new AuthVO();
		auth.setUserid(memberVO.getUserid());
		auth.setAuth("ROLE_USER");
		
		String encodedPw = encoder.encode(memberVO.getPassword());
		memberVO.setPassword(encodedPw);
		try {
			if(memberMapper.insertMember(memberVO) != 0 && memberMapper.insertAuth(auth) !=0) {
				CustomUser user = (CustomUser) customUserDetailsService.loadUserByUsername(memberVO.getUserid());
				Collection<? extends GrantedAuthority> authoritiesCollection = new HashSet<>(user.getAuthorities());
				ArrayList<? extends GrantedAuthority> authoritiesList = new ArrayList<>(authoritiesCollection);
				String token = jwtTokenProvider.createToken(user.getUsername(), authoritiesList);
				result.put("code", 200);
				result.put("error", false);
				result.put("msg", "OK");
				result.put("accesstoken", token);
				return new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				result.put("code", 406);
				result.put("error", false);
				result.put("msg", "Regstring failed");
				return new ResponseEntity<>(result, HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("code", 403);
		result.put("error", false);
		result.put("msg", "Duplicate entry");
		return new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
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
	public boolean tokenCheck(HttpServletRequest request) {
		String token = jwtTokenProvider.resolveToken(request);
		return jwtTokenProvider.validateToken(token);
	}
}
