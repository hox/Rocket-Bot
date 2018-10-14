package com.rocketbot.listeners;

import com.rocketbot.main.Main;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.server.ServerLeaveEvent;
import org.javacord.api.listener.server.ServerLeaveListener;
import org.json.simple.JSONObject;

public class ServerLeave implements ServerLeaveListener {

	@Override
	public void onServerLeave(ServerLeaveEvent e) {
		JSONObject data = Main.getConfig();
		data.remove(e.getServer().getIdAsString());
		Main.setConfig(data);
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Server Leave Message");
		embed.setDescription("Rocket has left __" + e.getServer().getName() + "__ with a member count of: **" + e.getServer().getMemberCount() + "**!");
		Main.api.getChannelById(493452917194620959l).get().asServerTextChannel().get().sendMessage(embed);
	}
}
