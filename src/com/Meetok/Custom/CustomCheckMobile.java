package com.Meetok.Custom;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomCheckMobile {
	public final static String format = "^1[3|4|5|7|8][0-9]\\d{8}$";//手机号格式
	public static Boolean checkMobile(String mobile){
		Pattern p = Pattern.compile(format);   
	    Matcher m = p.matcher(mobile);   
	    return m.matches();
	}
}
