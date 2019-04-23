package com.cn.web.shiro;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.cn.common.util.config.Global;
 

public class UserInfo implements Serializable {

	private static final long serialVersionUID = 1L;
 

	/** 所有的id和sessionid的对应表 */
	private static Hashtable<String, HttpSession> htAll = new Hashtable<String, HttpSession>();
	/** 用户临时的session作用范围的数据容器 */
	private Hashtable<String, Object> htTempUserArea = new Hashtable<String, Object>();

	/** 定义变量 */
	private String userName;
    
    private String account; 
    
    private String type;
    
    private boolean admin;
	
	private String id; // 用户号
	private String password;//密码
	private String name; // 用户名
	private String right; // 用户权限
	private String depId; // 用户机构id
	private String depName; // 用户机构名称
	private String sessionId; // SessionID

	private String roleId; // 用户角色编号
	private String corporation; // 法人行号
	private String corName; // 法人名称
	private boolean isAdmin = false; // 是否系统管理员
	private boolean isRemember =false;//是否记住用户名

	/**
	 * 构造函数
	 */
	public UserInfo() {
		init();
	}

	public UserInfo(String id) {
	this.id =id;
	}

	private void init() {
		this.id = null;
		this.name = null;
		this.right = null;
		this.depId = null;
		this.depName = null;
	}

	/**
	 * 用户登录系统，返回是否成功的信息
	 * 
	 * @param request
	 *            就是jsp中的request对象
	 * @exception Exception
	 */
	public void login(HttpServletRequest request, String corp,
			String loginType, String orgcd) throws Exception {
		 

		// 查询并获得一个session
		HttpSession session = request.getSession(false);
		if (session == null) {
			session = request.getSession(true);
			while (htAll.containsValue(session)) {
				// 销毁刚创建的session
				session.invalidate();
				// 创建一个新的session
				session = request.getSession(true);
				Thread.sleep(100);
			}
		}
		htAll.put(id, session);
		this.sessionId = session.getId();
 
	}

	/**
	 * 本操作会将本session销毁，并从全局Hashtable中去掉此柜员的条目。
	 * 
	 * @param request
	 *            HttpServletRequest型的request对象
	 * @throws Exception
	 */
	public void logout(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		session.invalidate();
	 
		htAll.remove(id);
		init();
	}

	/**
	 * 解控
	 * 
	 * @param request
	 *            就是jsp中的HttpServletRequest型的request对象
	 * @param id
	 *            用户id
	 * @throws Exception
	 */
	public void setFree(HttpServletRequest request, String id) throws Exception {
	 
		htAll.remove(id);

		// HttpSession session = request.getSession();
		// session.invalidate();
		init();
	}

	/**
	 * 检查session是否有效
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @exception Exception
	 */
	public void checkSession(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession(false);
		UserInfo user = (UserInfo) session
		.getAttribute(Global.TEXT_USERINFO);

		if (htAll.get(user.getId()) == null
				|| !(htAll.get(user.getId()).equals(session))) {
			throw new Exception("被非法解控，请重新登录!");
		}
	}

	/**
	 * 检查权限
	 * 
	 * @param sRight
	 *            用户权限串，以符号"$"分隔.
	 * @exception Exception
	 *                操作员无操作权限
	 */
	public void checkRight(String sRight) throws Exception {
		if (hasRight(sRight)) {
			return;
		}
		throw new Exception("用户无此功能的操作权限，请和系统管理员联系！");
	}

	/**
	 * 检查权限，如果有此权限返回true，否则返回false；多个权限的只要具备其中一个就返回true
	 * 
	 * @param sRight
	 *            用户权限串，以符号"$"分隔.
	 * @return boolean 有，true；无，false
	 * @exception Exception
	 */
	public boolean hasRight(String sRight) throws Exception {
		if (sRight.equals("000000000000")) {
			// 公用功能代号为12个0，不判断权限
			return true;
		}
		String nowRight = "$" + this.right + "$";
		StringTokenizer st = new StringTokenizer(sRight, "$", false);
		while (st.hasMoreTokens()) {
			String tmp = "$" + st.nextToken() + "$";
			if (nowRight.indexOf(tmp) > -1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检查一个柜员是否在线
	 * 
	 * @param id
	 *            柜员号
	 * @return 是否在线，ture为在线，false为不在线
	 */
	public boolean isOnNet(String id) {
		return htAll.containsKey(id);
	}

	/**
	 * 获得UserInfo实例
	 * 
	 * @param request
	 *            请求对象
	 * @return UserInfo类对象
	 * @exception Exception
	 */
	public static UserInfo getInstance(HttpServletRequest request)
	throws Exception {
		if (null != request) {
			HttpSession session = request.getSession(false);
			if (null != session) {
				Object o = session.getAttribute(Global.TEXT_USERINFO);
				UserInfo user = (UserInfo) o;
				return user;
			}
		}
		return null;
	}

	/**
	 * 获得UserInfo实例
	 * 
	 * @param request
	 *            请求对象
	 * @return UserInfo类对象
	 * @exception Exception
	 */
	public static UserInfo getInstance()
	throws Exception {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		if (null != session) {
			Object o = session.getAttribute(Global.TEXT_USERINFO);
			UserInfo user = (UserInfo) o;
			return user;
		}
		return null;
	}
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getRight() {
		return right;
	}

	public String getDepId() {
		return depId;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRight(String right) {
		this.right = right;
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Object getTempUserValue(String sKey) {
		return htTempUserArea.remove(sKey);
	}

	public Object getTempUserValueAL(String sKey) {
		return htTempUserArea.get(sKey);
	}

	public void setTempUserValue(String sKey, Object o) {
		htTempUserArea.put(sKey, o);
	}

	public String getCorporation() {
		return corporation;
	}

	public void setCorporation(String corporation) {
		this.corporation = corporation;
	}

	public String getCorName() {
		return corName;
	}

	public void setCorName(String corName) {
		this.corName = corName;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public static Hashtable<String, HttpSession> getHtAll() {
		return htAll;
	}

	public boolean isRemember() {
		return isRemember;
	}

	public void setRemember(boolean isRemember) {
		this.isRemember = isRemember;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Hashtable<String, Object> getHtTempUserArea() {
		return htTempUserArea;
	}

	public void setHtTempUserArea(Hashtable<String, Object> htTempUserArea) {
		this.htTempUserArea = htTempUserArea;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isAdmin() {
		return admin;
	}
 
	
}
