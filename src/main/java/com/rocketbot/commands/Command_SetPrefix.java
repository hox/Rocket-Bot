package com.rocketbot.commands;

import java.awt.Color;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import com.rocketbot.listeners.MessageCreate;
import com.rocketbot.listeners.ServerJoin;

public class Command_SetPrefix {

	public Command_SetPrefix(MessageCreateEvent e, Message m, String mc, String[] args, EmbedBuilder embed) {
		if(args.length < 2) {
			embed.setTitle("Error");
			embed.setDescription("You must provide a prefix to set for the server! [*setprefix PREFIX]");
			embed.setColor(Color.red);
		} else {
			if(m.getAuthor().canManageMessagesInTextChannel()) {
				String newprefix = args[1];
				ServerJoin.onServerJoin(m.getServer().get().getIdAsString(), newprefix);
				embed.setTitle("Prefix Change");
				embed.setDescription("You have set the prefix of __" + m.getServer().get().getName() + "__ to `" + newprefix + "`");
			} else {
				embed.setTitle("Error");
				embed.setDescription("You do not have permissions to execute this command!");
				embed.setColor(Color.red);
			}
		}
		MessageCreate.sendBack(embed);
	}
}
