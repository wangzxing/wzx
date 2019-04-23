package com.cn.wzx.user.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cn.common.util.CookieUtils;
import com.cn.common.util.StringUtils;
import com.cn.web.shiro.UserInfo;
import com.cn.web.shiro.UserUtils;
import com.cn.web.shiro.filter.FormAuthenticationFilter;

@Controller
@RequestMapping("/")
public class LoginController {
	 
	/**
	 * 管理登录
	 */
	
	@RequestMapping(value = "/user/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request,
			HttpServletResponse response, Model model) {
	 System.out.println("sddd");
		return "login";
	}

	@RequestMapping(value = "/user")
	public String login(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		 
		UserInfo user = UserUtils.getUser();
		if (!StringUtils.isEmpty(user.getUserName())) {
			try {
// 保存用户信息-------将用户信息放入session中-----start---
				
			/*	Subject currentUser = SecurityUtils.getSubject();
				Session session = currentUser.getSession();
				session.setAttribute(Global.TEXT_USERINFO, user);
				*/
				model.addAttribute("user", user.getUserName());

			} catch (Exception e) {
				e.printStackTrace();
			}
			return "showUser";
		}
		return "login";
	}

	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	public String err(Model model, HttpServletRequest request) {
		String message = (String) request
				.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM,
				message);
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
		}

		return "login";
	}
	/**
	 * 保存用户名
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private Cookie saveCookie(String name, HttpServletRequest request,
			HttpServletResponse response) {
		Cookie ck = CookieUtils.getCookieByName(request, "HRPT_USER_ID");
		if (null == ck) {
			ck = new Cookie("HRPT_USER_ID", name);
		}

		ck.setValue(name);
		ck.setMaxAge(365 * 24 * 60 * 60);

		ck.setPath("/");
		response.addCookie(ck);
		return ck;
	}
}
