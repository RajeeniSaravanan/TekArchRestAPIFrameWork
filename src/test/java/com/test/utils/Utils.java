package com.test.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import com.test.constants.*;

public class Utils {
	private FileInputStream stream = null;
	private Properties propFile = null;
	
	public Properties loadFile(String filename) {
		propFile=new Properties();
		String propertyFilePath=null;
		switch(filename) {
		case "configProperties":
			propertyFilePath=SourcePath.CONFIG_PROPERTIES_PATH;
			break;
		case "Properties":
			propertyFilePath=SourcePath.TEKARCH_DATA_PATH;
			break;
			
		}
		try {
			stream=new FileInputStream(propertyFilePath);
			propFile.load(stream);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return propFile;
		
	}
}
