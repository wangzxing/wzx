package com.cn.web.shiro.filter;

/**
 * 登录次数限制
 */
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.common.util.Encodes;
import com.cn.wzx.user.dao.UserDao;



 

public class RetryLimitHashedCredentialsMatcher extends
		HashedCredentialsMatcher {
	@Autowired
	private UserDao userDAO;

	private Cache<String, AtomicInteger> passwordRetryCache;
 

	public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
		passwordRetryCache = cacheManager.getCache("passwordRetryCache");
	}

	@Override
	public boolean doCredentialsMatch(AuthenticationToken authcToken,
			AuthenticationInfo authcInfo) {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String username = (String) token.getPrincipal();
		int dlNum = 5;

		AtomicInteger retryCount = passwordRetryCache.get(username);
		if (retryCount == null) {
			retryCount = new AtomicInteger(0);
			passwordRetryCache.put(username, retryCount);
		}
		int logn = retryCount.incrementAndGet();
		if (logn >= dlNum) {
			try {
				userDAO.lockAccount(username);
				passwordRetryCache.remove(username);// 清除密码验证错误次数（仅限锁定数据库账户后）
			} catch (Exception e) {
			}// 错误次数过多，锁定用户
		}

		Object tokenCredentials = encrypt(String.valueOf(token.getPassword()),"");
		SimpleAuthenticationInfo info = (SimpleAuthenticationInfo) authcInfo;
		Object accountCredentials = info.getCredentials();

		// boolean matches = super.doCredentialsMatch(token, info); 原生密码校验方式
		boolean matches = equal(tokenCredentials, accountCredentials);// equals(tokenCredentials,
																		// accountCredentials);
																		// //自定义密码校验方式

		if (matches) {
			// clear retry count
			passwordRetryCache.remove(username);
		}
		token.setLoginNum(dlNum - logn);
		return matches;
	}

	/**
	 * 
	 * <pre>
	 * <dt><b>名称：encrypt</b></dt>
	 * <dt><b>描述：</b></dt>
	 * <dd>将传进来的密码进行加密</dd>
	 * </pre>
	 * 
	 * @param data
	 *            要加密的数据
	 * @param jmType
	 *            加密方式
	 * @return
	 */
	private String encrypt(String data, String jmType) {

		// 判断加密方式
		if ("02".equals(jmType)) {
			data = Encodes.encodeBase64(data.getBytes());
		} else if ("03".equals(jmType)) {// HEX加密
			data = Encodes.encodeHex(data.getBytes());
		} else {// md5加密
			data = Encodes.getMD5(data);
		}
		return data;
	}

	private boolean equal(Object tokenCredentials, Object accountCredentials) {
		return (accountCredentials.toString()).equals(tokenCredentials
				.toString());
	}
}
