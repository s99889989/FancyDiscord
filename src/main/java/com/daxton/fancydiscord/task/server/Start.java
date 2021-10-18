package com.daxton.fancydiscord.task.server;

import com.daxton.fancydiscord.FancyDiscord;
import com.daxton.fancydiscord.api.DiscordBot;
import com.daxton.fancydiscord.api.config.ConfigCreate;
import com.daxton.fancydiscord.config.FileConfig;

import java.util.ArrayList;
import java.util.List;

public class Start {
	//開服執行
	public static void execute(){
		//建立設定檔
		ConfigCreate.execute(Start.getFileList());
		//讀取設定檔
		FileConfig.execute();
		//Discord機器人
		String token = FileConfig.config.getString("bot_token");
		long id = FileConfig.config.getLong("bot_channel");
		FancyDiscord.discordBot = new DiscordBot(token, id);

	}

	public static List<String> getFileList(){
		List<String> fileList = new ArrayList<>();
		fileList.add("resource/config.json");
		fileList.add("resource/language/Chinese_CN.json");
		fileList.add("resource/language/Chinese_TW.json");
		fileList.add("resource/language/English.json");
		fileList.add("resource/language/Korean_KR.json");
		fileList.add("resource/language/Portuguese_BR.json");
		return fileList;
	}

}
