package com.uinetworks.ecar;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.uinetworks.ecar.model.CarVO;
import com.uinetworks.ecar.model.DriveGPSVO;
import com.uinetworks.ecar.model.DriveInfoVO;
import com.uinetworks.ecar.model.MemberVO;
import com.uinetworks.ecar.model.NoticeVO;
import com.uinetworks.ecar.model.PurposeVO;
import com.uinetworks.ecar.model.TokenVO;
import com.uinetworks.ecar.model.MemberVO;
import com.uinetworks.ecar.service.APIService;

@RestController
public class APIController {

	@Autowired
	APIService eCarService;
	

	/* 서버 상태 체크 */
	String serverStatus = "on";
	String serverSessionId = "qwe5678asd1234";

	/* 앱 정보 응답 */
	@RequestMapping(value = "/v1/version", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> version() throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();

		result.put("code", 200);
		result.put("error", false);
		result.put("msg", "OK");
		
		result.put("data", eCarService.versionSelect());

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	/* 서버 체크 */
	@RequestMapping(value = "/v1/server", method = RequestMethod.GET)
	public Map<String, Object> status(String status) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		
		String str = status;
		
		if (str == null){
			if(serverStatus.equals("on")) {
				result.put("code", 200);
				result.put("msg", "OK");
				result.put("status", serverStatus);
			} else {
				result.put("code", 200);
				result.put("msg", "OK");
				result.put("status", "service not available");
			}
		} else {
			if (status.equals("on")) {
				serverStatus = "on";
			} else if (status.equals("off")) {
				serverStatus = "off";
			} 
		}
 
		return result;
	}

