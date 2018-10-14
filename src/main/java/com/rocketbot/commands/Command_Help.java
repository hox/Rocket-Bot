package com.rocketbot.commands;

import com.rocketbot.listeners.MessageCreate;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.json.simple.JSONObject;

public class Command_Help {

	public Command_Help(MessageCreateEvent e, Message m, String mc, String[] args, EmbedBuilder embed, JSONObject json) {
		embed.setTitle("Help Command");
		embed.setDescription("To view all commands please [click here!](https://rocketbot.xyz)");
		MessageCreate.sendBack(embed);
	}
}
