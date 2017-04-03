package com.github.service;

public enum MailDomain {
	USER_NAME("yashwanth.merugu@gmail.com"),
	PASSWORD("======");
	
	private String value;
	MailDomain(final String value) {	this.value = value;	}

	public String getValue() {	return value;	}
	
}
