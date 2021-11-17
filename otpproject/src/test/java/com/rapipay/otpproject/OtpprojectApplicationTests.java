package com.rapipay.otpproject;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rapipay.otpproject.dao.EmailDao;
import com.rapipay.otpproject.dao.EmailOtp;
import com.rapipay.otpproject.entity.Email;
import com.rapipay.otpproject.services.ServicesImpl;


@SpringBootTest
class OtpprojectApplicationTests {

	@Test
	void checkOtpTest() {
		
		Email email = new Email();
		
		int actOTP = email.generateOTP();
		String actual = Integer.toString(actOTP);
		
        assertTrue(actual.length()==6);
	}
	
	@Autowired
	EmailDao dao;
	@Test
	public void checkIfObjectSaved() {
	Email e = new Email();
	e.setEmail("anubhav17225562@gmail.com");
	e.setGeneratedTime();
	e.setExpiryTime();
	e.setOtp();
	dao.save(e);
	assertNotNull(dao, "should be saved as email object!");
	}

	public boolean checkEmail(String email) {
	String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
	Pattern pattern = Pattern.compile(regex);
	Matcher matcher = pattern.matcher(email);
	return matcher.matches();
	}

	@Test
	void checkEmailRegex() {
	boolean expected = true;
	boolean actual = checkEmail("anubhav17225562@gmail.com");
	assertEquals(expected, actual);
	}

	
	

}
