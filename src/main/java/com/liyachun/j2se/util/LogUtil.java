package com.liyachun.j2se.util;

public class LogUtil {

	public static String log(String message){
		
		String dataString = DateUtil.current();
		
		String newMessage = String.format("%s - %s", dataString,message);
		
		return newMessage;
	}
}
