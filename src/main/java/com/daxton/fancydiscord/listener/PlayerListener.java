package com.daxton.fancydiscord.listener;

import com.daxton.fancydiscord.FancyDiscord;
import com.daxton.fancydiscord.config.FileConfig;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerListener {

	//玩家登入
	@SubscribeEvent
	public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event){
		Player player = event.getPlayer();
		String name = player.getName().getString();
		String player_join = FileConfig.languageConfig.getString("player_join");
		player_join = player_join.replace("{player}", name);
		FancyDiscord.discordBot.sendMessage(player_join);
	}
	//玩家登出
	@SubscribeEvent
	public void onPlayerOut(PlayerEvent.PlayerLoggedOutEvent event){
		Player player = event.getPlayer();
		String name = player.getName().getString();
		String player_leave = FileConfig.languageConfig.getString("player_leave");
		player_leave = player_leave.replace("{player}", name);
		FancyDiscord.discordBot.sendMessage(player_leave);
	}
	//獲得成就
	@SubscribeEvent
	public void onAdvancement(AdvancementEvent event){
		boolean advancement_message = FileConfig.config.getBoolean("advancement_message");
		if(advancement_message){
			final DisplayInfo displayInfo = event.getAdvancement().getDisplay();
			Player player = event.getPlayer();
			String player_name = player.getName().getString();
			String description = "";
			if (displayInfo != null) {
				description = displayInfo.getDescription().getString();
			}
			if(!description.isEmpty()){
				String name = event.getAdvancement().getChatComponent().getString();
				String advancement = FileConfig.languageConfig.getString("advancement");
				advancement = advancement.replace("{player}", player_name).replace("{name}", name).replace("{description}", description);
				FancyDiscord.discordBot.sendMessage(advancement);
			}
		}


	}

	//玩家聊天
	@SubscribeEvent
	public void onServerChat(ServerChatEvent event){
		String message = event.getMessage();
		Player player = event.getPlayer();
		String name = player.getName().getString();
		String player_msg = FileConfig.languageConfig.getString("player_msg");
		player_msg = player_msg.replace("{player}", name);
		player_msg = player_msg.replace("{msg}", message);
		FancyDiscord.discordBot.sendMessage(player_msg);
	}

	//玩家死亡
	@SubscribeEvent
	public void onDeath(LivingDeathEvent event){
		LivingEntity livingEntity = event.getEntityLiving();
		if(livingEntity instanceof Player){
			boolean death_message = FileConfig.config.getBoolean("death_message");
			if(death_message){
				Player player = (Player) livingEntity;
				String name = player.getName().getString();
				String player_death = FileConfig.languageConfig.getString("player_death");
				player_death = player_death.replace("{player}", name);
				FancyDiscord.discordBot.sendMessage(player_death);
			}
		}
	}
	//玩家復活
	@SubscribeEvent
	public void onReSpawn(PlayerEvent.PlayerRespawnEvent event){
		boolean rebirth_message = FileConfig.config.getBoolean("rebirth_message");
		if(rebirth_message){
			Player player = event.getPlayer();
			String name = player.getName().getString();
			String player_rebirth = FileConfig.languageConfig.getString("player_rebirth");
			player_rebirth = player_rebirth.replace("{player}", name);
			FancyDiscord.discordBot.sendMessage(player_rebirth);
		}

	}

	//世界存檔
	@SubscribeEvent
	public void onWorldSave(WorldEvent.Save event){

	}



}

