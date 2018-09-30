package com.rocketbot.commands;

import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import com.rocketbot.main.Main;

public class Command_BotInfo {

	public Command_BotInfo(MessageCreateEvent e, Message m, String mc, String[] args, EmbedBuilder embed) {
		try {
			embed.setTitle("Bot Info");
			embed.setDescription("A description of the bot!");
			embed.addField("Creator", Main.api.getUserById(223217915673968641l).get().getDiscriminatedName(), true);
		} catch (InterruptedException | ExecutionException e1) {
			e1.printStackTrace();
		}
	}
}
