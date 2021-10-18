package com.daxton.fancydiscord;

import com.daxton.fancydiscord.api.DiscordBot;
import com.daxton.fancydiscord.api.Information;
import com.daxton.fancydiscord.command.CommandMain;
import com.daxton.fancydiscord.config.FileConfig;
import com.daxton.fancydiscord.listener.PlayerListener;
import com.daxton.fancydiscord.task.RunTask;
import com.daxton.fancydiscord.task.server.Start;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fmlserverevents.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmlserverevents.FMLServerStoppedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;


@Mod("fancydiscord")
public class FancyDiscord {

	private static final Logger LOGGER = LogManager.getLogger();

	public static FancyDiscord fancyDiscord;

	public static MinecraftServer minecraftServer;

	public static DiscordBot discordBot;

	public FancyDiscord() {
		fancyDiscord = this;

		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {
		//監聽註冊
		MinecraftForge.EVENT_BUS.register(new PlayerListener());
		//出使動作
		Start.execute();
		//伺服器正在啟動訊息
		String server_starting = FileConfig.languageConfig.getString("server_starting");
		discordBot.sendStartingMessage(server_starting);
	}


	@SubscribeEvent
	public void onServerStarting(FMLServerStartingEvent event) {
		minecraftServer = event.getServer();
		CommandMain.register(event.getServer().getCommands().getDispatcher());
		//伺服器啟動訊息
		String server_started = FileConfig.languageConfig.getString("server_started");
		discordBot.deleteSetMessage();
		discordBot.sendMessage(server_started);
		//紀錄開啟時間
		Date dd=new Date();
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		RunTask.startTime = sim.format(dd);
		//更改頻道為開啟標題
		discordBot.setTopic(Information.getTitle());
		//定時任務
		RunTask.execute();
	}

	@SubscribeEvent
	public void onServerStop(FMLServerStoppedEvent event){
		//停止持續任務
		RunTask.timer.cancel();
		//伺服器停止訊息
		String server_stopped = FileConfig.languageConfig.getString("server_stopped");
		discordBot.sendMessage(server_stopped);
		//更改頻道為關閉標題
		String channel_title_offline = FileConfig.languageConfig.getString("channel_title_offline");
		discordBot.setTopic(channel_title_offline);
		//機器人離線
		FancyDiscord.discordBot.cancel();
	}

	//發送後台訊息
	public static void sendLogger(String message){
		LOGGER.info(message);
	}

}
