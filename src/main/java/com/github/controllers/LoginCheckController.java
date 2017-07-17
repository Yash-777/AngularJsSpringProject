package com.github.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.github.dao.LoginDAO_Impl;
import com.github.dto.LoginDTO;
import com.github.dto.RegistrationDTO;
import com.github.service.MailService;

// http://localhost:8080/AngularSpringProject/account/login
@Controller
@RequestMapping(value = "/account")
public class LoginCheckController {

	@Autowired private LoginDAO_Impl loginDao;
	
	@Autowired private MailService mailService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET )
	public ModelAndView inputFrom( Model model ) {
		System.out.println("GET... /login");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("signin"); //signinRoute | signin
		return mav;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET )
	public String signOUT( Model model, HttpServletRequest req, HttpServletResponse resp ) {
		System.out.println("GET... /logout");
		
		HttpSession session = req.getSession();
		if( !session.isNew() ) {
			session.removeAttribute("userName");
			req.getSession().invalidate();
		}
		return "redirect:/account/login";
	}
	
	/*@RequestMapping(value = "/member/{name}", method = RequestMethod.GET)
    public String displayMember(@PathVariable String name) {
        System.out.println(name);
        return "member";
    }*/
	
	@RequestMapping(value = "/userLogin", method = RequestMethod.POST )
	public void inputFromData(@RequestBody String sb, @ModelAttribute("loginDto") LoginDTO loginDto,
			HttpServletRequest request, HttpServletResponse response ) throws IOException {
		setAccessControlHeaders( response );
		
		
		/*Using writer to send a message to requested resource*/
		PrintWriter writer = response.getWriter();
		System.out.println("POST...  inputFromData");
		/*StringBuilder sb = new StringBuilder();
		BufferedReader br = request.getReader();
		String str = null;
		while ((str = br.readLine()) != null) {
			sb.append(str);
		} // SB: {"username":"yashwanth.merugu@gmail.com","password":"Yash@777"}*/
		System.out.println("SB : "+sb);
		
		JSONObject jObj = null;
		String userName = null, password = null;
		String returnMessage = "error";
		try {
			jObj = new JSONObject(sb.toString());
			userName = jObj.getString("username");
			password = jObj.getString("password");
			
			/*loginDto.setUserName(userName);*/
			loginDto.setEmail(userName);
			loginDto.setPassword(password);
			
			if( loginDao.loginChectk(loginDto) /*password.equals("Yash@777")*/ ) {
				request.getSession().setAttribute("userName", loginDto.getUserName() );
				request.getSession().setAttribute("displayName", "Yash" );
				request.getSession().setAttribute("email",loginDto.getEmail());
				returnMessage = "valid";
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println(userName+" :: "+password+"« "+returnMessage);
		writer.print(returnMessage);
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST )
	public void registrationFromData(@ModelAttribute("registerDto") @Validated RegistrationDTO registerDto,
			HttpServletRequest request, HttpServletResponse response ) throws IOException {
		setAccessControlHeaders( response );
		
		/*Using writer to send a message to requested resource*/
		PrintWriter writer = response.getWriter();
		System.out.println("POST...  registrationFromData");
		StringBuilder sb = new StringBuilder();
		BufferedReader br = request.getReader();
		String str = null;
		while ((str = br.readLine()) != null) {
			sb.append(str);
		} // SB: {"username":"yashwanth", "email":"yashwanth.merugu@gmail.com","password":"Yash@777"}
		System.out.println("SB : "+sb);
		JSONObject jObj = null;
		String userName = null, firstName = null, lastName = null, password = null, email = null;
		String returnMessage = "error";
		try {
			jObj = new JSONObject(sb.toString());
			userName = jObj.getString("username");
			firstName = jObj.getString("firstName");
			lastName = jObj.getString("lastName");
			password = jObj.getString("password");
			email = jObj.getString("email");
			
			registerDto.setEmail(email);
			registerDto.setFirstName(firstName);
			registerDto.setLastName(lastName);
			registerDto.setPassword(password);
			registerDto.setUserName(userName);
			
			if( loginDao.checkEmail_InsertDetails(registerDto) /*email.equals("yashwanth.m@gmail.com")*/ ) {
				returnMessage = "valid";
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println(userName+" :: "+password+"« "+returnMessage);
		writer.print(returnMessage);
	}
	
	@RequestMapping(value = "/forgot", method = RequestMethod.POST )
	public void forgotFromData(/*@ModelAttribute("loginDto") @Validated LoginDTO loginDto,*/
			HttpServletRequest request, HttpServletResponse response ) throws IOException {
		setAccessControlHeaders( response );
		
		/*Using writer to send a message to requested resource*/
		PrintWriter writer = response.getWriter();
		System.out.println("POST... forgotFromData");
		StringBuilder sb = new StringBuilder();
		BufferedReader br = request.getReader();
		String str = null;
		while ((str = br.readLine()) != null) {
			sb.append(str);
		} // SB: {"email":"yashwanth.merugu@gmail.com"}
		System.out.println("SB : "+sb);
		JSONObject jObj = null;
		String email = null;
		String returnMessage = "error";
		try {
			jObj = new JSONObject(sb.toString());
			email = jObj.getString("email");
			
			if( loginDao.forgotPasswordToken( email ) /*email.equals("yashwanth.merugu@gmail.com")*/ ) {
				mailService.sendMail("yashwanth.merugu@gmail.com", "yashwanth.merugu@gmail.com:Yash M", "Git Subject : Test Mail" );
				returnMessage = "valid";
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println(email+" « "+returnMessage);
		writer.print(returnMessage);
	}
	
	@RequestMapping(value = "/loginSucess", method = RequestMethod.GET )
	public ModelAndView inputSucessFrom( Model model, HttpServletRequest request, HttpServletResponse response ) {
		System.out.println("GET... inputSucessFrom");
		ModelAndView mav = new ModelAndView();
		
		String ip = request.getRemoteAddr();
		try {
			if ( ip.equalsIgnoreCase("0:0:0:0:0:0:0:1") ) {
				InetAddress inetAddress;
				inetAddress = InetAddress.getLocalHost();
				String ipAddress = inetAddress.getHostAddress();
				ip = ipAddress;
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		model.addAttribute("IP", ip);
		
		mav.setViewName("userDashboardLayout");
		return mav;
	}
	
	/**
	 * <P> Sets access control headers to allow cross-origin resource sharing from
	 * any origin.
	 * 
	 * <P> Firstly sends an HTTP "Pre-Flighted" request by the OPTIONS method 
	 * to the resource on the other domain, in order to determine whether the actual 
	 * request is safe to send.
	 * 
	 * @param response The response to modify.
	 * @see <a href="http://www.w3.org/TR/cors/">http://www.w3.org/TR/cors/</a>
	 */
	private void setAccessControlHeaders(HttpServletResponse response) {
		// An Access-Control-Allow-Origin (ACAO) header with a wild-card that allows all domains:
		response.setHeader("Access-Control-Allow-Origin", "*");
		//CORS also supports other types of HTTP requests [ GET, POST, PUT, DELETE, OPTIONS, HEAD ]
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		// The list of non-standard | custom HTTP headers.
		response.setHeader("Access-Control-Allow-Headers", "Accept, Content-Type, X-PINGOTHER, Origin, X-Requested-With");
		// Tell client that this Pre-flight info is valid for 86400 seconds is 24 hours.
		response.setHeader("Access-Control-Max-Age", "86400");
	}
}
