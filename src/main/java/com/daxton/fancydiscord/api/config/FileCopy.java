package com.daxton.fancydiscord.api.config;

import com.daxton.fancydiscord.FancyDiscord;

import java.io.*;

public class FileCopy {

	public static void resourceCopy(String mainPath, String resourcePath, String savePath, boolean replace){

		//建立缺失資料夾
		makeFile(mainPath, savePath);

		if(savePath.contains(".")){
			File outFile = new File(mainPath, savePath);
			try {
				InputStream in = FancyDiscord.fancyDiscord.getClass().getClassLoader().getResourceAsStream(resourcePath);
				if(in != null){
					if (!outFile.exists() || replace) {
						OutputStream out = new FileOutputStream(outFile);
						byte[] buf = new byte[1024];
						int len;
						while ((len = in.read(buf)) > 0) {
							out.write(buf, 0, len);
						}
						out.close();
						in.close();
					}
				}
			}catch (IOException exception) {
				//exception.printStackTrace();
			}
		}

	}

	//建立缺失資料夾
	public static void makeFile(String mainPath, String dest){
		int lastIndex = dest.lastIndexOf('/');
		File outDir = new File(mainPath, dest.substring(0, lastIndex >= 0 ? lastIndex : 0));
		if (!outDir.exists()) {
			outDir.mkdirs();
		}
	}

}
