package com.cn.common.util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 
 * <pre>
 * <dt><b>类名：DateUtil</b></dt>
 * <dt><b>描述：</b></dt>
 * <dd>日期工具类</dd>
 * <dt><b>日期：2017-1-9</b></dt>
 * </pre>
 * 
 * @author wangzxing
 */
public class DateUtil extends org.apache.commons.lang3.time.DateUtils implements
		Serializable {
	/**
	 */
	private static final long serialVersionUID = 1L;

	// 定义格式化的日期的实例
	/** 临时格式化日期类 */
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	/** 临时日历类 */
	private static Calendar cld = Calendar.getInstance();

	/** 临时日期类 */
	private static Date dt = null;

	/** 临时开始日期类 */
	private static Date dtBegin = null;

	/** 临时结束日期类 */
	private static Date dtEnd = null;

	public static String getTimes(String sdf) {
		SimpleDateFormat sdformat = new SimpleDateFormat(sdf);
		String s = sdformat.format(Calendar.getInstance().getTime());
		return s;
	}

	public static String getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance(); // new GregorianCalendar();
		cal.set(Calendar.YEAR, year);// 年
		cal.set(Calendar.MONTH, month - 1);// 月，因为Calendar里的月是从0开始，所以要减1
		cal.set(Calendar.DATE, 1);// 日，设为一号
		cal.add(Calendar.MONTH, 1);// 月份加一，得到下个月的一号
		cal.add(Calendar.DATE, -1);// 下一个月减一为本月最后一天
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String date = format.format(cal.getTime());
		return date;// 获得月末是几号
	}

	public static String thisSeasonEnd(String year, String season) {
		String dateString = "";
		String y = season;

		if (y.equals("01")) {
			dateString = year + "03" + "31";
		}
		if (y.equals("02")) {
			dateString = year + "06" + "30";
		}
		if (y.equals("03")) {

			dateString = year + "09" + "30";
		}
		if (y.equals("04")) {
			dateString = year + "12" + "31";
		}
		return dateString;
	}

	public static String getXunDayOfMonth(String year, String month, String xun) {

		if ("1".equals(xun)) {

			return year + month + "10";

		} else if ("2".equals(xun)) {

			return year + month + "20";
		} else {

			int t1 = Integer.parseInt(year);
			int t2 = Integer.parseInt(month);
			return getLastDayOfMonth(t1, t2);
		}

	}

	public static String getHalfDayOfMonth(String year, String half) {

		if ("1".equals(half)) {

			return year + "0630";

		} else {

			return year + "1231";
		}
	}

	public static String getYearDayOfMonth(String year) {

		return year + "1231";
	}

	/**
	 * 判断日期的合法性
	 * 
	 * @param sDate
	 *            日期
	 * 
	 * @throws Exception
	 */
	public static void checkDate(String sDate) throws Exception {
		if (sDate == null)
			throw new Exception("日期为空");

		if (sDate.length() != 8)
			throw new Exception("日期必须为8位");

	}

	/**
	 * 获得给定日期的，n几天后的日期
	 * 
	 * @param sDate
	 *            原日期
	 * @param n
	 *            天数
	 * @return 计算后得的日期
	 * @throws Exception
	 */
	public static String getNextDate(String sDate, int n) throws Exception {
		// 把日期的字符串转化成日期的实例
		dt = sdf.parse(sDate, new ParsePosition(0));
		cld.setTime(dt);
		// 计算新的Calendar
		cld.add(Calendar.DATE, n);
		return sdf.format(cld.getTime());
	}

	/**
	 * 获得给定日期的前一个会计工作日
	 * 
	 * @param sDate
	 *            日期
	 * @return String
	 * @throws Exception
	 */
	public static String getKJPrevDate(String sDate) throws Exception {
		throw new java.lang.UnsupportedOperationException(
				"Method getKJPrevDate() not yet implemented.");
		// try
		// {
		// checkDB();
		//
		// //判断此日期是否为合法日期
		// s = "SELECT MAX(chdt) FROM " + getHolidayAlias()
		// + " WHERE "//load_date=(SELECT MAX(load_date) FROM
		// " + getHolidayAlias() + ")"
		// //+ " AND
		// + "chdt < '" + sDate + "' AND inFlag = 0 " ;
		// rd = db.executeQuery(s) ;
		// if (rd.size() != 1)
		// throw new Exception("此日期为非法日期");
		// else
		// return rd.getString(0,0) ;
		// }
		// catch(Exception ae)
		// {
		// throw new Exception("DateUtil.java查询数据库出错：" + ae.getMsg()) ;
		// }

	}

	/**
	 * 获得给定日期的下一个会计工作日
	 * 
	 * @param sDate
	 *            日期
	 * @return String
	 * @throws Exception
	 */
	public static String getKJNextDate(String sDate) throws Exception {
		throw new java.lang.UnsupportedOperationException(
				"Method getKJNextDate() not yet implemented.");
		// try
		// {
		// checkDB();
		//
		// //判断此日期是否为合法日期
		// s = "SELECT MIN(chdt) FROM " + getHolidayAlias()
		// + " WHERE " //load_date=(SELECT MAX(load_date) FROM
		// " + getHolidayAlias() + ")"
		// //+ " AND
		// + "chdt > '" + sDate + "' AND inFlag = 0 " ;
		// rd = db.executeQuery(s) ;
		// if (rd.size() != 1)
		// throw new Exception("此日期为非法日期");
		// else
		// return rd.getString(0,0) ;
		// }
		// catch(Exception ae)
		// {
		// throw new Exception("DateUtil.java查询数据库出错：" + ae.getMsg()) ;
		// }

	}

	/**
	 * 取得两个日期之间的所有日期（包括本身）---自然日期 add by ck 2002-12-9 10:50
	 * 
	 * @param sBeginDate
	 *            开始日期
	 * @param sEndDate
	 *            结束日期
	 * @return 两个日期之间的所有日期（包括本身）
	 * @throws Exception
	 */
	public static String[] getNaturalBetweenDates(String sBeginDate,
			String sEndDate) throws Exception {
		String aReturn[] = null;

		if (sEndDate.compareTo(sBeginDate) < 0)
			throw new Exception("结束日期不能小于开始日期。");

		int iBetween = 0;

		try {
			// 取出两个日期相差的天数
			dtBegin = sdf.parse(sBeginDate, new ParsePosition(0));
			dtEnd = sdf.parse(sEndDate, new ParsePosition(0));
			iBetween = (new Long((dtEnd.getTime() - dtBegin.getTime()) / 1000
					/ 60 / 60 / 24 + 1)).intValue();

			aReturn = new String[iBetween];

			aReturn[0] = sBeginDate;
			for (int i = 1; i < iBetween; i++) {
				aReturn[i] = getNextDate(sBeginDate, i);
			}
		} catch (Exception e) {
			throw new Exception("DateUtil.java:getNaturalBetweenDates方法出错：" + e);
		}

		return aReturn;

	}

	/**
	 * 返回当前时间　HHmmss 24小时制
	 * 
	 * @return 当前时间　HHmmss
	 * @throws Exception
	 */
	public static String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String s = sdf.format(Calendar.getInstance().getTime());
		return s;
	}

	/**
	 * 返回当前日期 yyyyMMdd
	 * 
	 * @return 当前日期 yyyyMMdd
	 * @throws Exception
	 */
	public static String getCurrentDate() {
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String s = sdf.format(Calendar.getInstance().getTime());
		return s;
	}

	/**
	 * 返回昨天日期 yyyyMMdd
	 * 
	 * @throws Exception
	 */
	public static String getPreDate() throws Exception {
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date d = new Date();
		d = new Date(d.getTime() - 24 * 60 * 60 * 1000);
		String ss = new SimpleDateFormat("yyyyMMdd").format(d);
		return ss;
	}

	/**
	 * Advances this day by n days. 返回今天以后的几天是哪天
	 * 
	 * @param n
	 *            the number of days by which to change this day
	 * @return String date.
	 * @throws Exception
	 */
	public static String getNextDate(int n) throws Exception {
		dt = sdf.parse(getCurrentDate(), new ParsePosition(0));
		cld.setTime(dt);
		cld.add(Calendar.DATE, n);
		String s = sdf.format(cld.getTime());
		return s;
	}

	/**
	 * 获取从calendar的日期到January 1, 1970, 0:00:00 GMT的milliseconds
	 * 
	 * @param calendar
	 *            Calendar
	 * @return 长整数milliseconds
	 */
	private static long getTimeMillis(Calendar calendar) {
		String sCalendar = calendar.toString();
		String sTimeInMillis = sCalendar.substring(sCalendar.indexOf("=") + 1,
				sCalendar.indexOf(","));
		long lTimeInMillis = Long.parseLong(sTimeInMillis);
		return lTimeInMillis;
	}

	/**
	 * 获得两个日期之间的天数（整形）
	 * 
	 * @param sBiginDate
	 *            开始日期
	 * @param sEndDate
	 *            结束日期
	 * @return 两个日期之间的天数（整形）
	 * @throws Exception
	 */
	public static int daysBetween(String sBiginDate, String sEndDate)
			throws Exception {
		try {
			dt = sdf.parse(sBiginDate, new ParsePosition(0));
			cld.setTime(dt);
			long lTime1 = getTimeMillis(cld);

			dt = sdf.parse(sEndDate, new ParsePosition(0));
			cld.setTime(dt);
			long lTime2 = getTimeMillis(cld);

			return (int) ((lTime2 - lTime1) / 1000 / 60 / 60 / 24);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 判断是否为年初
	 * 
	 * @param sDate
	 *            日期
	 * @return 是否为年初
	 * @throws Exception
	 */
	public static boolean isYearEarlier(String sDate) throws Exception {
		checkDate(sDate);

		String s = sDate.substring(4, 6);
		return s.equals("01") && checkMonthEarlierDate(sDate);

		// return sDate.substring(4).equals("0101") ;
	}

	/**
	 * 判断是否为季末
	 * 
	 * @param sDate
	 *            日期
	 * @return 是否为季末
	 * @throws Exception
	 */
	public static boolean isQuarterEnd(String sDate) throws Exception {
		checkDate(sDate);

		String s = sDate.substring(4, 6);
		return (s.equals("03") || s.equals("06") || s.equals("09") || s
				.equals("12")) && checkMonthEndDate(sDate);

		// s = sDate.substring(4) ;
		// return s.equals("0331") || s.equals("0630") || s.equals("0930") ||
		// s.equals("1231");
	}

	/**
	 * 获得上月同期日
	 * 
	 * @param sDate
	 *            日期
	 * @return String
	 * @throws Exception
	 */
	public static String getPreMonthByDate(String sDate) throws Exception {
		checkDate(sDate);
		dt = sdf.parse(sDate, new ParsePosition(0));
		cld.setTime(dt);
		// 计算新的Calendar
		cld.add(Calendar.MONTH, -1);
		return sdf.format(cld.getTime());
	}

	/**
	 * 获得本年年初日
	 * 
	 * @param sDate
	 *            日期
	 * @return String
	 * @throws Exception
	 */
	public static String getYearEarlierDate(String sDate) throws Exception {
		checkDate(sDate);

		return getMonthEarlierDate(sDate.substring(0, 4) + "0101");

		// return sDate.substring(0,4) + "0101" ;
	}

	/**
	 * 获得本月月末日--自然日期
	 * 
	 * @param sDate
	 *            日期
	 * @return String
	 * @throws Exception
	 */
	public static String getNaturalMonthEndDate(String sDate) throws Exception {
		String s = sDate.substring(4, 6);
		if (s.equals("12"))
			return sDate.substring(0, 4) + "1231";
		else {
			// 取出下月月份
			s = String.valueOf(Integer.parseInt(s) + 1);
			s = "0" + s;
			s = s.substring(s.length() - 2);
			// 取出下月月初 --- 8位日期
			s = sDate.substring(0, 4) + s + "01";
			// 获得本月月末
			return getNextDate(s, -1);

		}
	}

	/**
	 * 获取格式化日期
	 * 
	 * @param tmpDate
	 *            传入的日期字符串可以为8位年月日，也可以为""
	 * @param tmpFormat
	 *            分隔符号，可以是"-"、"/"等。
	 * @return 若传入的日期字符串为""，按传入的格式返回当前日期，若为8位日期，按传入的格式返回该日期
	 * @exception Exception
	 */
	public static String getFormatDate(String tmpDate, String tmpFormat)
			throws Exception {
		if (!(tmpDate.length() == 0 || tmpDate.length() == 8))
			throw new Exception("日期必须为8位，格式为: yyyymmdd；或为\"\"，表示取当前日期!");

		if (tmpDate.length() == 0) {
			Calendar calNow = Calendar.getInstance();
			String yy = "", mm = "", dd = "";
			int y = calNow.get(Calendar.YEAR);
			int m = calNow.get(Calendar.MONTH) + 1;
			int d = calNow.get(Calendar.DAY_OF_MONTH);
			yy = String.valueOf(y);
			mm = String.valueOf(100 + m).substring(1);
			dd = String.valueOf(100 + d).substring(1);
			return yy + tmpFormat + mm + tmpFormat + dd;
		} else {
			return tmpDate.substring(0, 4) + tmpFormat
					+ tmpDate.substring(4, 6) + tmpFormat
					+ tmpDate.substring(6, 8);
		}
	}

	/**
	 * 获取格式化日期YYYYDDMM
	 * 
	 * @param tmpDate
	 *            传入的日期字符串
	 * @param tmpFormat
	 *            分隔符号，可以是"-"、"/"等。
	 * @return 若传入的日期字符串为""，按传入的格式返回当前日期，若为8位日期，按传入的格式返回该日期
	 * @exception Exception
	 */
	public static String getFormatYYYYMMDDDate(String tmpDate) throws Exception {
		if (!(tmpDate.length() == 0 || tmpDate.length() == 10))
			throw new Exception("日期必须为10位，格式为: yyyy/mm/dd；或为\"\"，表示取当前日期!");

		if (tmpDate.length() == 0) {
			// 获取日期，生成格式为 20010309
			Calendar calNow = Calendar.getInstance();

			String yy = "", mm = "", dd = "";
			int y = calNow.get(Calendar.YEAR);
			int m = calNow.get(Calendar.MONTH) + 1;
			int d = calNow.get(Calendar.DAY_OF_MONTH);

			yy = String.valueOf(y);
			mm = String.valueOf(100 + m).substring(1);
			dd = String.valueOf(100 + d).substring(1);
			return yy + mm + dd;
		} else {
			return tmpDate.substring(0, 4) + tmpDate.substring(5, 7)
					+ tmpDate.substring(8, 10);
		}

	}

	public static String getChinaDate(String tmpDate) throws Exception {
		if (tmpDate == null)
			tmpDate = "";
		if (tmpDate.length() < 8)
			return tmpDate;
		String rDate = "";
		rDate = tmpDate.substring(0, 4) + "年";
		rDate = rDate + tmpDate.substring(4, 6) + "月";
		rDate = rDate + tmpDate.substring(6, 8) + "日";
		return rDate;
	}

	/**
	 * 获取格式化时间
	 * 
	 * @param tmpTime
	 *            传入的时间字符串可以为8位时间，也可以为""
	 * @param tmpFormat
	 *            分隔符号，可以是":"、"."等。
	 * @return 若传入的时间字符串为""，按传入的格式返回当前时间，若为8位时间，按传入的格式返回该时间
	 * @exception Exception
	 */
	public static String getFormatTime(String tmpTime, String tmpFormat)
			throws Exception {
		if (!(tmpTime.length() == 0 || tmpTime.length() == 6))
			throw new Exception("时间必须为6位，格式为: hhmmss；或为\"\"，表示取当前时间!");

		if (tmpTime.length() == 0) {
			// 获取日期和时间，生成格式为 2001-03-09 09:08:07
			Calendar calNow = Calendar.getInstance();

			String hh = "", min = "", ss = "";
			int h = calNow.get(Calendar.HOUR_OF_DAY);
			int mi = calNow.get(Calendar.MINUTE);
			int s = calNow.get(Calendar.SECOND);
			hh = String.valueOf(100 + h).substring(1);
			min = String.valueOf(100 + mi).substring(1);
			ss = String.valueOf(100 + s).substring(1);

			return hh + tmpFormat + min + tmpFormat + ss;
		} else {
			return tmpTime.substring(0, 2) + tmpFormat
					+ tmpTime.substring(2, 4) + tmpFormat
					+ tmpTime.substring(4, 6);
		}

	}

	/**
	 * 比较给定与当前系统日期的差值
	 * 
	 * @param sDate
	 *            日期
	 * @return 相差的天数
	 * @throws Exception
	 */
	public static int compareCurrentDate(String sDate) throws Exception {
		checkDate(sDate);
		return daysBetween(getCurrentDate(), sDate);
	}

	/**
	 * 判断是否此日期为月末
	 * 
	 * @param sDate
	 *            日期
	 * @throws Exception
	 * @return boolean
	 */
	private static boolean checkMonthEndDate(String sDate) throws Exception {
		return getMonthEndDate(sDate).equals(sDate);

	}

	/**
	 * 判断是否此日期为月初
	 * 
	 * @param sDate
	 *            日期
	 * @throws Exception
	 * @return boolean
	 */
	private static boolean checkMonthEarlierDate(String sDate) throws Exception {
		return getMonthEarlierDate(sDate).equals(sDate);

	}

	/**
	 * 判断一个日期是否为假日
	 * 
	 * @param sDate
	 *            日期
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean isHoliday(String sDate) throws Exception {
		throw new java.lang.UnsupportedOperationException(
				"Method isHoliday() not yet implemented.");

		// try
		// {
		// boolean bReturn = true ;
		//
		// checkDB() ;
		//
		// //判断此日期是否为合法日期
		// s = "SELECT inFlag FROM " + getHolidayAlias()
		// + " WHERE " //load_date=(SELECT MAX(load_date) FROM
		// " + getHolidayAlias() + ")"
		// //+ " AND
		// + "chdt = '" + sDate + "'" ;
		// rd = db.executeQuery(s) ;
		// if (rd.size() != 1)
		// throw new Exception("此日期为非法日期");
		// else
		// {
		// if (rd.getString(0,0).equals("1"))
		// bReturn = true ;
		// else
		// bReturn = false ;
		// }
		// return bReturn ;
		// }
		// catch(Exception ae)
		// {
		// throw new Exception("DateUtil.java查询数据库出错：" + ae.getMsg()) ;
		// }

	}

	/**
	 * 判断一个数组是否为空
	 * 
	 * @param aSource
	 *            日期
	 * @throws Exception
	 */
	public static void checkArray(String[] aSource) throws Exception {
		if (aSource == null || aSource.length == 0)
			throw new Exception("参数数组不能为空");
	}

	/**
	 * 获得本日期所在的季度的季末日期
	 * 
	 * @param sDate
	 *            日期
	 * @return String
	 * @throws Exception
	 */
	public static String getQuarterEndDate(String sDate) throws Exception {
		checkDate(sDate);
		String s = sDate.substring(4, 6);

		if (s.equals("01") || s.equals("02") || s.equals("03")) {
			s = sDate.substring(0, 4) + "0301";
		} else if (s.equals("04") || s.equals("05") || s.equals("06")) {
			s = sDate.substring(0, 4) + "0601";
		} else if (s.equals("07") || s.equals("08") || s.equals("09")) {
			s = sDate.substring(0, 4) + "0901";
		} else if (s.equals("10") || s.equals("11") || s.equals("12")) {
			s = sDate.substring(0, 4) + "1201";
		} else {
			throw new Exception("不存在的月份：" + s);
		}
		// 返回季末日期
		return getMonthEndDate(s);

	}

	/**
	 * 获得等于或早于给定日期的合法的就近会计工作日 若给定日期为会计工作日，则直接返回此日期， 若不是，则找到早于给定日期的就近的会计工作日
	 * 
	 * @param sDate
	 *            日期
	 * @return String
	 * @throws Exception
	 */
	public static String getKJDateEarly(String sDate) throws Exception {
		// checkDate(sDate) ;

		if (!isHoliday(sDate))
			return sDate;
		else
			return getKJPrevDate(sDate);
	}

	/**
	 * 获得等于或晚于给定日期的合法的就近会计工作日 若给定日期为会计工作日，则直接返回此日期， 若不是，则找到晚于给定日期的就近的会计工作日
	 * 
	 * @param sDate
	 *            日期
	 * @return String
	 * @throws Exception
	 */
	public static String getKJDateLate(String sDate) throws Exception {
		if (!isHoliday(sDate))
			return sDate;
		else
			return getKJNextDate(sDate);
	}

	/***************************** add by wsq 2002.11.12 ******************************/

	/**
	 * 生成中文日期：该方法根据传人的8位有效日期生成该日期的中文名称
	 * 
	 * @param sDate
	 *            8位有效日期
	 * @return String
	 * @throws Exception
	 */
	public static String makeCnDate(String sDate) throws Exception {
		if (sDate.length() == 4) {
			return sDate + "年";
		} else if (sDate.length() == 6) {
			if (sDate.substring(4, 5).equals("Q")
					|| sDate.substring(4, 5).equals("q"))
				return sDate.substring(0, 4) + "年" + sDate.substring(5, 6)
						+ "季度";
			else
				return sDate.substring(0, 4) + "年" + sDate.substring(4, 6)
						+ "月";
		} else if (sDate.length() == 8) {
			return sDate.substring(0, 4) + "年" + sDate.substring(4, 6) + "月"
					+ sDate.substring(6, 8) + "日";
		} else {
			throw new Exception("日期格式不对，不能生成中文日期");
		}
	} // end of makeCnDate()

	/**
	 * 是否周末
	 * 
	 * @param chDate
	 *            String
	 * @return boolean
	 */
	public static boolean isWeekEnd(String chDate) {
		Date dn = new java.text.SimpleDateFormat("yyyyMMdd").parse(chDate,
				new ParsePosition(0));
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyyMMddWE", new java.text.DateFormatSymbols(
						java.util.Locale.US));
		String s = sdf.format(dn);
		String week = s.substring(9, 12).toLowerCase();
		return week.equals("fri");
	}

	/**
	 * 是否月末
	 * 
	 * @param chDate
	 *            String
	 * @return boolean
	 */
	public static boolean isMonthEnd(String chDate) {
		Date dn = new java.text.SimpleDateFormat("yyyyMMdd").parse(chDate,
				new ParsePosition(0));
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"yyyyMMdd",
				new java.text.DateFormatSymbols(java.util.Locale.US));
		String s2 = sdf.format(new Date(dn.getTime()
				+ (long) (24 * 60 * 60 * 1000)));
		return s2.substring(6, 8).equals("01");
	}

	/**
	 * 是否旬末
	 * 
	 * @param chDate
	 *            String
	 * @return boolean
	 */
	public static boolean isTenDaysEnd(String chDate) {
		String day = chDate.substring(6, 8);
		return day.equals("10") || day.equals("20") || isMonthEnd(chDate);
	}

	/**
	 * 是否季末
	 * 
	 * @param chDate
	 *            String
	 * @return boolean
	 */
	public static boolean isSeasonEnd(String chDate) {
		String month = chDate.substring(4, 6);
		return (month.equals("03") || month.equals("06") || month.equals("09") || month
				.equals("12")) && isMonthEnd(chDate);
	}

	/**
	 * 是否年末
	 * 
	 * @param chDate
	 *            String
	 * @return boolean
	 */
	public static boolean isYearEnd(String chDate) {
		return chDate.substring(4, 8).equals("1231");
	}

	/**
	 * 是否半年末
	 * 
	 * @param chDate
	 *            String
	 * @return boolean
	 */
	public static boolean isHalfYearEnd(String chDate) {
		return chDate.substring(4, 8).equals("0630");
	}

	/**
	 * 生成季日期：该方法根据传人的8位有效日期生成该日期的季日期
	 * 
	 * @param sDate
	 *            8位有效日期
	 * @return String
	 * @throws Exception
	 */
	public static String makeQuarterDate(String sDate) throws Exception {
		if (sDate.length() != 8)
			throw new Exception("日期格式不对，不能生成季日期");

		if (sDate.substring(4, 6).equals("01")
				|| sDate.substring(4, 6).equals("02")
				|| sDate.substring(4, 6).equals("03"))
			return sDate.substring(0, 4) + "Q1";
		else if (sDate.substring(4, 6).equals("04")
				|| sDate.substring(4, 6).equals("05")
				|| sDate.substring(4, 6).equals("06"))
			return sDate.substring(0, 4) + "Q2";
		else if (sDate.substring(4, 6).equals("07")
				|| sDate.substring(4, 6).equals("08")
				|| sDate.substring(4, 6).equals("09"))
			return sDate.substring(0, 4) + "Q3";
		else if (sDate.substring(4, 6).equals("10")
				|| sDate.substring(4, 6).equals("11")
				|| sDate.substring(4, 6).equals("12"))
			return sDate.substring(0, 4) + "Q4";
		else
			throw new Exception("日期格式不对，不能生成季日期");

	} // end of makeQuarterDate()

	// 根据参数ogdate，得到ogdate这个月的最后一个日期，例如：
	// getLastDate("200308")=20030831
	// 参数ogdate必须是6位（到月）或8位（到日）
	public static String getMonthLastDate(String ogdate) {
		if (ogdate.length() == 6)
			ogdate = ogdate + "01";
		else {// 把ogdate变成前6位加01的串，如20030805-->20030801
			ogdate = ogdate.substring(0, 6) + "01";
		}
		ogdate = getNextDateByMonth(ogdate, 1);
		ogdate = getNextDateByNum(ogdate, -1);
		return ogdate;
	}

	// 得到+i以后的日期，i可以是负数
	public static String getNextDateByNum(String s, int i) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
		java.util.Date date = simpledateformat.parse(s, new ParsePosition(0));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(5, i);
		date = calendar.getTime();
		s = simpledateformat.format(date);
		return s;
	}

	// 得到+i以后的月，i可以是负数
	public static String getNextDateByMonth(String s, int i) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
		java.util.Date date = simpledateformat.parse(s, new ParsePosition(0));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(2, i);
		date = calendar.getTime();
		s = simpledateformat.format(date);
		return s;
	}

	public static int getQuarter(String s) {
		if (s.length() < 6)
			return 0;
		s = s.substring(4, 6);
		int i = Integer.parseInt(s);
		i = (i - 1) / 4 + 1;
		return i;
	}

	public static String getQuarterLastDate(String s) {
		int i = getQuarter(s);
		if (i == 1)
			s = s.substring(0, 4) + "0331";
		if (i == 2)
			s = s.substring(0, 4) + "0630";
		if (i == 3)
			s = s.substring(0, 4) + "0930";
		if (i == 4)
			s = s.substring(0, 4) + "1231";
		return s;
	}

	public static String getTenDayDate(String s) {
		int i = (Integer.parseInt(s.substring(6, 8)) - 1) / 10 + 1;
		if (i == 1)
			s = s.substring(0, 6) + "10";
		if (i == 2)
			s = s.substring(0, 6) + "20";
		if (i > 2)
			s = getMonthLastDate(s);
		return s;
	}

	public static String getNextDateByQuarter(String s, int i) {
		i = i + getQuarter(s) - 1;
		int year = Integer.parseInt(s.substring(0, 4)) + i / 4;
		i = i % 4 + 1;
		if (i == 1)
			s = year + "0331";
		if (i == 2)
			s = year + "0630";
		if (i == 3)
			s = year + "0930";
		if (i == 4)
			s = year + "1231";
		return s;
	}

	public static boolean isRyear(String year) {
		return isRyear(Integer.parseInt(year));
	}

	public static boolean isRyear(int year) {
		if (year % 100 == 0 && year % 400 == 0 || year % 100 != 0
				&& year % 4 == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isDate(String strDate) {
		if (strDate == null || strDate.trim().equals("")) {
			return false;
		}
		if (strDate.length() != 8)
			return false;
		int year = Integer.parseInt(strDate.substring(0, 4));
		int month = Integer.parseInt(strDate.substring(4, 6));
		int day = Integer.parseInt(strDate.substring(6, 8));
		if (month < 1 || month > 12 || day < 1 || day > 31 || year < 1000
				|| year > 3000) {
			return false;
		} else if ((month == 4 || month == 6 || month == 9 || month == 11)
				&& (day > 30)) {
			return false;
		} else if (isRyear(year) && month == 2 && day > 29 || !isRyear(year)
				&& month == 2 && day > 28) {
			return false;
		} else {
			return true;
		}
	}

	// 取得strYearMonth以后的第i个月
	// strYearMonth为6位的年和月格式为“yyyymm”
	// 输出也为6位的年和月格式为“yyyymm”
	public static String getNextYearMonth(String strYearMonth, int i) {
		if ((strYearMonth.length() < 6))
			return "000000";
		int m = i % 12;
		if (m < 0) {
			m = 12 - m;
			i = i / 12 - 1;
		} else {
			i = i / 12;
		}
		int year = Integer.parseInt(strYearMonth.substring(0, 4)) + i;
		int month = Integer.parseInt(strYearMonth.substring(4, 6)) + m;
		if (month > 12) {
			year = year + 1;
			month = month - 12;
		}
		if (year < 0)
			return "0000101";
		if (year > 9999)
			return "999912";
		strYearMonth = "0" + month;
		strYearMonth = "0000"
				+ year
				+ strYearMonth.substring(strYearMonth.length() - 2,
						strYearMonth.length());
		strYearMonth = strYearMonth.substring(strYearMonth.length() - 6,
				strYearMonth.length());
		return strYearMonth;
	}

	// 将8位日期转换为6位日期
	// 如YYYYMMDD不足6位取返回6位的系统日期
	public static String getYYYYMM(String YYYYMMDD) {
		if (YYYYMMDD.length() < 6)
			YYYYMMDD = getCurrentDate();
		return YYYYMMDD.substring(0, 6);
	}

	// 将6位日期转换为8位日期取该月最后一天
	// 如YYYYMM不足6位返回系统日期的月末
	public static String getYYYYMMDD(String YYYYMM) {
		if (YYYYMM.length() < 6)
			YYYYMM = getCurrentDate();
		else
			YYYYMM = YYYYMM + "01";
		return getMonthLastDate(YYYYMM);
	}

	// 检查由strStartDate和strEndDate组成的多个时间段是否连续
	// 连续则输出ture，不连续则输出false
	public static boolean isContinuum(String[] strStartDate, String[] strEndDate) {
		HashMap<String, String> DateSect = new HashMap<String, String>();
		String strDate = strStartDate[0];
		for (int i = 0; i < strStartDate.length; i++) {
			DateSect.put(strStartDate[i], strEndDate[i]);
			if (Integer.parseInt(strDate) > Integer.parseInt(strStartDate[i]))
				strDate = strStartDate[i];
		}
		int i = 0;
		while (DateSect.get(strDate) != null) {
			strDate = (String) DateSect.get(strDate);
			strDate = getNextYearMonth(strDate, 1);
			i++;
		}
		;
		if (i == (strStartDate.length - 1))
			return true;
		else
			return false;
	}

	public static void main(String args[]) {
		String date = "20150623";
		try {
			date = getFormatYYYYMMDDDate(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(date);
	}

	/**
	 * 得到半年<br>
	 */
	public static int getHYear(String s) {
		if (s.length() < 6)
			return 1;
		s = s.substring(4, 6);
		int i = 0;
		if (s.equals("01") || s.equals("02") || s.equals("03")
				|| s.equals("04") || s.equals("05") || s.equals("06")) {
			i = 1;
		} else {
			i = 2;
		}
		return i;
	}

	/**
	 * 得到旬<br>
	 */
	public static int getX(String s) {
		if (s.length() < 6)
			return 1;
		s = s.substring(6, 8);
		int intX = Integer.parseInt(s);
		int i = 0;
		if (intX <= 10) {
			i = 1;
		} else if (intX <= 20 && intX > 10) {
			i = 2;
		} else if (intX > 20) {
			i = 3;
		}
		return i;
	}

	/**
	 * 获得本月月末日
	 * 
	 * @param sDate
	 *            日期
	 * @return String
	 * @throws Exception
	 */
	public static String getMonthEndDate(String sDate) throws Exception {
		String tmpD = sDate.substring(0, 6);
		return getNextDate(tmpD + "01", -1);

	}

	/**
	 * 获得上月月末日
	 * 
	 * @param sDate
	 *            日期
	 * @return String
	 * @throws Exception
	 */
	public static String getPreMonthEndDate(String sDate) throws Exception {
		checkDate(sDate);
		if (sDate.substring(4).equals("1331"))
			sDate = sDate.substring(0, 4) + "1231";
		String tmpD = sDate.substring(0, 6);
		return getNextDate(tmpD + "01", -1);
	}

	/**
	 * 获得上月月末日_13期
	 * 
	 * @param sDate
	 *            日期
	 * @return String
	 * @throws Exception
	 */
	public static String getPreMonthEndDate_13(String sDate) throws Exception {
		String s = "";
		if (sDate.substring(4).equals("1331"))
			s = sDate.substring(0, 4) + "1130";
		else {
			s = getPreMonthEndDate(sDate);
			if (s.substring(4).equals("1231"))
				s = s.substring(0, 4) + "1331";
		}
		return s;
	}

	/**
	 * 获得上季季末日
	 * 
	 * @param sDate
	 *            日期
	 * @return String
	 * @throws Exception
	 */
	public static String getPreQuarterEndDate(String sDate) throws Exception {
		checkDate(sDate);
		if (sDate.substring(4).equals("1331"))
			sDate = sDate.substring(0, 4) + "1231";
		String s = sDate.substring(4, 6);

		if (s.equals("01") || s.equals("02") || s.equals("03")) {
			// 返回上年年底日
			return getPreYearEndDate(sDate);
		} else if (s.equals("04") || s.equals("05") || s.equals("06")) {
			return sDate.substring(0, 4) + "0331";
			// return sDate.substring(0,4) + "0331" ;
		} else if (s.equals("07") || s.equals("08") || s.equals("09")) {
			return sDate.substring(0, 4) + "0630";
			// return sDate.substring(0,4) + "0630" ;
		} else if (s.equals("10") || s.equals("11") || s.equals("12")) {
			return sDate.substring(0, 4) + "0930";
			// return sDate.substring(0,4) + "0930" ;
		} else {
			throw new Exception("不存在的月份：" + s);
		}

	}

	/**
	 * 获得上季季末日_13期
	 * 
	 * @param sDate
	 *            日期
	 * @return String
	 * @throws Exception
	 */
	public static String getPreQuarterEndDate_13(String sDate) throws Exception {
		String s = "";
		if (sDate.substring(4).equals("1331")) {
			s = sDate.substring(0, 4) + "0930";
		} else {
			s = getPreQuarterEndDate(sDate);
			if (s.substring(4).equals("1231"))
				s = s.substring(0, 4) + "1331";
		}
		return s;
	}

	/**
	 * 获得上年同期日
	 * 
	 * @param sDate
	 *            日期
	 * @return String
	 * @throws Exception
	 */
	public static String getPreYearByDate(String sDate) throws Exception {
		checkDate(sDate);
		if (sDate.substring(4).equals("1331")) {
			int a = Integer.parseInt(sDate.substring(0, 4)) - 1;
			String s = a + "1331";
			return s;
		}
		dt = sdf.parse(sDate, new ParsePosition(0));
		cld.setTime(dt);
		// 计算新的Calendar
		cld.add(Calendar.YEAR, -1);
		return sdf.format(cld.getTime());
	}

	/**
	 * 获得上年年末日_13期
	 * 
	 * @param sDate
	 *            日期
	 * @return String
	 * @throws Exception
	 */
	public static String getPreYearEndDate_13(String sDate) throws Exception {
		String s = "";
		if (sDate.substring(4).equals("1331")) {
			int a = Integer.parseInt(sDate.substring(0, 4)) - 1;
			s = a + "1331";
		} else {
			s = getPreYearEndDate(sDate);
			if (s.substring(4).equals("1231"))
				s = s.substring(0, 4) + "1331";
		}
		return s;
	}

	/**
	 * 获得上半年年末日
	 * 
	 * @param sDate
	 *            日期
	 * @return String
	 * @throws Exception
	 */
	public static String getPreHalfYearEndDate(String sDate) throws Exception {
		checkDate(sDate);
		if (sDate.substring(4).equals("1331"))
			sDate = sDate.substring(0, 4) + "1231";
		String temp = sDate.substring(4, 6);
		if (Integer.parseInt(temp) <= 6) {
			return getPreYearEndDate(sDate);
		} else {
			String s = sDate.substring(0, 4) + "0630";
			return s;
		}

	}

	/**
	 * 获得上半年年末日_13期
	 * 
	 * @param sDate
	 *            日期
	 * @return String
	 * @throws Exception
	 */
	public static String getPreHalfYearEndDate_13(String sDate)
			throws Exception {
		String s = "";
		if (sDate.substring(4).equals("1331"))
			s = sDate.substring(0, 4) + "0630";
		else {
			s = getPreHalfYearEndDate(sDate);
			if (s.substring(4).equals("1231"))
				s = s.substring(0, 4) + "1331";
		}
		return s;
	}

	/**
	 * 获得本月月初日
	 * 
	 * @param sDate
	 *            日期
	 * @return String
	 * @throws Exception
	 */
	public static String getMonthEarlierDate(String sDate) throws Exception {
		return sDate.substring(0, 6) + "01";
	}

	/**
	 * 返回当前时间　HHmmss 24小时制
	 * 
	 * @return 当前时间　HHmmss
	 * @throws Exception
	 */
	public static String getTimes() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
		String s = sdf.format(Calendar.getInstance().getTime());
		return s;
	}

	/**
	 * /** 获得上年年末日
	 * 
	 * @param sDate
	 *            日期
	 * @return String
	 * @throws Exception
	 */
	public static String getPreYearEndDate(String sDate) throws Exception {
		checkDate(sDate);
		String s = "";
		s = sDate.substring(0, 4) + "0101";
		s = getNextDate(s, -1);
		return s;
	}

	// 得到 至给定日期止 的 当月天数
	public static int getDaysOfMonth(String date) {
		String days = date.substring(6, 8);
		int d = Integer.parseInt(days);
		return d;
	}

	// 得到 至给定日期止 的 当季天数
	public static int getDaysOfQuarter(String date) {
		String month = date.substring(4, 6);
		int d = Integer.parseInt(date.substring(6, 8));
		if ("01".equals(month)) {
			return d;
		} else if ("02".equals(month)) {
			return 31 + d;
		} else if ("03".equals(month)) {
			if (isRunYear(date)) {
				return 59 + d;
			} else {
				return 60 + d;
			}
		} else if ("04".equals(month)) {
			return d;
		} else if ("05".equals(month)) {
			return 30 + d;
		} else if ("06".equals(month)) {
			return 61 + d;
		} else if ("07".equals(month)) {
			return d;
		} else if ("08".equals(month)) {
			return 31 + d;
		} else if ("09".equals(month)) {
			return 62 + d;
		} else if ("10".equals(month)) {
			return d;
		} else if ("11".equals(month)) {
			return 31 + d;
		} else {
			return 61 + d;
		}
	}

	public static int getDaysOfYear(String date) throws Exception {
		String start = date.substring(0, 4) + "0101";
		return daysBetween(start, date) + 1;
	}

	public static boolean isRunYear(String date) {
		String year1 = date.substring(1, 4);
		int year = Integer.parseInt(year1);
		if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
			return true;
		}
		return false;
	}

	private static String[] parsePatterns = { "yyyy-MM-dd",
			"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyy/MM/dd",
			"yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd",
			"yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM" };

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}

	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatTimeyyyyMMdd(Date date) {
		return formatDate(date, "yyyyMMdd HH:mm:ss");
	}

	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd）
	 */
	public static String formatyyyyMMdd(Date date) {
		return formatDate(date, "yyyyMMdd");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}

	/**
	 * 日期型字符串转化为日期 格式 { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
	 * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy.MM.dd",
	 * "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * 
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (24 * 60 * 60 * 1000);
	}

	/**
	 * 获取过去的小时
	 * 
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (60 * 60 * 1000);
	}

	/**
	 * 获取过去的分钟
	 * 
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (60 * 1000);
	}

	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * 
	 * @param timeMillis
	 * @return
	 */
	public static String formatDateTime(long timeMillis) {
		long day = timeMillis / (24 * 60 * 60 * 1000);
		long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
		long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60
				* 1000 - min * 60 * 1000 - s * 1000);
		return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "."
				+ sss;
	}

	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}
}
