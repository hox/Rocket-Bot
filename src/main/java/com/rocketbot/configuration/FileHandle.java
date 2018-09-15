package com.rocketbot.configuration;

import java.io.InputStream;

public interface FileHandle {

	public static InputStream inputStreamFromFile(String path) {
		try {
			InputStream inputStream = FileHandle.class.getResourceAsStream(path);
			return inputStream;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
