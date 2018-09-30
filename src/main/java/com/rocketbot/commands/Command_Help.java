package com.rocketbot.commands;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import com.rocketbot.listeners.MessageCreate;

public class Command_Help {

	public Command_Help(MessageCreateEvent e, Message m, String mc, String args[], EmbedBuilder embed) {
		embed.setTitle("Help Command");
		embed.setDescription("To view all commands please [click here!](https://rocketbot.xyz)");
		MessageCreate.sendBack(embed);
	}
}
