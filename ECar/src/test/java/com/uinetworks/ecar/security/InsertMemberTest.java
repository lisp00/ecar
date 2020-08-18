package com.uinetworks.ecar.security;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@Log4j
public class InsertMemberTest {
	@Setter(onMethod_ = @Autowired)
	private PasswordEncoder pwencoder;	
	
	@Setter(onMethod_ = @Autowired)
	private DataSource ds;
	
	@Test
	public void testMember() {
		String insert = "insert into tb_member(userid, password, name, service, code) values (?,?,?,?,?)";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(insert);
			pstmt.setString(1, "admin");
			pstmt.setString(2, pwencoder.encode("1234"));
			pstmt.setString(3, "관리자");
			pstmt.setString(4, "");
			pstmt.setString(5, "00");
			
			pstmt.executeUpdate();
			
			pstmt.setString(1, "manager");
			pstmt.setString(2, pwencoder.encode("1234"));
			pstmt.setString(3, "운영자");
			pstmt.setString(4, "");
			pstmt.setString(5, "00");
			
			pstmt.executeUpdate();
			
			pstmt.setString(1, "user");
			pstmt.setString(2, pwencoder.encode("1234"));
			pstmt.setString(3, "사용자");
			pstmt.setString(4, "11");
			pstmt.setString(5, "00");
			
			pstmt.executeUpdate();
			
			log.info("success");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) { try {pstmt.close(); } catch(Exception e) {} }
			if(con!= null) { try {con.close(); } catch(Exception e) {} }
		}
	}
}
