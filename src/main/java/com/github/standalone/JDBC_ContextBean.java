package com.github.standalone;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.dao.LoginDAO;
import com.github.dto.LoginDTO;

/**
 * StandAlone application to communicate with DataBase.
 * @author yashwanth.m
 *
 */
public class JDBC_ContextBean {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("webApplicationContext/applicationContext.xml");

		LoginDAO login = (LoginDAO) context.getBean("loginDao");
		LoginDTO user = new LoginDTO();
		user.setEmail("yashwanth.m@gmail.com");
		user.setPassword("Yash@777");
		
		boolean loginChectk = login.loginChectk(user);
		System.out.println("Valid User : "+loginChectk);
		
		if (loginChectk) {
			String userName = login.getUserName(user);
			System.out.println("User Name : "+userName);
		}
	}
}
