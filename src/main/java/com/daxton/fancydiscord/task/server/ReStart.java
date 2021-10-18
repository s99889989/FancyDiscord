package com.daxton.fancydiscord.task.server;


import com.daxton.fancydiscord.FancyDiscord;
import com.daxton.fancydiscord.api.DiscordBot;
import com.daxton.fancydiscord.api.config.ConfigCreate;
import com.daxton.fancydiscord.config.FileConfig;
import com.daxton.fancydiscord.task.RunTask;

public class ReStart {

	public static void execute(){
		//建立設定檔
		ConfigCreate.execute(Start.getFileList());
		//讀取設定檔
		FileConfig.reload();
		//Discord機器人
		String token = FileConfig.config.getString("bot_token");
		long id = FileConfig.config.getLong("bot_channel");
		FancyDiscord.discordBot.cancel();
		FancyDiscord.discordBot = new DiscordBot(token, id);
		//定時任務
		RunTask.execute();

	}

}
