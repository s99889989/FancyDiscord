package com.daxton.fancydiscord.api.config;


import com.daxton.fancydiscord.FancyDiscord;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileConfiguration {

	String path;
	String jsonString;
	JsonObject jsonObject;

	public FileConfiguration(String path){
		this.path = path;
		jsonString = readJson(path);
		jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
	}

	//獲得字串
	public String getString(String inputKey){
		try {
			if(inputKey.contains(".")){
				String[] keyArray = inputKey.split("\\.");
				JsonObject jObject = null;
				for(int i = 0 ; i < keyArray.length ; i++){
					String key = keyArray[i];
					if(i == keyArray.length -1){
						if (jObject != null) {
							return jObject.get(key).getAsString();
						}
					}else if(i == 0){
						jObject = jsonObject.get(key).getAsJsonObject();
					}else {
						if (jObject != null) {
							jObject = jObject.get(key).getAsJsonObject();
						}
					}
				}
			}else {
				return jsonObject.get(inputKey).getAsString();
			}
		}catch (Exception exception){
			FancyDiscord.sendLogger("沒有這個key");
		}
		return "";
	}

	//獲得double
	public double getDouble(String inputKey){
		try {
			if(inputKey.contains(".")){
				String[] keyArray = inputKey.split("\\.");
				JsonObject jObject = null;
				for(int i = 0 ; i < keyArray.length ; i++){
					String key = keyArray[i];
					if(i == keyArray.length -1){
						if (jObject != null) {
							return jObject.get(key).getAsDouble();
						}
					}else if(i == 0){
						jObject = jsonObject.get(key).getAsJsonObject();
					}else {
						if (jObject != null) {
							jObject = jObject.get(key).getAsJsonObject();
						}
					}
				}
			}else {
				return jsonObject.get(inputKey).getAsDouble();
			}
		}catch (Exception exception){
			FancyDiscord.sendLogger("沒有這個key");
		}
		return 0;
	}

	//獲得int
	public int getInt(String inputKey){
		try {
			if(inputKey.contains(".")){
				String[] keyArray = inputKey.split("\\.");
				JsonObject jObject = null;
				for(int i = 0 ; i < keyArray.length ; i++){
					String key = keyArray[i];
					if(i == keyArray.length -1){
						if (jObject != null) {
							return jObject.get(key).getAsInt();
						}
					}else if(i == 0){
						jObject = jsonObject.get(key).getAsJsonObject();
					}else {
						if (jObject != null) {
							jObject = jObject.get(key).getAsJsonObject();
						}
					}
				}
			}else {
				return jsonObject.get(inputKey).getAsInt();
			}
		}catch (Exception exception){
			FancyDiscord.sendLogger("沒有這個key");
		}
		return 0;
	}

	//獲得long
	public long getLong(String inputKey){
		try {
			if(inputKey.contains(".")){
				String[] keyArray = inputKey.split("\\.");
				JsonObject jObject = null;
				for(int i = 0 ; i < keyArray.length ; i++){
					String key = keyArray[i];
					if(i == keyArray.length -1){
						if (jObject != null) {
							return jObject.get(key).getAsLong();
						}
					}else if(i == 0){
						jObject = jsonObject.get(key).getAsJsonObject();
					}else {
						if (jObject != null) {
							jObject = jObject.get(key).getAsJsonObject();
						}
					}
				}
			}else {
				return jsonObject.get(inputKey).getAsLong();
			}
		}catch (Exception exception){
			FancyDiscord.sendLogger("沒有這個key");
		}
		return 0;
	}

	//獲得boolean
	public boolean getBoolean(String inputKey){
		try {
			if(inputKey.contains(".")){
				String[] keyArray = inputKey.split("\\.");
				JsonObject jObject = null;
				for(int i = 0 ; i < keyArray.length ; i++){
					String key = keyArray[i];
					if(i == keyArray.length -1){
						if (jObject != null) {
							return jObject.get(key).getAsBoolean();
						}
					}else if(i == 0){
						jObject = jsonObject.get(key).getAsJsonObject();
					}else {
						if (jObject != null) {
							jObject = jObject.get(key).getAsJsonObject();
						}
					}
				}
			}else {
				return jsonObject.get(inputKey).getAsBoolean();
			}
		}catch (Exception exception){
			FancyDiscord.sendLogger("沒有這個key");
		}
		return false;
	}



	//獲得boolean
	public List<String> getStringList(String inputKey){
		List<String> stringList = new ArrayList<>();
		try {
			if(inputKey.contains(".")){
				String[] keyArray = inputKey.split("\\.");
				JsonObject jObject = null;
				for(int i = 0 ; i < keyArray.length ; i++){
					String key = keyArray[i];
					if(i == keyArray.length -1){
						JsonArray jsonArray = null;
						if (jObject != null) {
							jsonArray = jObject.get(key).getAsJsonArray();
						}
						if (jsonArray != null) {
							jsonArray.forEach(jsonElement -> stringList.add(jsonElement.getAsString()));
						}
						return stringList;
					}else if(i == 0){
						jObject = jsonObject.get(key).getAsJsonObject();
					}else {
						jObject = jObject.get(key).getAsJsonObject();
					}
				}
			}else {
				JsonArray jsonArray = jsonObject.get(inputKey).getAsJsonArray();
				jsonArray.forEach(jsonElement -> stringList.add(jsonElement.getAsString()));
				return stringList;
			}
		}catch (Exception exception){
			FancyDiscord.sendLogger("沒有這個key");
		}
		return stringList;
	}

	public void test(){
		//String s = "歡迎";
		try {
			jsonObject.addProperty("string", "修改值");
		}catch (IllegalStateException exception){
			exception.printStackTrace();
		}


	}

	public void set(String inputKey, Object value){
		try {
			if(inputKey.contains(".")){
				String[] keyArray = inputKey.split("\\.");
				JsonObject jObject = null;
				for(int i = 0 ; i < keyArray.length ; i++){
					String key = keyArray[i];
					if(i == keyArray.length -1){
						if(value instanceof String){
							if (jObject != null) {
								jObject.addProperty(inputKey, String.valueOf(value));
							}
						}
					}else if(i == 0){
						jObject = jsonObject.get(key).getAsJsonObject();
					}else {
						if (jObject != null) {
							jObject = jObject.get(key).getAsJsonObject();
						}
					}
				}
			}else {
				if(value instanceof String){
					jsonObject.addProperty(inputKey, String.valueOf(value));
				}
			}
		}catch (Exception exception){
			FancyDiscord.sendLogger("沒有這個key");
		}
	}

	public void print(){
		FancyDiscord.sendLogger(jsonObject.toString());
	}

	public void save(){
		try {
			Gson gson = new Gson();
			JsonWriter jsonWriter = new JsonWriter(new FileWriter(path));
			jsonWriter.setIndent("  ");
			gson.toJson(jsonObject, JsonObject.class, jsonWriter);
		}catch (IOException exception){
			exception.printStackTrace();
		}
	}

	public static String readJson(String path){
		try {
			return new String(Files.readAllBytes(Paths.get(path)));
		}catch (Exception exception){
			exception.printStackTrace();
		}
		return "";
	}

}
