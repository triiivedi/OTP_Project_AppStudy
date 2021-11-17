package com.rapipay.otpproject.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.rapipay.otpproject.entity.Email;
import com.rapipay.exceptions.EmailNotFoundException;
import com.rapipay.exceptions.OtpInvalidException;
import com.rapipay.exceptions.ResourceNotFoundException;
import com.rapipay.exceptions.TimeOutException;
import com.rapipay.otpproject.dao.EmailDao;
import com.rapipay.otpproject.dao.EmailOtp;

@Service
public class ServicesImpl implements AllServices {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private EmailDao emaildao;

	@Override
	public List<Email> getAllEmail() {

		return emaildao.findAll();
	}

	@Override
	public Email getByEmail(String email) throws ResourceNotFoundException {

		return emaildao.findById(email).orElseThrow(() -> new ResourceNotFoundException(" Email not found"));
	}

	@Override
	public String addEmail(Email email) throws EmailNotFoundException {
		Email x = emaildao.save(email);
		sendEmail(email.getEmail(), email.getOtp());
		if (x != null)
			return "OTP Sent";
		else
			throw new EmailNotFoundException();
	}

	void sendEmail(String email, int otp) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(email);
		msg.setSubject("RAPIPAY");
		msg.setText(otp + " sent from Anubhav's PC, this OTP will expire in 1 minutes ");
		javaMailSender.send(msg);
	}

	@Override
	public String OtpValidate(EmailOtp eo)
			throws ResourceNotFoundException, OtpInvalidException, TimeOutException, EmailNotFoundException {

		Email emailData = getByEmail(eo.getEmail());

		if (emailData == null) {
			throw new EmailNotFoundException();
		} else {
			if (emailData.getOtp() == (eo.getOtp())) {
				LocalDateTime start = emailData.getGeneratedTime();
				LocalDateTime end = emailData.getExpiryTime();
				LocalDateTime curr = LocalDateTime.now();
				System.out.println(ChronoUnit.MINUTES.between(start, curr));
				System.out.println(ChronoUnit.MINUTES.between(curr, end));
				if (ChronoUnit.MINUTES.between(start, curr) <= 10 && ChronoUnit.MINUTES.between(curr, end) >= 0) {
					return "OTP Validated";
				} else

				{
					throw new TimeOutException();
				}

			} else {
				throw new OtpInvalidException();
			}
		}
	}
}
