package com.daxton.fancydiscord.api;

import com.daxton.fancydiscord.FancyDiscord;
import com.daxton.fancydiscord.config.FileConfig;
import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.Channel;
import discord4j.core.object.entity.channel.TextChannel;
import discord4j.core.object.presence.Activity;
import discord4j.core.object.presence.Presence;
import discord4j.discordjson.json.ImmutableChannelModifyRequest;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DiscordBot {

    public DiscordClient client;

    public GatewayDiscordClient gateway;

    public long channelID;

    boolean state;

    String token;

    Message startingMessage;

    TextChannel channel;
    //關閉連線
    public void cancel(){
        gateway.logout().block();
    }
    //新機器人
    public DiscordBot(String token, long channelID){
        this.token = token;
        this.channelID = channelID;

        String bot_error_token = FileConfig.languageConfig.getString("bot_error_token");
        String bot_error_channel = FileConfig.languageConfig.getString("bot_error_channel");

        try {
            client = DiscordClient.create(token);

            gateway = client.login().block();

        }catch (Exception exception){
            FancyDiscord.sendLogger(bot_error_token);
        }
        try {
            if(gateway != null){
                channel = (TextChannel) gateway.getChannelById(Snowflake.of(channelID)).block();
                if(channel != null){
                    state = true;
                    DiscordListener();
                    //設置機器人遊玩狀態
                    String bot_status = FileConfig.languageConfig.getString("bot_status");
                    bot_status = bot_status.replace("{player_now}", String.valueOf(FancyDiscord.minecraftServer.getPlayerCount()));
                    setActivity(bot_status);
                }
            }
        }catch (Exception exception){
            FancyDiscord.sendLogger(bot_error_channel);
        }
    }

    //Discord訊息監聽
    public void DiscordListener(){

        CompletableFuture.runAsync(()->{
            DiscordClient.create(token)
                .withGateway(client ->
                    client.on(MessageCreateEvent.class, event -> {
                        if(event.getMessage().getChannelId().asLong() == channelID){
                            Message message = event.getMessage();
                            User user = message.getAuthor().get();
                            if(!user.isBot()){

                                String messageString = message.getContent();
                                if(messageString.equalsIgnoreCase("!tps")){
                                    String discord_command_tps = FileConfig.languageConfig.getString("discord_command_tps");
                                    discord_command_tps = discord_command_tps.replace("{tps}", String.valueOf(Information.getTPS()));
                                    String finalDiscord_command_tps = discord_command_tps;
                                    return message.getChannel().flatMap(channel -> channel.createMessage(finalDiscord_command_tps));
                                }

                                if(messageString.equalsIgnoreCase("!list")){
                                    String discord_command_list = FileConfig.languageConfig.getString("discord_command_list");
                                    List<ServerPlayer> serverPlayerList = FancyDiscord.minecraftServer.getPlayerList().getPlayers();
                                    String playerCount = String.valueOf(FancyDiscord.minecraftServer.getPlayerCount());
                                    StringBuilder playerString = new StringBuilder();
                                    for(int i = 0; i < serverPlayerList.size() ; i++){
                                        if(i != 0){
                                            playerString.append(", ");
                                        }
                                        String playerName = serverPlayerList.get(i).getName().getString();
                                        playerString.append(playerName);
                                    }
                                    discord_command_list = discord_command_list.replace("{player_now}", playerCount).replace("{player_list}", playerString.toString());

                                    String finalDiscord_command_list = discord_command_list;
                                    return message.getChannel().flatMap(channel -> channel.createMessage(finalDiscord_command_list));

                                }
                                String discord_msg = FileConfig.languageConfig.getString("discord_msg");
                                String userName = message.getAuthor().get().getUsername();
                                discord_msg = discord_msg.replace("{user}", userName).replace("{msg}", messageString);
                                for(ServerPlayer serverPlayer : FancyDiscord.minecraftServer.getPlayerList().getPlayers()){
                                    serverPlayer.sendMessage(new TextComponent(discord_msg), serverPlayer.getUUID());
                                }
                                FancyDiscord.sendLogger(discord_msg);

                            }
                        }
                        return Mono.empty();
                    }))
                .block();

        });
    }
    //發送訊息
    public void sendMessage(String message){
        if(state){
            CompletableFuture.runAsync(()-> channel.createMessage(message).block());
        }
    }
    //發送紀錄訊息
    public void sendStartingMessage(String message){
        if(state){
            startingMessage = channel.createMessage(message).block();
        }
    }
    //刪除紀錄訊息
    public void deleteSetMessage(){
        if(state){
            startingMessage.delete().block();
        }
    }

    //設置機器人遊玩狀態
    public void setActivity(String activity){
        if(state){
            gateway.updatePresence(Presence.online(Activity.playing(activity))).subscribe();
        }
    }

    //設置頻道主題
    public void setTopic(String message){
        if(state){
            channel.edit(textChannelEditSpec -> textChannelEditSpec.setTopic(message)).subscribe();
        }
    }

    //設置頻道名稱
    public void setName(String message){
        CompletableFuture.runAsync(()->{
            Channel channel = gateway.getChannelById(Snowflake.of(channelID)).block();
            assert channel != null;
            channel.getRestChannel().modify(ImmutableChannelModifyRequest.builder().name(message).build(), "demo").block();
        });
    }
    //獲取頻道名稱
    public String getName(){
        if(state){
            TextChannel channel = (TextChannel) gateway.getChannelById(Snowflake.of(channelID)).block();
            assert channel != null;
            return channel.getName();
        }
        return "";
    }



}
