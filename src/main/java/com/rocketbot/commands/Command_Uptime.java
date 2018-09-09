package com.rocketbot.commands;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import com.rocketbot.listeners.MessageCreate;
import com.rocketbot.main.Main;

public class Command_Uptime {

	public Command_Uptime(MessageCreateEvent e, Message m, String mc, String args[], EmbedBuilder embed) {
		Main.api.addResumeListener(event -> Main.lastResume = System.currentTimeMillis());
		Main.api.addReconnectListener(event -> Main.lastReconnect = System.currentTimeMillis());
		embed.addField("The uptime is: ", Main.format(System.currentTimeMillis() - Main.lastRestart), false);
		MessageCreate.sendBack(embed);
	}
}
