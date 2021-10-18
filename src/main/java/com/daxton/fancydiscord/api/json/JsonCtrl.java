package com.daxton.fancydiscord.api.json;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

public class JsonCtrl {

	//儲存物件到檔案裡面
	public static void writeJSON(String path, Object object) {
		try {
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			FileWriter writer = new FileWriter(path);
			writer.write(gson.toJson(object));
			writer.close();
		}catch (IOException exception){
			//
		}
	}

}
