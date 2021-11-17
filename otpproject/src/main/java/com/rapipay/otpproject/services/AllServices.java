package com.rapipay.otpproject.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rapipay.exceptions.EmailNotFoundException;
import com.rapipay.exceptions.OtpInvalidException;
import com.rapipay.exceptions.ResourceNotFoundException;
import com.rapipay.exceptions.TimeOutException;
import com.rapipay.otpproject.dao.EmailOtp;
import com.rapipay.otpproject.entity.Email;

public interface AllServices {
	
	 List<Email> getAllEmail();
	
	 Email getByEmail(String email) throws ResourceNotFoundException ;
	 

		String addEmail(Email email) throws EmailNotFoundException ;

		String OtpValidate(EmailOtp eo)
				throws ResourceNotFoundException, OtpInvalidException, TimeOutException, EmailNotFoundException ;
	 
	 
	 
	 
	
	

}

