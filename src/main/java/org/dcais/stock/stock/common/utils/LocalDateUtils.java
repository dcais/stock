package org.dcais.stock.stock.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jni.Local;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 */
@Slf4j
public class LocalDateUtils {
  public static final String Y_M_D = "yyyy-MM-dd";
  public static final String Y_M = "yyyy-MM";
  public static final String Y = "yyyy";
  public static final String Y_M_D_H = "yyyy-MM-dd HH";
  public static final String Y_M_D_HM = "yyyy-MM-dd HH:mm";
  public static final String Y_M_D_HMS = "yyyy-MM-dd HH:mm:ss";
  public static final String Y_M_D_HMSS = "yyyy-MM-dd HH:mm:ss.SSS";
  public static final String ISO_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ";
  public static final String Y_M_D_HMS_CN = "yyyy年M月d日 HH:mm:ss";
  public static final String YMD = "yyyyMMdd";
  public static final String YM = "yyyyMM";
  public static final String D = "dd";
  public static final String MD = "MMdd";
  public static final String YMDHM = "yyyyMMddHHmm";
  public static final String YMDHMS = "yyyyMMddHHmmss";
  public static final String ymd = "yyyy/MM/dd";
  public static final String ymd_HM = "yyyy/MM/dd HH:mm";
  public static final String ymd_HMS = "yyyy/MM/dd HH:mm:ss";
  public static final String YYMMDD = "yyMMdd";
  public static final String HM = "HH:mm";
  public static final String H = "HH";
  public static final String HMS = "HH:mm:ss";
  public static final String HMSS = "HHmmssSSS";
  public static final String Y_POINT_M_POINT_D = "yyyy.MM.dd";
  public static final String Y_POINT_POINT_M_POINT_D_POINT_H_POINT_M_POINT_S = "yyyy.MM.dd.HH.mm.ss";
  public static final String YMDH = "yyyyMMddHH";
  public static final String M = "mm";
  public static final String YYYYMMDD = "yyyy年MM月dd日";

  public static Date asDate(LocalDate localDate) {
    return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
  }

  public static Date asDate(LocalDateTime localDateTime) {
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }

  public static LocalDate asLocalDate(Date date) {
    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
  }

  public static LocalDateTime asLocalDateTime(Date date) {
    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
  }


  public static String formatToStr(LocalDateTime localDateTime, String format) {
    if (localDateTime == null || format == null) {
      return null;
    }
    LocalDateTime dt = LocalDateTime.now();
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern(format);
    return dt.format(fmt);
  }

  public static LocalDateTime formatToDate(String str, String format) {
    if (StringUtils.isBlank(str) || StringUtils.isBlank(format)) {
      return null;
    }
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern(format);
    try{
      LocalDateTime dt = LocalDateTime.parse(str, fmt);
      return dt;
    }catch (Exception e){
      log.error("",e);
      return null;
    }
  }
  public static String smartFormat(LocalDateTime localDateTime) {
    String dateStr = null;
    if (localDateTime == null) {
      localDateTime = LocalDateTime.now();
    }
    dateStr = formatToStr(localDateTime, ISO_DATE_TIME);
    return dateStr;
  }

  public static LocalDateTime smartFormat (String text){
    LocalDateTime localDateTime = null;
    if (text == null || text.length() == 0) {
      localDateTime = null;
      return localDateTime;
    }

    if (text.length() == 8) {
      localDateTime = formatToDate(text, YMD);
    } else if (text.length() == 10) {
      localDateTime = formatToDate(text, Y_M_D);
    } else if (text.length() == 13) {
      localDateTime = formatToDate(text, Y_M_D_H);
    } else if (text.length() == 16) {
      localDateTime = formatToDate(text, Y_M_D_HM);
    } else if (text.length() == 19) {
      localDateTime = formatToDate(text, Y_M_D_HMS);
    } else if (text.length() == 28) {
      localDateTime = formatToDate(text, ISO_DATE_TIME);
    } else {
      throw new IllegalArgumentException("日期长度不符合要求!");
    }
    if (localDateTime == null ){
      throw new IllegalArgumentException("日期转换失败!");
    }

    return localDateTime;
  }

}
