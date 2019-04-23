package com.cn.web.shiro.realm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.common.util.StringUtils;
import com.cn.common.util.config.Global;
import com.cn.web.shiro.UserInfo;
import com.cn.web.shiro.filter.UsernamePasswordToken;
import com.cn.wzx.user.dao.UserDao;
 
 

@Service(value = "userRealm")
public class UserRealm extends AuthorizingRealm {

	 

	UserInfo user;
	@Autowired
	private UserDao userDAO;

	/**
	 * 权限认证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		try {
			user = UserInfo.getInstance();
		} catch (Exception e) {

			e.printStackTrace();
		}

		List<String> roles = new ArrayList<String>(); // 角色
		// List<Resource> reses = userService.listAllResource(uid);

		// System.out.println("这里经过权限认证:当前登录人权限："+reses);
		List<String> permissions = new ArrayList<String>();// 权限

	 
		 
			String[] strs = StringUtils.stringToArray(user.getRight(), "$");
			for (String str : strs) {
				permissions.add(str);
		 
			}

		roles.add("admin");
		roles.add("user");
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setRoles(new HashSet<String>(roles));
		info.setStringPermissions(new HashSet<String>(permissions));
		return info;
	}

	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		// checkPollInfo();//检测平台是否过期
		UsernamePasswordToken to = (UsernamePasswordToken) token;
		to.setLoginNum(-1);

		String userCode = token.getPrincipal().toString();
 
		// 查找人员,用户信息设置
		UserInfo user = null;
		try {
			user = userDAO.selectByName(userCode);
			
		} catch (Exception e1) {
			throw new IncorrectCredentialsException();
		}

		if (user == null)
			throw new IncorrectCredentialsException();
		// 当前操作员
		if (user.getType().equals(Global.SYS_ADMIN)) {
			user.setIsAdmin(true);
		}
		user.setId(user.getUserName());

		// 判断用户是否被锁定
		if (StringUtils.isNotBlank(user.getAccount())
				&& "1".equals(user.getAccount()))
			throw new LockedAccountException("账户已被锁定，请联系管理员解除锁定！");
		 
	 

		// 限制登录次数 //
		/*
		 * 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配,shiro-bean
		 * .xml中<凭证匹配器>需要与数据库密码加密情况保持一致
		 */
	 
		 
	 
	 
		 
	 
		 
	 
		 
		user.setRemember(((UsernamePasswordToken) token).isRemember());// 是否记住用户名
		if (user.getType().equals(Global.SYS_ADMIN)) {
			user.setIsAdmin(true);
		}

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
				user, // 用户名
				user.getPassword(), // 密码
				ByteSource.Util.bytes(""),
				"UserRealm" // realm name
		);

		/*
		 * SimpleAuthenticationInfo info = new
		 * SimpleAuthenticationInfo(user, password, "UserRealm");
		 * info.setCredentialsSalt(ByteSource.Util.bytes(username));
		 */
		return info;
	}

	/*	*//**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	/*
	 * public static class ShiroUser implements Serializable {
	 * 
	 * private static final long serialVersionUID = -1748602382963711884L;
	 * private String loginName; private String name;
	 * 
	 * public ShiroUser(String loginName, String name) { this.loginName =
	 * loginName; this.name = name; }
	 * 
	 * public String getLoginName() { return loginName; }
	 */
	/**
	 * /** 本函数输出将作为默认的&lt;shiro:principal/&gt;输出.
	 */
	/*
	 * @Override public String toString() { return loginName; }
	 * 
	 * public String getName() { return name; }
	 */
	/**
	 * 清空缓存，重新获取用户的角色信息
	 */
	/*
	 * public void clearAuthz(){
	 * this.clearCachedAuthenticationInfo(SecurityUtils
	 * .getSubject().getPrincipals());
	 * 
	 * }
	 */
	
}
