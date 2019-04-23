package com.cn.web.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Service;

import com.cn.common.util.StringUtils;

/**
 * 
 * <pre>
 * <dt><b>类名：FormAuthenticationFilter</b></dt>
 * <dt><b>描述：</b></dt>
 * <dd>表单验证（包含验证码）过滤类</dd>
 * <dt><b>日期：2017-1-9</b></dt>
 * </pre>
 * 
 * @author wangzexing
 */

@Service
public class FormAuthenticationFilter extends
org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

	public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";// 验证码
	public static final String DEFAULT_MOBILE_PARAM = "mobileLogin"; // 手机登录
	public static final String DEFAULT_MESSAGE_PARAM = "message";//登陆错误提示

	private String captchaParam = DEFAULT_CAPTCHA_PARAM;
	private String mobileLoginParam = DEFAULT_MOBILE_PARAM;
	private String messageParam = DEFAULT_MESSAGE_PARAM;
	public String username = "";

	@Override
	protected AuthenticationToken createToken(ServletRequest request,
			ServletResponse response) {
		username = getUsername(request);
		String password = getPassword(request);
		if (password == null) {
			password = "";
		}
		boolean rememberMe = isRememberMe(request);
		String host = StringUtils.getRemoteAddr((HttpServletRequest) request);
		String captcha = getCaptcha(request);
		boolean mobile = isMobileLogin(request);
		return new UsernamePasswordToken(username, password.toCharArray(),
				rememberMe, host, captcha, mobile);
	}

 

	/**
	 * 登录失败调用事件
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		UsernamePasswordToken to = (UsernamePasswordToken) token;	
		String className = e.getClass().getName(), message = "";//org.apache.shiro.authc.IncorrectCredentialsException
		if(IncorrectCredentialsException.class.getName().equals(className)){
			message = "用户不存在";
		} else if (UnknownAccountException.class.getName().equals(className)&&to.getLoginNum()>0) {
			message = "用户或密码错误,"+to.getLoginNum()+"次后将锁定用户";
		} else if (LockedAccountException.class.getName().equals(className)) {
			message = "账户已被锁定，请联系管理员解除锁定！";
		} else if (ExcessiveAttemptsException.class.getName().equals(className)) {
			message = "登录失败多次，账户锁定10分钟！";
		} else if (to.getLoginNum() ==0) {	
			message = "账户已被锁定，请联系管理员解除锁定！";
		} else if (AuthenticationException.class.getName().equals(className)) {
			if(StringUtils.isAllChinese(e.getMessage())) {
				message =  e.getMessage();
			}else{
				message = "登录失败，请联系管理员";
			}
		} else {

			message = "系统出现问题，请稍后再试！";
			e.printStackTrace(); // 输出到控制台
		}
		request.setAttribute(getFailureKeyAttribute(), className);
		request.setAttribute(getMessageParam(), message);
		return true;
	}

	public String getCaptchaParam() {
		return captchaParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	public String getMobileLoginParam() {
		return mobileLoginParam;
	}

	protected boolean isMobileLogin(ServletRequest request) {
		return WebUtils.isTrue(request, getMobileLoginParam());
	}

	public String getMessageParam() {
		return messageParam;
	}
}