	/* 기사등록 응답 */
	@RequestMapping(value = "/v1/member/register", method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> memberRegister(@RequestBody MemberVO memberVO) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();

		eCarService.driverInsert(memberVO);

		result.put("code", 200);
		result.put("error", false);
		result.put("msg", "OK");

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	/* 로그인 처리 */
	@RequestMapping(value = "/v1/login", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> login(@RequestBody MemberVO memberVO) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();
		
		MemberVO userData = eCarService.driverSelect(memberVO);
		
		if (userData == null) {
			result.put("code", 201);
			result.put("error", true);
			result.put("msg", "아이디 또는 비밀번호 불일치");

			return new ResponseEntity<>(result, HttpStatus.CREATED);

		} else if (userData.getAuthorization().equals("0")) {
			result.put("code", 202);
			result.put("error", false);
			result.put("msg", "가입승인 대기중 또는 거절");

			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
			
		} else {
			result.put("code", 200);
			result.put("error", false);
			result.put("msg", "OK");
			result.put("sessionId", serverSessionId);
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	/* 로그인 세션 체크 */
	@RequestMapping(value = "/v1/logincheck", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> loginCheck(@RequestBody TokenVO tokenVO) throws Exception {
		
		boolean check = sessionCheck(tokenVO.getSessionId());
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(check==false) {
			result.put("code", 202);
			result.put("msg", "Invalid Session");
			
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
		
		boolean survey = true;
		
		if(serverSessionId.equals(tokenVO.getSessionId()) && survey == false) {
			result.put("code", 201);
			result.put("msg", "ok");
			result.put("survey", survey);
			
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		}
		
		result.put("code", 200);
		result.put("msg", "ok");
		result.put("survey", survey);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	/* 아이디 중복체크 */
	@RequestMapping(value = "/v1/idcheck", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> idCheck(@RequestBody MemberVO memberVO) throws Exception {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		MemberVO userData = eCarService.driverCheck(memberVO);
		
		if(!(userData == null)) {
			result.put("code", 201);
			result.put("msg", "overlap");
			
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		}
		
		result.put("code", 200);
		result.put("msg", "ok");
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	/* 토큰 갱신 */
	@RequestMapping(value = "/v1/token", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> token(@RequestBody TokenVO tokenVO) throws Exception {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		TokenVO token = eCarService.tokenSelect(tokenVO);
		/* 토큰이 존재하지 않을 때*/
		if(token == null){
			
			eCarService.tokenInsert(tokenVO);
			
			result.put("code", 200);
			result.put("msg", "token changed");
			result.put("token", tokenVO.getToken());
			
			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} else if(token != null && !(token.getToken().equals(tokenVO.getToken()))) { /* 토큰이 일치하지 않을 때 */
			eCarService.tokenUpdate(tokenVO); 
			
			result.put("code", 200);
			result.put("msg", "token changed");
			result.put("token", tokenVO.getToken());
			
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		result.put("code", 200);
		result.put("msg", "ok");

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	/* 운영기관 목록 응답 */
	@RequestMapping(value = "/v1/data/agency", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> agency() throws Exception {
		
		Map<String, Object> result = new HashMap<String, Object>();

		result.put("code", 200);
		result.put("error", false);
		result.put("msg", "OK");
		result.put("agency", eCarService.agencyList());

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	/* 운행목적 목록 응답 */
	@RequestMapping(value = "/v1/data/purpose", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public ResponseEntity<Map<String, Object>> purpose(@RequestBody PurposeVO purposeVO) throws Exception {

		boolean check = sessionCheck(purposeVO.getSessionId());
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(check==false) {
			result.put("code", 400);
			result.put("error", true);
			result.put("msg", "sessionId not matched");
			
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		
		if(purposeVO.getService().equals("A")) {
			result.put("code", 200);
			result.put("error", false);
			result.put("msg", "OK");
			result.put("purpose", eCarService.purposeList("PUR1"));
			
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else if(purposeVO.getService().equals("B")) {
			result.put("code", 200);
			result.put("error", false);
			result.put("msg", "OK");
			result.put("purpose", eCarService.purposeList("PUR2"));
			
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			result.put("code", 400);
			result.put("msg", "존재하지 않는 서비스");
			
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
	}
	
	/* 차량정보 */
	@RequestMapping(value = "/v1/data/car", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> car(@RequestBody CarVO carVO) throws Exception {
		
		boolean check = sessionCheck(carVO.getSessionId());
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(check==false) {
			result.put("code", 400);
			result.put("error", true);
			result.put("msg", "sessionId not matched");
			
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		

		result.put("code", 200);
		result.put("error", false);
		result.put("msg", "OK");
		result.put("car", eCarService.carList());
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	/* 운행시작 데이터 삽입 */
	@RequestMapping(value = "/v1/drive/start", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> driveStart(@RequestBody DriveInfoVO driveInfoVO) throws Exception {

		boolean check = sessionCheck(driveInfoVO.getSessionId());
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(check==false) {
			result.put("code", 400);
			result.put("error", true);
			result.put("msg", "sessionId not matched");
			
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}

		eCarService.driveStartInsert(driveInfoVO);
		eCarService.carStatusStart(driveInfoVO.getCarNumber());
		result.put("code", 200);
		result.put("error", false);
		result.put("msg", "OK");

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	/* 운행종료 데이터 삽입 */
	@RequestMapping(value = "/v1/drive/end", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> driveEnd(@RequestBody DriveInfoVO driveInfoVO) throws Exception {

		boolean check = sessionCheck(driveInfoVO.getSessionId());
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(check==false) {
			result.put("code", 400);
			result.put("error", true);
			result.put("msg", "sessionId not matched");
			
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		
		driveInfoVO.setDistance(11.6);
		eCarService.carStatusEnd(driveInfoVO.getDriveKey());
		eCarService.driveEndInsert(driveInfoVO);
		
		if (driveInfoVO.getDistance() >= 10) {
			result.put("code", 200);
			result.put("error", false);
			result.put("msg", "OK");
			result.put("surveyLink", "https://ws.uinetworks.kr:2442/ecar/v1/survey/0.1");
		} else {
			result.put("code", 200);
			result.put("error", false);
			result.put("msg", "OK");
		}
			
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	/* 좌표 데이터 삽입 */
	@RequestMapping(value = "/v1/drive/gps", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> driveGPS(@RequestBody DriveGPSVO driveGPSVO) throws Exception {

		boolean check = sessionCheck(driveGPSVO.getSessionId());
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(check==false) {
			result.put("code", 400);
			result.put("error", true);
			result.put("msg", "sessionId not matched");
			
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		
		eCarService.DriveGPSInsert(driveGPSVO);

		result.put("code", 200);
		result.put("error", false);
		result.put("msg", "OK");

		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	/* 공지사항 목록 데이터 뿌려주기 */
	@RequestMapping(value = "/v1/notice", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public ResponseEntity<Map<String, Object>> notice(@RequestBody NoticeVO noticeVO)	throws Exception {
		
		boolean check = sessionCheck(noticeVO.getSessionId());
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(check==false) {
			result.put("code", 400);
			result.put("error", true);
			result.put("msg", "sessionId not matched");
			
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		
		result.put("code", 200);
		result.put("error", false);
		result.put("msg", "OK");
		result.put("contents", eCarService.noticeList(noticeVO));

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	/* 공지사항 데이터 뿌려주기 */
	@RequestMapping(value = "/v1/notice/detail", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public ResponseEntity<Map<String, Object>> noticeDetail(@RequestBody NoticeVO noticeVO) throws Exception {

		boolean check = sessionCheck(noticeVO.getSessionId());
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(check==false) {
			result.put("code", 400);
			result.put("error", true);
			result.put("msg", "sessionId not matched");
			
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}

		result.put("code", 200);
		result.put("error", false);
		result.put("msg", "OK");
		result.put("contents", eCarService.noticeDetail(noticeVO));

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	/* 공지사항 팝업 */
	@RequestMapping(value = "/v1/notice/popup", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public ResponseEntity<Map<String, Object>> noticePopup(@RequestBody NoticeVO noticeVO) throws Exception {

		boolean check = sessionCheck(noticeVO.getSessionId());
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(check==false) {
			result.put("code", 400);
			result.put("error", true);
			result.put("msg", "sessionId not matched");
			
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		
		result.put("code", 200);
		result.put("error", false);
		result.put("msg", "OK");
		result.put("contents", eCarService.noticePopup(noticeVO));

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	/*푸시 등록 */
	@RequestMapping(value = "/v1/push", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> push(@RequestBody TokenVO tokenVO) throws Exception {

		boolean check = sessionCheck(tokenVO.getSessionId());
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(check==false) {
			result.put("code", 400);
			result.put("error", true);
			result.put("msg", "sessionId not matched");
			
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		
		eCarService.pushInsert(tokenVO);

		result.put("code", 200);
		result.put("error", false);
		result.put("msg", "OK");
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	/* 푸시 ON/OFF */
	@RequestMapping(value = "/v1/pushonoff", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> pushOnOff(@RequestBody TokenVO tokenVO) throws Exception {

		boolean check = sessionCheck(tokenVO.getSessionId());
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(check==false) {
			result.put("code", 400);
			result.put("error", true);
			result.put("msg", "sessionId not matched");
			
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}

		eCarService.pushOnOff(tokenVO);

		result.put("code", 200);
		result.put("error", false);
		result.put("msg", "OK");
		result.put("receive", tokenVO.getReceive());

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	
	/* 에러 발생 시 응답 */
	@RequestMapping("error/{errorId}")
	public Map<String, Object> error(@PathVariable Integer errorId) {

		Map<String, Object> exception = new HashMap<String, Object>();

		int code = errorId;

		exception.put("code", code);
		exception.put("error", true);
		exception.put("msg", code + "에러");

		return exception;
	}
	
	public boolean sessionCheck(String session) {
		
		if(!(serverSessionId.equals(session))) {
			
			return false;
		}
		
		return true;
	}
}
