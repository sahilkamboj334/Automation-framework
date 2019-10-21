package com.framework.utill;

public class XLSUtil {

	public static boolean isXLSType(String str){
		return str.endsWith(".xls")?true:false;
	}
	public static boolean isXLSXType(String str){
		return str.endsWith(".xlsx")?true:false;
	}
}
