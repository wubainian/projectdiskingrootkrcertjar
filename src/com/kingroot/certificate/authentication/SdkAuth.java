package com.kingroot.certificate.authentication;

import android.text.TextUtils;

public class SdkAuth{
	private static final String[] c = {"7CC749CFC0FB5677E6ABA342EDBDBA5A", "00B1208638DE0FCD3E920886D658DAF6", "191240FCB048127DB9110D1B30537FDE"};
	
	public static void main(String[] args){
		StringBuilder sb = new StringBuilder();
		sb.append(args[0]);//zipFilePath
		sb.append("+");
		sb.append(args[1]);//packageName
		sb.append("+");
		sb.append(args[2]);//packageMD5
		
		Object obj = SdkAuth.xx(0, sb.toString());
		
		if(null == obj){
			StringBuilder sb1 = new StringBuilder();
			sb1.append(args[1]);
			sb1.append(" ");
			sb1.append("F");
			System.out.println(sb1.toString());
		}else{
			StringBuilder sb1 = new StringBuilder();
			sb1.append(args[1]);
			sb1.append(" ");
			sb1.append("O");
			System.out.println(sb1.toString());
		}
	}
	public static Object xx(int intParam, String str){
		if( TextUtils.isEmpty(str) ){
			return null;
		}
		String[] strs = str.split("+");
		if(null == strs){
			return null;
		}
		if(strs.length < 2){
			return null;
		}
		String zipFilePath = strs[0];
		String packageName = strs[1];
		String packageMD5 = strs[2];
		for(int i=0; i< SdkAuth.c.length; i++){
			if(SdkAuth.c[i].equals(packageMD5)){
				System.out.println("V O");
				return new Object();
			}
		}
		int intRet = SdkAuth.verifyAuth(zipFilePath, packageName, packageMD5);
		if(1 == intRet){
			return new Object();
		}else{
			return null;
		}
	}
	
	public static int verifyAuth(String zipFilePath, String packageName, String packageMD5){
		a aObj = null;
		try{
			aObj = a.a(zipFilePath, "krsdk.cert");
			System.out.println("A O");
		}catch(Exception e){
			System.out.println("A F");
			e.printStackTrace();
		}
		if( null == aObj ){
			System.out.println("C F");
			return 0;
		}
		if( aObj.b(packageName, packageMD5) ){
			return 1;
		}
		System.out.println("C F");
		return 0;
	}
}