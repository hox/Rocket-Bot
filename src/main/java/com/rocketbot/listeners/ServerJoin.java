package com.rocketbot.listeners;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.server.ServerJoinEvent;
import org.javacord.api.listener.server.ServerJoinListener;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.rocketbot.main.Main;

@SuppressWarnings("unchecked")
public class ServerJoin implements ServerJoinListener {

	public void onServerJoin(ServerJoinEvent e) {
		String path = (Main.devmode ? "f:\\\\config.json" : "./config/config.json");
		try {
			JSONParser parser = new JSONParser();
			JSONObject data = (JSONObject) parser.parse(new FileReader(path));
			JSONObject prefix = new JSONObject();
			prefix.put("prefix", "*");
			data.put(e.getServer().getIdAsString(), prefix);
			FileWriter file = new FileWriter(path);
			file.write(data.toJSONString());
			file.flush();
			file.close();
		} catch (IOException | ParseException e2) {
			e2.printStackTrace();
		}
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Server Join Message");
		embed.setDescription("Rocket has joined __" + e.getServer().getName() + "__ with a member count of: **" + e.getServer().getMemberCount() + "**!");
		Main.api.getChannelById(493452917194620959l).get().asServerTextChannel().get().sendMessage(embed);
	}

	public static void onServerJoin(String id, String putprefix) {
		String path = (Main.devmode ? "f:\\\\config.json" : "./config/config.json");
		try {
			JSONParser parser = new JSONParser();
			JSONObject data = (JSONObject) parser.parse(new FileReader(path));
			JSONObject prefix = new JSONObject();
			prefix.put("prefix", putprefix);
			data.put(id, prefix);
			FileWriter file = new FileWriter(path);
			file.write(data.toJSONString());
			file.flush();
			file.close();
		} catch (IOException | ParseException e2) {
			e2.printStackTrace();
		}
	}
}
