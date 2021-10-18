package com.daxton.fancydiscord.api.config;

import java.util.List;

public class ConfigCreate {

	//執行建立設定檔
	public static void execute(List<String> fileList){
		try {
			for(String patch : fileList){
				String savePath = patch.replace("resource/", "");
				String pluginPatch = "config/FancyDiscord/";
				FileCopy.resourceCopy(pluginPatch, patch, savePath, false);
			}
		}catch (Exception exception){
			//exception.printStackTrace();
		}
	}



}
