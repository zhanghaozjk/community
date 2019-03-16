package com.hcven.community;

import com.hcven.community.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommunityApplicationTests {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private UserService userService;

	@Test
	public void contextLoads() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("service@hcven.cn");
		message.setTo("zhanghaozjk@outlook.com");
		message.setSubject("邮件测试");
		message.setText("测试");

		mailSender.send(message);
	}

	@Test
	public void checkEmailExists() {
		String email = "zhanghaozjk@outlook.com";
		System.out.println(userService.checkEmailExist(email));
	}
}
