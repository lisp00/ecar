package com.uinetworks.ecar.service;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.uinetworks.ecar.model.AuthVO;
import com.uinetworks.ecar.model.MemberVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@Log4j
public class MemberServiceTests {

	@Setter(onMethod_ = @Autowired)
	private MemberService service;
	
//	@Test
	public void registerTest() {
		AuthVO authVo = new AuthVO();
		authVo.setUserid("test");
		authVo.setAuth("ROLE_USER");
		
		MemberVO vo = new MemberVO();
		vo.setUserid("test");
		vo.setPassword("1234");
		vo.setService("");
		
		service.register(vo, authVo);
	}
	
//	@Test
	public void updateTest() {
		AuthVO authVo = new AuthVO();
		authVo.setUserid("test");
		authVo.setAuth("ROLE_MANAGER");
		
		MemberVO vo = new MemberVO();
		vo.setUserid("test");
		vo.setPassword("5678");
		vo.setService("A");
		
		service.modify(vo, authVo);
	}	
	
//	@Test
	public void readListTest() {
		service.getMemberList().forEach(member -> log.info(member));
	}
//	@Test
	public void readTest() {
		log.info(service.getMember("admin"));
	}
	
//	@Test
	public void deleteTest() {
		log.info(service.delete("test"));
	}
}
