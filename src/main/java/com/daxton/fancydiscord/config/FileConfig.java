package com.daxton.fancydiscord.config;

import com.daxton.fancydiscord.api.config.FileConfiguration;

public class FileConfig {

	public static FileConfiguration config;

	public static FileConfiguration languageConfig;

	public static void execute(){

		config = new FileConfiguration("config/FancyDiscord/config.json");

		String languageString = config.getString("language");

		languageConfig =  new FileConfiguration("config/FancyDiscord/language/"+languageString+".json");

	}

	public static void reload(){

		config = new FileConfiguration("config/FancyDiscord/config.json");

		String languageString = config.getString("language");

		languageConfig =  new FileConfiguration("config/FancyDiscord/language/"+languageString+".json");

	}

}
