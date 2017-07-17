package com.github.dao;

import java.util.Random;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.github.dto.LoginDTO;
import com.github.dto.RegistrationDTO;

/**
 * 
 * ORM: http://docs.spring.io/spring/docs/current/spring-framework-reference/html/orm.html
 * @author yashwanth.m
 *
 */
@Repository
public class LoginDAO_Impl implements LoginDAO {
	
	public LoginDAO_Impl() {
		System.out.println("LoginDAO_Impl :: Object Created ");
	}
	
	private MongoFilesDAO_Impl mongoDao;
	public MongoFilesDAO_Impl getMongoDao() {
		return mongoDao;
	}
	public void setMongoDao(MongoFilesDAO_Impl mongoDao) {
		System.out.println("Mongo Dao : "+mongoDao);
		this.mongoDao = mongoDao;
	}
	
	private JdbcTemplate jt;
	
	public JdbcTemplate getJt() {	return jt;	}
	public void setJt(JdbcTemplate jt) {
		System.out.println("Jdbc Template : "+jt);
		this.jt = jt;
	}
	
	public String getUserName(LoginDTO loginDto){
		
		String query ="SELECT `USER_NAME` FROM `user` WHERE `EMAIL` ='"+loginDto.getEmail()+"' AND `PASSWORD` = '"+loginDto.getPassword()+"' ";
		System.out.println("JT: "+jt);
		try { // Incorrect result size: expected 1, actual 0 - For Incorrect Details
			String username = jt.queryForObject(query, String.class);
			if( username != null ) {
				loginDto.toString();
				loginDto.setUserName( username );
				loginDto.toString();
				return username;
			}
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
		}
		return "";
	}
	
	/**
	 * SELECT * FROM `user` WHERE `EMAIL` = "yashwanth.merugu@gmail.com" AND `PASSWORD` = 'Yash@123'
	 */
	public boolean loginChectk(LoginDTO loginDto){
		
		String query ="SELECT `USER_NAME` FROM `user` WHERE `EMAIL` ='"+loginDto.getEmail()+"' AND `PASSWORD` = '"+loginDto.getPassword()+"' ";
		System.out.println("JT: "+jt);
		try { // Incorrect result size: expected 1, actual 0 - For Incorrect Details
			String username = jt.queryForObject(query, String.class);
			if( username != null ) {
				loginDto.toString();
				loginDto.setUserName( username );
				loginDto.toString();
				return true;
			}
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
		}
		return false;
	}
	
	public boolean isEmailIDUsed(String emailID){
		
		String query ="SELECT `EMAIL` FROM `user` WHERE `EMAIL` ='"+emailID+"'";
		System.out.println("JT: "+jt);
		try { // Incorrect result size: expected 1, actual 0 - For Incorrect Details
			String username = jt.queryForObject(query, String.class);
			if( username != null ) {
				return true;
			}
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
		}
		return false;
	}
	
	//  Field 'HASH_PASSWORD_TOKEN' doesn't have a default value
	@Override
	public boolean checkEmail_InsertDetails(RegistrationDTO registerDto) {
		
		if( !isEmailIDUsed( registerDto.getEmail() ) ) {
			String query ="INSERT INTO `user`(`USER_NAME`, `FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`) "
					+ "VALUES ('"+registerDto.getUserName()+"','"+registerDto.getFirstName()+"','"
					+registerDto.getLastName()+"','"+registerDto.getEmail()+"','"+registerDto.getPassword()+"')";
			jt.update(query);
			return true;
		}
		return false;
	}
	
	/** Set password / expiration:
	 * The shared link expires at 11:59 PM on the day you choose, based on your time zone.
	 */
	static Random random = new Random();
	@Override
	public boolean forgotPasswordToken(String emailID) {
		if( isEmailIDUsed( emailID ) ) {
			Integer id = random.nextInt( Integer.MAX_VALUE );
			String query ="UPDATE `user` SET `HASH_PASSWORD_TOKEN`='"+id.toString()+"',"
					+ "`CHANGE_PASSWORD`='true',`FORGET_PASSWORD_URL`= 'null' WHERE `EMAIL` = '"+emailID+"'";
			jt.update(query);
			return true;
		}
		return false;
	}
}