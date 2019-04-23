package com.cn.web.shiro.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.common.util.DateUtil;
import com.cn.common.util.StringUtils;
import com.cn.web.servlet.Servlets;
import com.google.common.collect.Sets;

/**
 * 
 * <pre>
 * <dt><b>类名：CacheSessionDAO</b></dt>
 * <dt><b>描述：</b></dt>
 * <dd>系统安全认证实现类</dd>
 * <dt><b>日期：2017-1-10</b></dt>
 * </pre>
 * 
 * @author wangzxing
 */
public class CacheSessionDAO extends EnterpriseCacheSessionDAO implements
		SessionDAO {
	private Logger logger = LoggerFactory.getLogger(getClass());

	public CacheSessionDAO() {
		super();
	}

	@Override
	protected void doUpdate(Session session) {
		if (session == null || session.getId() == null) {
			return;
		}

		HttpServletRequest request = Servlets.getRequest();
		if (request != null) {
			String uri = request.getServletPath();
			// 如果是静态文件，则不更新SESSION
			if (Servlets.isStaticFile(uri)) {
				return;
			}

			// 手动控制不更新SESSION
			String updateSession = request.getParameter("updateSession");
			if ("false".equals(updateSession)
					|| "no".equals(updateSession)) {
				return;
			}
		}

		if (session instanceof ValidatingSession
				&& !((ValidatingSession) session).isValid()) {
			return; // 如果会话过期/停止 没必要再更新了
		}

		// UserInfo user = (UserInfo)
		// session.getAttribute(HlcConstant.TEXT_USERINFO);
		//
		// if(user !=null &&!"".equals(user.getId())){
		//
		// boolean bool;
		// try {
		// bool =
		// userOnlineDAO.getUserOnline(user.getId(),user.getCorporation());
		// if(bool){//新增
		// UserOnlineEn userOnline = new UserOnlineEn();
		// userOnline.setCorporation(user.getCorporation());//法人主体号
		// userOnline.setChoperatorcode(user.getId());//操作员代码
		// userOnline.setChsessionid(session.getId().toString());//sessionID
		// userOnline.setChlogindate(DateUtil.formatTimeyyyyMMdd(session.getLastAccessTime
		// ()));//登陆日期
		// userOnline.setChuserip(session.getHost());//用户IP
		// userOnline.setChorgcd(user.getDepId());//用户机构号 //将用户登录信息保存到数据库
		//
		// baseDAO.insert(userOnline);
		// }
		// } catch (Exception e) {
		//
		// }
		// }
		super.doUpdate(session);
		logger.debug("update {} {}", session.getId(),
				request != null ? request.getRequestURI() : "");
	}

	@Override
	protected void doDelete(Session session) {
		if (session == null || session.getId() == null) {
			return;
		}

		super.doDelete(session);
		logger.debug("delete {} ", session.getId());
	}

	@Override
	protected Serializable doCreate(Session session) {
		HttpServletRequest request = Servlets.getRequest();
		if (request != null) {
			String uri = request.getServletPath();
			// 如果是静态文件，则不创建SESSION
			if (Servlets.isStaticFile(uri)) {
				return null;
			}
		}
		super.doCreate(session);
		logger.debug("doCreate {} {}", session,
				request != null ? request.getRequestURI() : "");
		return session.getId();
	}

	@Override
	public Session readSession(Serializable sessionId)
			throws UnknownSessionException {
		try {
			Session s = null;
			HttpServletRequest request = Servlets.getRequest();
			if (request != null) {
				String uri = request.getServletPath();
				// 如果是静态文件，则不获取SESSION
				if (Servlets.isStaticFile(uri)) {
					return null;
				}
				s = (Session) request.getAttribute("session_" + sessionId);
			}
			if (s != null) {
				return s;
			}

			Session session = super.readSession(sessionId);
			logger.debug("readSession {} {}", sessionId,
					request != null ? request.getRequestURI() : "");

			if (request != null && session != null) {
				request.setAttribute("session_" + sessionId, session);
			}

			return session;
		} catch (UnknownSessionException e) {
			return null;
		}
	}

	/**
	 * 获取活动会话 true:获取全部会话 ；false：获取活动会话
	 * 
	 * @param includeLeave
	 *            是否包括离线（最后访问时间大于3分钟为离线会话）
	 * @return
	 */

	public Collection<Session> getActiveSessions(boolean includeLeave) {
		return getActiveSessions(includeLeave, null, null);
	}

	/**
	 * 获取活动会话
	 * 
	 * @param includeLeave
	 *            是否包括离线（最后访问时间大于3分钟为离线会话）
	 * @param principal
	 *            根据登录者对象获取活动会话
	 * @param filterSession
	 *            不为空，则过滤掉（不包含）这个会话。 * true:获取全部会话 ；false：获取活动会话
	 * @return
	 */

	public Collection<Session> getActiveSessions(boolean includeLeave,
			Object principal, Session filterSession) {
		// 如果包括离线，并无登录者条件。
		if (includeLeave && principal == null) {
			return getActiveSessions();
		}
		Set<Session> sessions = Sets.newHashSet();
		for (Session session : getActiveSessions()) {
			boolean isActiveSession = false;
			// 不包括离线并符合最后访问时间小于等于3分钟条件。
			if (includeLeave
					|| DateUtil.pastMinutes(session.getLastAccessTime()) <= 1) {
				isActiveSession = true;
			}
			// 符合登陆者条件。
			if (principal != null) {
				PrincipalCollection pc = (PrincipalCollection) session
						.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
				if (principal.toString().equals(
						pc != null ? pc.getPrimaryPrincipal().toString()
								: StringUtils.EMPTY)) {
					isActiveSession = true;
				}
			}
			// 过滤掉的SESSION
			if (filterSession != null
					&& filterSession.getId().equals(session.getId())) {
				isActiveSession = false;
			}
			if (isActiveSession) {
				sessions.add(session);
			}
		}
		return sessions;
	}

}
