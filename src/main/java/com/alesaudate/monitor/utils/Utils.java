package com.alesaudate.monitor.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Utils {
	
	
	public static Properties loadProperties(String propertiesFile) throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(propertiesFile));
		
		return properties;
	}

}
