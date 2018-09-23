package com.rocketbot.listeners;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.server.ServerLeaveEvent;
import org.javacord.api.listener.server.ServerLeaveListener;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.rocketbot.main.Main;

public class ServerLeave implements ServerLeaveListener {

	@Override
	public void onServerLeave(ServerLeaveEvent e) {
		String path = (Main.devmode ? "f:\\\\config.json" : "./config/config.json");
		try {
			JSONParser parser = new JSONParser();
			JSONObject data = (JSONObject) parser.parse(new FileReader(path));
			data.remove(e.getServer().getIdAsString());
			FileWriter file = new FileWriter(path);
			file.write(data.toJSONString());
			file.flush();
			file.close();
		} catch (IOException | ParseException e2) {
			e2.printStackTrace();
		}
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Server Leave Message");
		embed.setDescription("Rocket has left __" + e.getServer().getName() + "__ with a member count of: **" + e.getServer().getMemberCount() + "**!");
		Main.api.getChannelById(493452917194620959l).get().asServerTextChannel().get().sendMessage(embed);
	}
}
