package com.rocketbot.commands;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import com.rocketbot.listeners.MessageCreate;

public class Command_Purge {

	public Command_Purge(MessageCreateEvent e, Message m, String mc, String[] args, EmbedBuilder embed) {
		if(args.length <= 1) {
			embed.setTitle("Error");
			embed.setDescription("You must provide how many messages you want to remove! [*purge 100]");
			embed.setColor(Color.red);
		} else {
			int md = Integer.parseInt(args[1]);
			if(!m.getAuthor().canManageMessagesInTextChannel()) {
				embed.setTitle("Error");
				embed.setDescription("You do not have permissions to execute this command!");
				embed.setColor(Color.red);
			} else {
				List<Message> messages;
				try {
					messages = m.getChannel().getMessages(md).get().stream().collect(Collectors.toList());
					m.getChannel().bulkDelete(messages);
					embed.setTitle("Success!");
					embed.setDescription("You have successfully removed " + md + " messages from this channel!");
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				} catch (ExecutionException e1) {
					e1.printStackTrace();
				}	
			}
		}
		MessageCreate.sendBack(embed);
	}
}
