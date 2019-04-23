package com.cn.web.shiro.filter;

/**
 * 用户和密码（包含验证码）令牌类
 * 
 * @author wang_zxing
 * @version 2016-12-13
 */
public class UsernamePasswordToken extends
		org.apache.shiro.authc.UsernamePasswordToken {

	private static final long serialVersionUID = 1L;

	private String captcha;
	private boolean mobileLogin;
	private boolean isRemember = false;
	private int loginNum =0;

	public UsernamePasswordToken() {
		super();
	}

	/*
	 * public UsernamePasswordToken(String username, char[] password, boolean
	 * rememberMe, String host) { super(username, password, rememberMe, host); }
	 */
	public UsernamePasswordToken(String username, char[] password,
			boolean rememberMe, String host, String captcha, boolean mobileLogin) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
		this.mobileLogin = mobileLogin;
		this.isRemember = rememberMe;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public boolean isMobileLogin() {
		return mobileLogin;
	}

	public boolean isRemember() {
		return isRemember;
	}

	public void setRemember(boolean isRemember) {
		this.isRemember = isRemember;
	}

	public int getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(int loginNum) {
		this.loginNum = loginNum;
	}
	
}