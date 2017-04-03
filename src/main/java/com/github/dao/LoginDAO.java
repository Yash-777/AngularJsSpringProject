package com.github.dao;

import com.github.dto.LoginDTO;
import com.github.dto.RegistrationDTO;

public interface LoginDAO {
	public boolean loginChectk(LoginDTO loginDto);
	public boolean forgotPasswordToken(String emailID);
	public boolean checkEmail_InsertDetails(RegistrationDTO registerDto);
}
