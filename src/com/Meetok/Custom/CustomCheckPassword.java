package com.Meetok.Custom;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomCheckPassword {
	public final static String format = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";//密码格式
	public static Boolean checkPassword(String password){
		Pattern p = Pattern.compile(format);   
	    Matcher m = p.matcher(password);   
	    return m.matches();
	}
}
