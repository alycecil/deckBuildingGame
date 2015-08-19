package com.wcecil.common.util;

public class StreamUtils {
	static String convertStreamToString(java.io.InputStream is) {
		try(java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A")){
			String res =  s.hasNext() ? s.next() : "";
			return res;
		} finally {
		}
	}
}
