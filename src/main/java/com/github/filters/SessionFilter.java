package com.github.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
JavaScript & Java Session Storage:
JavaScript is executed on the client side (browser),
while the session data is stored on the server.

by this you can't modify the servers session data with JavaScript.
Sessions may contain sensitive data.

Browser Session local to client:
	sessionStorage.setItem("SessionName","SessionData");
	sessionStorage.SessionName = "SessionData";
	sessionStorage.getItem("SessionName");

Cookie are stored over client side:
using JS you can easily find many examples how to modify cookies.
 * @author yashwanth.m
 *
 */
public class SessionFilter implements Filter {
	private ArrayList<String> urlList;
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		String urls = config.getInitParameter("avoid-urls");
		StringTokenizer token = new StringTokenizer(urls, ",");
		urlList = new ArrayList<String>();
		while ( token.hasMoreTokens() ) {
			urlList.add( token.nextToken() );
		}
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
	
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String url = request.getServletPath();
		System.out.println("URL ***** "+url);
		
		ServletContext servletContext = request.getServletContext();
		String contextPath = servletContext.getContextPath();
		
		//System.out.println("Resource Path : "+ contextPath );
		// getRealPath("/") = {WorkSpace\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps\AngularSpringProject }
		
		String clientID =( String ) request.getSession().getId();
		System.out.println("Session ID : "+clientID); // B38443BF181CDB7A35A0514F8E09A753
		
		String name = req.getServletContext().getSessionCookieConfig().getName();
		System.out.println("Cookie Name : "+name );
		
		String requestName="";
		requestName =( String ) request.getSession().getAttribute("userName");
		if( !urlList.contains(url) && !url.contains(".") && requestName == null ) {
			response.sendRedirect(contextPath+"/account/login");
		}
		chain.doFilter(request, response);
	}
	
	@Override
	public void destroy() {
	}
}
