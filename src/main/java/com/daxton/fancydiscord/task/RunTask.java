package com.daxton.fancydiscord.task;

import com.daxton.fancydiscord.FancyDiscord;
import com.daxton.fancydiscord.api.Information;
import com.daxton.fancydiscord.config.FileConfig;

import java.util.Timer;
import java.util.TimerTask;

public class RunTask extends TimerTask {

	public static Timer timer;

	//登入時間
	public static String startTime = "";

	public static void execute(){
		//單位為分鐘
		int delay = FileConfig.config.getInt("status_check_time");

		if(delay < 1){
			delay = 1;
		}

		if(timer != null){
			timer.cancel();
		}

		timer = new Timer();

		timer.schedule(new RunTask(), delay * 60 * 1000L, delay * 60 * 1000L);

	}

	public void run(){

		//更改頻道為開啟標題
		FancyDiscord.discordBot.setTopic(Information.getTitle());

		double tps_lower_than = FileConfig.config.getDouble("tps_lower_than");

		double tps = Information.getTPS();

		if(tps < tps_lower_than){
			String tps_warning = FileConfig.languageConfig.getString("tps_warning");
			tps_warning = tps_warning.replace("{tps}",String.valueOf(tps));
			FancyDiscord.discordBot.sendMessage(tps_warning);
		}

		//設置玩家遊玩狀態
		String bot_status = FileConfig.languageConfig.getString("bot_status");
		bot_status = bot_status.replace("{player_now}", String.valueOf(FancyDiscord.minecraftServer.getPlayerCount()));
		FancyDiscord.discordBot.setActivity(bot_status);

	}



}
