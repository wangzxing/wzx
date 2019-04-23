package com.cn.common.util.config;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.core.io.DefaultResourceLoader;

import com.cn.common.util.PropertiesLoader;
import com.cn.common.util.StringUtils;
import com.google.common.collect.Maps;

/**
 * 
 * <pre>
 * <dt><b>类名：Global</b></dt>
 * <dt><b>描述：</b></dt>
 * <dd>全局配置类</dd>
 * <dt><b>日期：2017-1-9</b></dt>
 * </pre>
 * 
 * @author wangzxing
 */
public class Global {

	/**
	 * 当前对象实例
	 */
	private static Global global = new Global();

	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = Maps.newHashMap();

	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader loader = new PropertiesLoader(
			"config.properties");

	/**
	 * 是/否
	 */
	public static final String YES = "1";
	public static final String NO = "0";

	/**
	 * 对/错
	 */
	public static final String TRUE = "true";
	public static final String FALSE = "false";

	
	/** 系统管理员 */
	public static final String SYS_ADMIN = "2";

	/** 编辑标志 */
	public static final String BJBZ_STDDATA = "0001";
	/** 法人类型 */
	public static final String FRLX_STDDATA = "0002";
	/** 是否显示 */
	public static final String SHOW_STDDATA = "0003";
	/** 日志级别 */
	public static final String LOG_LEVEL = "0004";
	/** 日志类型 */
	public static final String LOG_TYPE = "0005";
	/** 日志位置 */
	public static final String LOG_POSITION = "0006";
	/** 科目类别 */
	public static final String KMLB_STDDATA = "0016";
	/** 余额性质 */
	public static final String YEXZ_STDDATA = "0017";
	/** 小计归属 */
	public static final String XJGS_STDDATA = "0018";
	/** 报表组别 */
	public static final String BBZB_STADATA = "0020";
	/** 机构归属 */
	public static final String JGGS_STDDATA = "0024";
	/** 科目级别 */
	public static final String KMJB_STDDATA = "0030";
	/** 机构级别 */
	public static final String JGJB_STDDATA = "0051";
	/** 机构类型 */
	public static final String JGLX_STDDATA = "0052";
	/** 角色归属 */
	public static final String JSGS_STDDATA = "0074";

	// 系统参数管理
	public static enum SYS_PARAM {
		SYS_FTP_FLAG, //
		SYS_FTP_IP, //
		SYS_FTP_USER, //
		SYS_FTP_PSD, //
		SYS_TUCAO, //
		SYS_MAINTAIN_STATE, //
		SYS_ANNEX_PATH, //
		SYSLOG_FLAG, //
		INIT_FLAG, //
		PSD_RESET_DATE, //
		PSD_RESET_VALUE, //
		LOGIN_CLASSPATH, //
		DEPARTMENTROOT, //
		SYS_CORP_NO, //
		BATCH_DATE, //
		RPT_RIGHT_CLASS_PATH, //
		SYS_TITLE, //
		POLL_CODE, //
		MACHINE_CODE,//
		INTER_ACTION_NAME,
		PUBLIC_MANAGE_MARK
	};

	public static final String NEWSBOARD_FILE_PATH = "newsboard";

	public static final String TEXT_USERINFO = "user";

	public static enum APP_TYPE {
		APP_ORGA, //
		APP_DEPA, //
		APP_RELA, //
		APP_POST, //
		APP_ROLE, //
		APP_OPER, //
		APP_PARA, //
		APP_FUNC, //
		APP_DICT, //
		APP_SUBJ, //
		APP_LOGS, //
		APP_SYST, //
		APP_DOOR //
	};

	public static final String ORGANIZATION_PARENT = "xxxxx"; // 机构父节点ID
	public static final String CORPORATION_PARENT = "xxx"; // 法人父节点ID
	public static final String CORPORATION_ALL = "999"; // 公共法人ID
	public static final String FUNCTION_PARENT = "xxxxxxxxxxxx"; // 功能父节点ID
	public static final String OUT_DEP_PARENT = "yyyyyy"; // 外审部门父节点
	public static final String IN_DEP_PARENT = "zzzzzz"; // 内部检查部门父节点
	public static final String ORG_PARENT_NOT = "[无上级机构]";

	public static final String PARAM_DEFAULT_VALUE = "00000";
	
	/**
	 * 获取当前对象实例
	 */
	public static Global getInstance() {
		return global;
	}
	
	/**
	 * 获取配置
	 * 
	 * @see ${fns:getConfig('adminPath')}
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null) {
			value = loader.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}

	/**
	 * 获取URL后缀
	 */
	public static String getUrlSuffix() {
		return getConfig("urlSuffix");
	}

	/**
	 * 页面获取常量
	 * 
	 * @see ${fns:getConst('YES')}
	 */
	public static Object getConst(String field) {
		try {
			return Global.class.getField(field).get(null);
		} catch (Exception e) {
			// 异常代表无配置，这里什么也不做
		}
		return null;
	}

	/**
	 * 获取工程路径
	 * 
	 * @return
	 */
	public static String getProjectPath() {
		// 如果配置了工程路径，则直接返回，否则自动获取。
		String projectPath = Global.getConfig("projectPath");
		if (StringUtils.isNotBlank(projectPath)) {
			return projectPath;
		}
		try {
			File file = new DefaultResourceLoader().getResource("").getFile();
			if (file != null) {
				while (true) {
					File f = new File(file.getPath() + File.separator + "src"
							+ File.separator + "main");
					if (f == null || f.exists()) {
						break;
					}
					if (file.getParentFile() != null) {
						file = file.getParentFile();
					} else {
						break;
					}
				}
				projectPath = file.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return projectPath;
	}

}
