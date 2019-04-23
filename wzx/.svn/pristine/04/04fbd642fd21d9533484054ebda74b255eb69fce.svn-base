package com.cn.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;

import com.google.common.collect.Lists;

/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 * 
 * @author ThinkGem
 * @version 2013-05-22
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

	private static final char SEPARATOR = '_';
	private static final String CHARSET_NAME = "UTF-8";

	/**
	 * 转换为字节数组
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] getBytes(String str) {
		if (str != null) {
			try {
				return str.getBytes(CHARSET_NAME);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 转换为字节数组
	 * 
	 * @param str
	 * @return
	 */
	public static String toString(byte[] bytes) {
		try {
			return new String(bytes, CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			return EMPTY;
		}
	}

	/**
	 * 是否包含字符串
	 * 
	 * @param str
	 *            验证字符串
	 * @param strs
	 *            字符串组
	 * @return 包含返回true
	 */
	public static boolean inString(String str, String... strs) {
		if (str != null) {
			for (String s : strs) {
				if (str.equals(trim(s))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 替换掉HTML标签方法
	 */
	public static String replaceHtml(String html) {
		if (isBlank(html)) {
			return "";
		}
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}

	/**
	 * 替换为手机识别的HTML，去掉样式及属性，保留回车。
	 * 
	 * @param html
	 * @return
	 */
	public static String replaceMobileHtml(String html) {
		if (html == null) {
			return "";
		}
		return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
	}

	/**
	 * 替换为手机识别的HTML，去掉样式及属性，保留回车。
	 * 
	 * @param txt
	 * @return
	 */
	public static String toHtml(String txt) {
		if (txt == null) {
			return "";
		}
		return replace(replace(Encodes.escapeHtml(txt), "\n", "<br/>"), "\t",
				"&nbsp; &nbsp; ");
	}

	/**
	 * 缩略字符串（不区分中英文字符）
	 * 
	 * @param str
	 *            目标字符串
	 * @param length
	 *            截取长度
	 * @return
	 */
	public static String abbr(String str, int length) {
		if (str == null) {
			return "";
		}
		try {
			StringBuilder sb = new StringBuilder();
			int currentLength = 0;
			for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str))
					.toCharArray()) {
				currentLength += String.valueOf(c).getBytes("GBK").length;
				if (currentLength <= length - 3) {
					sb.append(c);
				} else {
					sb.append("...");
					break;
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String abbr2(String param, int length) {
		if (param == null) {
			return "";
		}
		StringBuffer result = new StringBuffer();
		int n = 0;
		char temp;
		boolean isCode = false; // 是不是HTML代码
		boolean isHTML = false; // 是不是HTML特殊字符,如&nbsp;
		for (int i = 0; i < param.length(); i++) {
			temp = param.charAt(i);
			if (temp == '<') {
				isCode = true;
			} else if (temp == '&') {
				isHTML = true;
			} else if (temp == '>' && isCode) {
				n = n - 1;
				isCode = false;
			} else if (temp == ';' && isHTML) {
				isHTML = false;
			}
			try {
				if (!isCode && !isHTML) {
					n += String.valueOf(temp).getBytes("GBK").length;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			if (n <= length - 3) {
				result.append(temp);
			} else {
				result.append("...");
				break;
			}
		}
		// 取出截取字符串中的HTML标记
		String temp_result = result.toString().replaceAll("(>)[^<>]*(<?)",
				"$1$2");
		// 去掉不需要结素标记的HTML标记
		temp_result = temp_result
				.replaceAll(
						"</?(AREA|BASE|BASEFONT|BODY|BR|COL|COLGROUP|DD|DT|FRAME|HEAD|HR|HTML|IMG|INPUT|ISINDEX|LI|LINK|META|OPTION|P|PARAM|TBODY|TD|TFOOT|TH|THEAD|TR|area|base|basefont|body|br|col|colgroup|dd|dt|frame|head|hr|html|img|input|isindex|li|link|meta|option|p|param|tbody|td|tfoot|th|thead|tr)[^<>]*/?>",
						"");
		// 去掉成对的HTML标记
		temp_result = temp_result.replaceAll("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>",
				"$2");
		// 用正则表达式取出标记
		Pattern p = Pattern.compile("<([a-zA-Z]+)[^<>]*>");
		Matcher m = p.matcher(temp_result);
		List<String> endHTML = Lists.newArrayList();
		while (m.find()) {
			endHTML.add(m.group(1));
		}
		// 补全不成对的HTML标记
		for (int i = endHTML.size() - 1; i >= 0; i--) {
			result.append("</");
			result.append(endHTML.get(i));
			result.append(">");
		}
		return result.toString();
	}

	/**
	 * 转换为Double类型
	 */
	public static Double toDouble(Object val) {
		if (val == null) {
			return 0D;
		}
		try {
			return Double.valueOf(trim(val.toString()));
		} catch (Exception e) {
			return 0D;
		}
	}

	/**
	 * 转换为Float类型
	 */
	public static Float toFloat(Object val) {
		return toDouble(val).floatValue();
	}

	/**
	 * 转换为Long类型
	 */
	public static Long toLong(Object val) {
		return toDouble(val).longValue();
	}

	/**
	 * 转换为Integer类型
	 */
	public static Integer toInteger(Object val) {
		return toLong(val).intValue();
	}



	/**
	 * 获得用户远程地址
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String remoteAddr = request.getHeader("X-Real-IP");
		if (isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("X-Forwarded-For");
		} else if (isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("Proxy-Client-IP");
		} else if (isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("WL-Proxy-Client-IP");
		}
		return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
	}

	/**
	 * 驼峰命名法工具
	 * 
	 * @return toCamelCase("hello_world") == "helloWorld"
	 *         toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 *         toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toCamelCase(String s) {
		if (s == null) {
			return null;
		}

		s = s.toLowerCase();

		StringBuilder sb = new StringBuilder(s.length());
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == SEPARATOR) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	/**
	 * 驼峰命名法工具
	 * 
	 * @return toCamelCase("hello_world") == "helloWorld"
	 *         toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 *         toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toCapitalizeCamelCase(String s) {
		if (s == null) {
			return null;
		}
		s = toCamelCase(s);
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	/**
	 * 驼峰命名法工具
	 * 
	 * @return toCamelCase("hello_world") == "helloWorld"
	 *         toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 *         toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toUnderScoreCase(String s) {
		if (s == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			boolean nextUpperCase = true;

			if (i < (s.length() - 1)) {
				nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
			}

			if ((i > 0) && Character.isUpperCase(c)) {
				if (!upperCase || !nextUpperCase) {
					sb.append(SEPARATOR);
				}
				upperCase = true;
			} else {
				upperCase = false;
			}

			sb.append(Character.toLowerCase(c));
		}

		return sb.toString();
	}

	/**
	 * 转换为JS获取对象值，生成三目运算返回结果
	 * 
	 * @param objectString
	 *            对象串 例如：row.user.id
	 *            返回：!row?'':!row.user?'':!row.user.id?'':row.user.id
	 */
	public static String jsGetVal(String objectString) {
		StringBuilder result = new StringBuilder();
		StringBuilder val = new StringBuilder();
		String[] vals = split(objectString, ".");
		for (int i = 0; i < vals.length; i++) {
			val.append("." + vals[i]);
			result.append("!" + (val.substring(1)) + "?'':");
		}
		result.append(val.substring(1));
		return result.toString();
	}

	/**
	 * 将传入的字符串格式化：传入 a,b,c 格式化后 'a','b','c'
	 * 
	 * @param sSource
	 *            String
	 * @return String
	 */
	public static String formatStringA(String sSource) {
		if (sSource == null)
			sSource = "''";
		else if (sSource.equals(""))
			sSource = "''";
		else {
			sSource = sSource.replaceAll(",", "','");
			sSource = "'" + sSource + "'";
		}

		return sSource;
	}

	/**
	 * 判断参数是否为空，为空则返回一个长度为0的整形数组，否则返回其值
	 * 
	 * @param aSource
	 *            源字符串数组
	 * @return 整型数组
	 */
	public static int[] getIntArray(String[] aSource) {
		int iReturn[] = new int[0];
		if (aSource != null) {
			iReturn = new int[aSource.length];
			for (int i = 0; i < aSource.length; i++) {
				iReturn[i] = Integer.parseInt(aSource[i]);
			}
		}
		return iReturn;
	}

	/**
	 * 将数组中的元素连成一个以逗号分隔的字符串
	 * 
	 * @param aSource
	 *            源数组
	 * @return 字符串
	 */
	public static String arrayToString(String[] aSource) {
		return arrayToString(aSource, ",");
	}

	/**
	 * 将数组中的元素连成一个以给定字符分隔的字符串
	 * 
	 * @param aSource
	 *            源数组
	 * @param sChar
	 *            分隔符
	 * @return 字符串
	 */
	public static String arrayToString(String[] aSource, String sChar) {
		String sReturn = "";
		for (int i = 0; i < aSource.length; i++) {
			if (i > 0)
				sReturn += sChar;
			sReturn += aSource[i];
		}
		return sReturn;
	}

	/**
	 * 将集合中的元素连成一个以给定字符分隔的字符串
	 * 
	 * @param aSource
	 *            源数组
	 * @param sChar
	 *            分隔符
	 * @return 字符串
	 */
	public static String listToString(List<String> list, String sChar) {
		String sReturn = "";
		for (int i = 0; i < list.size(); i++) {
			if (i > 0)
				sReturn += sChar;
			sReturn += list.get(i);
		}
		return sReturn;
	}

	/**
	 * 将两个字符串的所有元素连结为一个字符串数组
	 * 
	 * @param array1
	 *            源字符串数组1
	 * @param array2
	 *            源字符串数组2
	 * @return String[]
	 */
	public static String[] arrayAppend(String[] array1, String[] array2) {
		return (String[]) (arrayAppend(array1, array2));
	}

	/**
	 * 将两个对象数组中的所有元素连结为一个对象数组
	 * 
	 * @param array1
	 *            源字符串数组1
	 * @param array2
	 *            源字符串数组2
	 * @return Object[]
	 */
	public static Object[] arrayAppend(Object[] array1, Object[] array2) {
		int iLen = 0;
		Object aReturn[];
		if (array1 == null)
			array1 = new Object[0];
		if (array2 == null)
			array2 = new Object[0];
		// 获得第一个对象数组的元素个数
		iLen = array1.length;
		aReturn = new Object[iLen + array2.length];
		// 将第一个对象数组的元素加到结果数组中
		for (int i = 0; i < iLen; i++)
			aReturn[i] = array1[i];
		// 将第二个对象数组的元素加到结果数组中
		for (int i = 0; i < array2.length; i++)
			aReturn[iLen + i] = array2[i];
		return aReturn;
	}

	/**
	 * 拆分以逗号分隔的字符串,并存入String数组中
	 * 
	 * @param sSource
	 *            源字符串
	 * @return String[]
	 */
	public static String[] stringToArray(String sSource) {
		return stringToArray(sSource, ",");
	}

	/**
	 * 拆分以给定分隔符分隔的字符串,并存入字符串数组中
	 * 
	 * @param sSource
	 *            源字符串
	 * @param sChar
	 *            分隔符
	 * @return String[]
	 */
	public static String[] stringToArray(String sSource, String sChar) {
		String aReturn[] = null;
		StringTokenizer st = null;
		st = new StringTokenizer(sSource, sChar);
		int i = 0;
		aReturn = new String[st.countTokens()];
		while (st.hasMoreTokens()) {
			aReturn[i] = st.nextToken();
			i++;
		}
		return aReturn;
	}

	/**
	 * 拆分以给定分隔符分隔的字符串,并存入整型数组中
	 * 
	 * @param sSource
	 *            源字符串
	 * @param sChar
	 *            分隔符
	 * @return int[]
	 */
	public static int[] stringToArray(String sSource, char sChar) {
		return getIntArray(stringToArray(sSource, String.valueOf(sChar)));
	}

	/**
	 * 将数组转换成字符串，转换后的字符串首尾不含分隔符，格式如下：a,b,c 。
	 * 
	 * @param a
	 *            int[]
	 * @param dot
	 *            分隔符，比如: ,
	 * @param mark
	 *            引号，比如: '
	 * @return 字符串
	 */
	public static String arrayToString(int a[], String dot, String mark) {
		String strReturn = "";
		if (a.length == 0)
			strReturn = "";
		else if (a.length == 1)
			strReturn = mark + a[0] + mark;
		else {
			for (int i = 0; i < a.length - 1; i++)
				strReturn = strReturn + mark + String.valueOf(a[i]) + mark
						+ dot;
			strReturn = strReturn + mark + String.valueOf(a[a.length - 1])
					+ mark;
		}
		return strReturn;
	}

	/**
	 * 将数组转换成字符串，转换后的字符串首尾不含分隔符，格式如下：a,b,c 。
	 * 
	 * @param a
	 *            String[]
	 * @param dot
	 *            分隔符，比如: ,
	 * @param mark
	 *            引号，比如: '
	 * @return 字符串
	 */
	public static String arrayToString(String a[], String dot, String mark) {
		String strReturn = "";
		if (a.length == 0)
			strReturn = "";
		else if (a.length == 1)
			strReturn = mark + a[0] + mark;
		else {
			for (int i = 0; i < a.length - 1; i++)
				strReturn = strReturn + mark + String.valueOf(a[i]) + mark
						+ dot;
			strReturn = strReturn + mark + String.valueOf(a[a.length - 1])
					+ mark;
		}
		return strReturn;
	}

	/**
	 * 删除磁盘上的文件
	 * 
	 * @param fileName
	 *            文件全路径
	 * @return boolean
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		return file.delete();
	}

	/**
	 * 获取点分格式(123,456,789.88)的显示用数据
	 * 
	 * @param dlSrc
	 *            源数值
	 * @param bitNum
	 *            小数位数
	 * @return boolean
	 * @throws Exception
	 */
	public static String getDecimalAsString(double dlSrc, int bitNum) {
		String sSrc = String.valueOf(dlSrc);
		return getDecimalAsString(sSrc, bitNum);
	}

	/**
	 * 获取点分格式(123,456,789.88)的显示用数据
	 * 
	 * @param sSrc
	 *            源数值
	 * @param bitNum
	 *            小数位数
	 * @return boolean
	 */
	public static String getDecimalAsString(String sSrc, int bitNum) {
		String input = "";
		String restr = "";
		String head = "";
		int flag = 0;

		double dl = Double.parseDouble(sSrc);
		BigDecimal bd = new BigDecimal(dl);
		// 对传入的数字四舍五入，小数点后位数
		bd = bd.setScale(bitNum, 5);
		input = String.valueOf(bd).trim();
		int i = input.indexOf('.');
		if (i == -1)
			i = input.length();

		if (input.substring(0, 1).equals("+")
				|| input.substring(0, 1).equals("-")) {
			head = input.substring(0, 1);
			flag = 1;
		}

		String inputsub = input.substring(flag, i);
		if (inputsub.length() <= 3) {
			restr = input;
			return restr;
		}

		int j = inputsub.length();
		while (j >= 0) {
			if (j > 3) {
				restr = "," + inputsub.substring(j - 3, j) + restr;
			} else {
				restr = inputsub.substring(0, j) + restr;
			}
			j = j - 3;
		}

		return head + restr + input.substring(i, input.length());
	}

	/**
	 * 判断参数是否为空，为空则返回0,不为空则返回其整型值
	 * 
	 * @param sSource
	 *            源字符串
	 * @return 整型数
	 */
	public static int getInt(String sSource) {
		int iReturn = 0;
		if (sSource != null && !sSource.equals(""))
			iReturn = Integer.parseInt(sSource);
		return iReturn;
	}

	/**
	 * 判断参数是否为空，为空则返回""，否则返回其值
	 * 
	 * @param sSource
	 *            源字符串
	 * @return 字符串
	 */
	public static String getString(String sSource) {
		String sReturn = "";
		if (sSource != null)
			sReturn = sSource;
		return sReturn;
	}

	/**
	 * 将给定的源字符串加1 例如：“0001” 经本函数转换后返回为“0002”
	 * 
	 * @param sSource
	 *            :源字符串
	 * @return 返回字符串
	 */
	public static String increaseOne(String sSource) {
		String sReturn = null;
		int iSize = 0;

		iSize = sSource.length();

		long l = (new Long(sSource)).longValue();
		l++;
		sReturn = String.valueOf(l);

		for (int i = sReturn.length(); i < iSize; i++) {
			sReturn = "0" + sReturn;
		}

		return sReturn;
	}

	/**
	 * 将给定的整数转化成字符串， 结果字符串的长度为给定长度，不足位数的左端补"0" （此方法为林伟伟于 2002-10-30 所加）
	 * 
	 * @param val
	 *            val
	 * @param len
	 *            len
	 * @return String
	 */
	public static String intToStr(int val, int len) {
		String sReturn = new String();

		sReturn = String.valueOf(val);

		if (sReturn.length() < len) {
			for (int i = len - sReturn.length(); i > 0; i--) {
				sReturn = "0" + sReturn;
			}
		}

		return sReturn;
	}

	/**
	 * 将String转换成HTML文本<br>
	 * 规则：<br>
	 * 1、将其中的\r\n转换为网页中换行<br>
	 * 2、将其中某段超过rowLen的文字拆成以每行rowLen字的多行;<br>
	 * 3、将其中的空格替换成网页中的空格;<br>
	 * 
	 * @param str
	 *            String 要转换的String
	 * @param rowLen
	 *            int 每行字数（英文字数[汉字*2]）
	 * @return String 转换后的String
	 */
	public static String stringToHTML(String str, int rowLen) {
		StringBuffer newStr = new StringBuffer();
		String aTmp[] = str.split("\r\n");
		for (int i = 0; i < aTmp.length; i++) {
			byte aByte[] = aTmp[i].getBytes();

			if (aByte.length <= rowLen) {
				newStr.append(aTmp[i] + "<br>");
				continue;
			}

			int sbLen = 0;
			StringBuffer sbTmp = new StringBuffer();
			for (int j = 0; j < aTmp[i].length(); j++) {
				String s1 = aTmp[i].substring(j, j + 1);
				int nowLen = s1.getBytes().length;
				sbLen = sbLen + nowLen;

				if (sbLen > rowLen) {
					sbTmp.append("<br>" + s1);
					sbLen = nowLen;
				} else if (sbLen == rowLen) {
					sbTmp.append(s1 + "<br>");
					sbLen = 0;
				} else
					sbTmp.append(s1);
			}
			String lastStr = sbTmp.substring(sbTmp.length() - 4);
			if (lastStr.equals("<br>"))
				newStr.append(sbTmp.toString());
			else
				newStr.append(sbTmp.toString() + "<br>");
		}

		return newStr.toString().replaceAll(" ", "&nbsp;");
	}

	/**
	 * 
	 * <pre>
	 * <dt><b>名称：strToUtf</b></dt>
	 * <dt><b>描述：</b></dt>
	 * <dd>解决get传值中文乱码</dd>
	 * </pre>
	 * 
	 * @param str
	 * @return
	 */
	public static String strToUtf8(String str) {
		if (StringUtils.isNotBlank(str)) {
			try {
				str = new String(str.getBytes("ISO-8859-1"), CHARSET_NAME);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return str;
	}

	/**
	 * 将以逗号分隔的字符串里相同的内容去掉。此方法不记忆原字符串顺序。
	 * 
	 * @param str
	 *            String
	 * @param mark
	 *            String 以此符号分隔
	 * @return String
	 * @author shantao
	 */
	public static String noRepeat(String str, String mark) {
		StringTokenizer st = new StringTokenizer(str, mark);
		List<String> lt = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		String tmp = null;
		while (st.hasMoreTokens()) {
			tmp = st.nextToken();
			if (!lt.contains(tmp))
				lt.add(tmp);
		}
		if (!lt.isEmpty())
			sb.append(lt.get(0));
		for (int i = 1; i < lt.size(); i++)
			sb.append(mark + lt.get(i));
		return sb.toString();
	}

	public static String getFormatDate(String chDate, String dot) {
		if (chDate.length() == 8)
			return chDate.substring(0, 4) + dot + chDate.substring(4, 6) + dot
					+ chDate.substring(6, 8);
		else
			return chDate;
	}

	public static String getFormatTime(String chTime, String dot) {
		if (chTime.length() == 6)
			return chTime.substring(0, 2) + dot + chTime.substring(2, 4) + dot
					+ chTime.substring(4, 6);
		else
			return chTime;
	}

	public static String getShortName(String name, int len) {
		if (name.length() > len) {
			name = name.substring(1, len) + "……";
		}
		return name;
	}

	public static String getUUID() {
		String uuid = UUID.randomUUID().toString().toUpperCase()
				.replaceAll("-", "");
		return uuid;
	}

	public static String gzip(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(str.getBytes());
		gzip.close();
		out.close();
		return Encodes.encodeBase64(out.toByteArray());
	}

	public static String ungzip(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] compressed = Encodes.decodeBase64(str);
		ByteArrayInputStream in = new ByteArrayInputStream(compressed);
		GZIPInputStream ingzip = new GZIPInputStream(in);
		byte[] buffer = new byte[1024];
		int offset = -1;
		while ((offset = ingzip.read(buffer)) != -1) {
			out.write(buffer, 0, offset);
		}
		String rtn = out.toString();
		ingzip.close();
		in.close();
		out.close();
		return rtn;
	}
	
	  // 根据Unicode编码完美的判断中文汉字和符号
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }
 
  	/**
  	 * 
  	 * <pre>
  	 * <dt><b>名称：isHasChinese</b></dt>
  	 * <dt><b>描述：</b></dt>
  	 * <dd>判断包含中文汉字和符号</dd>
  	 * </pre>
  	 * 
  	 * @param strName
  	 * @return
  	 */
    public static boolean isHasChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }
    /**
     * 
     * <pre>
     * <dt><b>名称：isChinese</b></dt>
     * <dt><b>描述：</b></dt>
     * <dd>判断字符串全部是中文和中文字符</dd>
     * </pre>
     * 
     * @param strName
     * @return
     */
    public static boolean isAllChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!isChinese(c)) {
                return false;
            }
        }
        return true;
    }
 
    // 只能判断部分CJK字符（CJK统一汉字）
    public static boolean isChineseByREG(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
        return pattern.matcher(str.trim()).find();
    }
 
    // 只能判断部分CJK字符（CJK统一汉字）
    public static boolean isChineseByName(String str) {
        if (str == null) {
            return false;
        }
        // 大小写不同：\\p 表示包含，\\P 表示不包含
        // \\p{Cn} 的意思为 Unicode 中未被定义字符的编码，\\P{Cn} 就表示 Unicode中已经被定义字符的编码
        String reg = "\\p{InCJK Unified Ideographs}&&\\P{Cn}";
        Pattern pattern = Pattern.compile(reg);
        return pattern.matcher(str.trim()).find();
    }
}
