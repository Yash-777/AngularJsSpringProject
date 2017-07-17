package com.github.service;

import java.util.HashMap;
import java.util.Map;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;


/**
 * MailService class is to send mail using JavaMail API.
 * @author yashwanth.m
 *
 */
@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender mailService;
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	public void sendMail(final String from, final String to, final String subject ){
		
		try {
			
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws Exception {
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true);
					System.out.println("EX: yashwanth.merugu@gmail.com:Yash");
					String temp[]=to.split(":");
			
					message.setTo(temp[0]);
					message.setFrom(from);
					message.setSubject(subject);
			
					String vmFileName = "mailtemplate.vm";
					Map<String, String> model = new HashMap<String, String>();
					model.put("userName", temp[1]);
					String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, vmFileName, model);
					
					System.out.println("==============================");
					System.out.println("==============================");
					message.setText(text, true);
				}
			};

			MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
			mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
			mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
			mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
			mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
			mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
			CommandMap.setDefaultCommandMap(mc);
			
			System.out.println("Mail is ready to send...");
			
			this.mailService.send(preparator);
			
			System.out.println("Mail sent successfully.");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}