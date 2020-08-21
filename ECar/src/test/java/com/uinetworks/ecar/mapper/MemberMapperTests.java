package com.uinetworks.ecar.mapper;


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
public class MemberMapperTests {
	
	@Setter(onMethod_ = @Autowired)
	private MemberMapper mapper;
	
//	@Test
	public void testInsertMember() {
		AuthVO authVo = new AuthVO();
		authVo.setUserid("test");
		authVo.setAuth("ROLE_USER");
		
		MemberVO vo = new MemberVO();
		vo.setUserid("test");
		vo.setPassword("1234");
		vo.setService("");
		vo.setToken("token");
		
		mapper.insertMember(vo);
		mapper.insertAuth(authVo);
		
	}
	
//	@Test
	public void testRead() {
		MemberVO vo = mapper.read("admin");
		
		log.info(vo);
		
		vo.getAuthList().forEach(authVO -> log.info(authVO));
	}
	
	@Test
	public void testReadList() {
		mapper.readList().forEach(member -> log.info(member));;
	}
	
//	@Test
	public void testUpdateMember() {
		MemberVO vo = new MemberVO();
		vo.setUserid("test");
		vo.setPassword("1235");
		vo.setService("1");
		vo.setToken("to2312ken");
		
		log.info(mapper.updateMember(vo));
	}
	
//	@Test
	public void testUpdateMemberAuth() {
		AuthVO authVo = new AuthVO();
		authVo.setUserid("test");
		authVo.setAuth("ROLE_ADMIN");
		
		log.info(mapper.updateAuth(authVo));
	}
	
	
//	@Test
	public void testDeleteMemberAuth() {
		log.info(mapper.deleteAuth("test"));
	}

//	@Test
	public void testDeleteMember() {
		log.info(mapper.deleteMember("test"));
	}
}
