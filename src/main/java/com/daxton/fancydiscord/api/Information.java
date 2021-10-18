package com.daxton.fancydiscord.api;

import com.daxton.fancydiscord.FancyDiscord;
import com.daxton.fancydiscord.config.FileConfig;
import com.daxton.fancydiscord.task.RunTask;
import net.minecraft.server.MinecraftServer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Information {


	public static double getTPS(){
		MinecraftServer minecraftServer = FancyDiscord.minecraftServer;
		double meanTickTime = mean(minecraftServer.tickTimes) * 1.0E-6D;
		return Math.min(1000.0/meanTickTime, 20);
	}

	public static String getTitle(){

		String channel_title_online = FileConfig.languageConfig.getString("channel_title_online");

		int player_now = FancyDiscord.minecraftServer.getPlayerList().getPlayers().size();
		int player_max = FancyDiscord.minecraftServer.getMaxPlayers();
		int total_players = getAllPlayerSize();
		double tps = Information.getTPS();


		String uptime = "" + FileConfig.languageConfig.getString("uptime");

		SubtractionTime subtractionTime = new SubtractionTime(RunTask.startTime, getNowTime());

		long day = subtractionTime.getDay();
		long hour = subtractionTime.getHour();
		long minute = subtractionTime.getMinute();
		long second = subtractionTime.getSecond();

		uptime = uptime.replace("{day}", String.valueOf(day))
			.replace("{hour}", String.valueOf(hour))
			.replace("{minute}", String.valueOf(minute))
			.replace("{second}", String.valueOf(second));

		channel_title_online = channel_title_online.replace("{player_now}", String.valueOf(player_now))
			.replace("{player_max}", String.valueOf(player_max))
			.replace("{total_players}", String.valueOf(total_players))
			.replace("{date}", getNowTime())
			.replace("{uptime}", uptime)
			.replace("{tps}", String.valueOf(tps));

		return channel_title_online;
	}


	public static String getNowTime(){
		Date dd=new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(dd);
	}

	public static int getAllPlayerSize(){
		File file = new File("world/playerdata");
		String[] s = file.list();
		if(s != null){
			//int size = s.length;
			return s.length;
		}
		return 0;
	}

	public static long mean(long[] values) {
		long sum = 0L;
		for (long v : values)
			sum += v;
		return sum / values.length;
	}

}
