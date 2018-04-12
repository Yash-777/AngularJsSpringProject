package com.github.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * https://avaldes.com/wp-content/uploads/2015/05/spring_interceptor.png?2d262d&2d262d
 * https://avaldes.com/spring-mvc-interceptor-using-handlerinterceptoradapter-example/
 * http://www.journaldev.com/2676/spring-mvc-interceptor-example-handlerinterceptor-handlerinterceptoradapter
 * 
 * @author yashwanth.m
 *
 */
@Component
public class TimeInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
		Object handler) throws Exception {
		
		long time = System.currentTimeMillis();
		request.setAttribute("time", time);
		System.out.println("Time : "+time);
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
		Object handler, ModelAndView modelAndView) throws Exception {
		
		System.out.println("Request URL::" + request.getRequestURL().toString()
				+ " Sent to Handler :: Current Time=" + System.currentTimeMillis());
		
		long totalTime = System.currentTimeMillis() - (Long)request.getAttribute("time");
		modelAndView.addObject("totalTime", totalTime);
		System.out.println(" post handle method, totalTime passed: " + totalTime + "ms" );
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
		Object handler, Exception ex) throws Exception {
		
		super.afterCompletion(request, response, handler, ex);
		System.out.println("####### after comletion");
	}
}
