package com.rocketbot.listeners;

import com.rocketbot.main.Main;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.server.ServerJoinEvent;
import org.javacord.api.listener.server.ServerJoinListener;
import org.json.simple.JSONObject;

@SuppressWarnings("unchecked")
public class ServerJoin implements ServerJoinListener {

	public void onServerJoin(ServerJoinEvent e) {
		JSONObject data = Main.getConfig();
		JSONObject prefix = new JSONObject();
		prefix.put("prefix", "*");
		data.put(e.getServer().getIdAsString(), prefix);
		Main.setConfig(data);
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Server Join Message");
		embed.setDescription("Rocket has joined __" + e.getServer().getName() + "__ with a member count of: **" + e.getServer().getMemberCount() + "**!");
		Main.api.getChannelById(493452917194620959l).get().asServerTextChannel().get().sendMessage(embed);
	}

	public static void onServerJoin(String id, String putprefix, JSONObject json) {
		JSONObject data = json;
		JSONObject prefix = new JSONObject();
		prefix.put("prefix", putprefix);
		data.put(id, prefix);
		Main.setConfig(data);
	}
}
