package com.rocketbot.commands;

import com.rocketbot.listeners.MessageCreate;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.json.simple.JSONObject;

public class Command_MemberCount {

	public Command_MemberCount(MessageCreateEvent e, Message m, String mc, String[] args, EmbedBuilder embed, JSONObject json) {
		Server server = m.getServer().get();
		embed.setTitle("Member Count");
		embed.setDescription("The current member count of __" + server.getName() + "__ is **" + server.getMemberCount() + "**!");
		MessageCreate.sendBack(embed);
	}
}
