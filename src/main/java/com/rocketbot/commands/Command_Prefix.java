package com.rocketbot.commands;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import com.rocketbot.listeners.MessageCreate;
import com.rocketbot.main.Main;

public class Command_Prefix {

	public Command_Prefix(MessageCreateEvent e, Message m, String mc, String[] args, EmbedBuilder embed) {
		String prefix = Main.getPrefix(m.getServer().get().getIdAsString());
		embed.setTitle("Prefix");
		embed.setDescription("The current prefix for __" + m.getServer().get().getName() + "__ is `" + prefix + "`");
		MessageCreate.sendBack(embed);
	}
}
