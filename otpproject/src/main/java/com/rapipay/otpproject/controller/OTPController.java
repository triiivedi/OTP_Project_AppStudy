package com.rapipay.otpproject.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rapipay.exceptions.EmailNotFoundException;
import com.rapipay.exceptions.OtpInvalidException;
import com.rapipay.exceptions.ResourceNotFoundException;
import com.rapipay.exceptions.TimeOutException;
import com.rapipay.otpproject.dao.EmailOtp;
import com.rapipay.otpproject.entity.Email;
import com.rapipay.otpproject.services.AllServices;

@RestController
public class OTPController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OTPController.class);

	@SuppressWarnings("unused")
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private AllServices allservices;

	@CrossOrigin
	@GetMapping("/email")
	public List<Email> getAllEmail() {
		return allservices.getAllEmail();
	}

	@CrossOrigin
	@GetMapping("/email/{email}")
	public Email getByEmail(@PathVariable String email) throws ResourceNotFoundException, EmailNotFoundException {

		return allservices.getByEmail(email);

	}

	@CrossOrigin
	@PostMapping("/email")
	public String addEmail(@RequestBody Email email) {
		LOGGER.info("for informational purpose");
		email.setGeneratedTime();
		email.setExpiryTime();
		email.setOtp();
		try {
			return this.allservices.addEmail(email);
		} catch (EmailNotFoundException e) {
			// TODO Auto-generated catch block
			return e.toString();
		}

	}

	@CrossOrigin
	@PostMapping("/otp-validate")
	public String otpValidate(@RequestBody EmailOtp eo)
			throws ResourceNotFoundException, OtpInvalidException, TimeOutException, EmailNotFoundException {
		try {
			return this.allservices.OtpValidate(eo);
		} catch (OtpInvalidException e) {
			return e.toString();
		} catch (TimeOutException te) {
			return te.toString();
		} catch (EmailNotFoundException ee) {
			return ee.toString();
		}

	}
}
