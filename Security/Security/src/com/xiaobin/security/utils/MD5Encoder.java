package com.xiaobin.security.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Encoder {
	public static String encode(String pwd)
	{
		try{
			MessageDigest messageDigest  = MessageDigest.getInstance("MD5");
			byte[] bytes = messageDigest.digest(pwd.getBytes());
			StringBuffer sb =  new StringBuffer();
			String tmp;
			for(int i=0; i< bytes.length; i++)
			{
				tmp = Integer.toHexString(0xff & bytes[i]);
				if(tmp.length() ==1)
				{
					sb.append("0" +tmp);
				}
				else
				{
					sb.append(tmp);
				}
			}
		}catch(NoSuchAlgorithmException e)
		{
			throw new RuntimeException("û����������㷨" + e);
		}
		return pwd;
	}
}