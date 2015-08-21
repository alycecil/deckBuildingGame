package com.wcecil.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class StreamUtils {
	private static final int BUFFER_SIZE = 256;

	public static String convertStreamToString(InputStream is) {
		final char[] buffer = new char[BUFFER_SIZE];
		final StringBuilder out = new StringBuilder();
		try (Reader in = new InputStreamReader(is, "UTF-8")) {
			for (;;) {
				int rsz = in.read(buffer, 0, buffer.length);
				if (rsz < 0)
					break;
				out.append(buffer, 0, rsz);
			}
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return out.toString();
	}
}
