package com.rocketbot.commands;

import com.rocketbot.listeners.MessageCreate;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Command_Purge {

	public Command_Purge(MessageCreateEvent e, Message m, String mc, String[] args, EmbedBuilder embed, JSONObject json) {
		if(args.length <= 1) {
			embed.setTitle("Error");
			embed.setDescription("You must provide how many messages you want to remove! [*purge 100]");
			embed.setColor(Color.red);
		} else {
			int md;
			try{
				md = Integer.parseInt(args[1]);
			} catch(NumberFormatException e2) {
				embed.setTitle("Error");
				embed.setDescription("You must provide a valid integer for how many messages to remove");
				embed.setColor(Color.red);
				MessageCreate.sendBack(embed);
				return;
			}
			if(!m.getAuthor().canManageMessagesInTextChannel()) {
				embed.setTitle("Error");
				embed.setDescription("You do not have permissions to execute this command!");
				embed.setColor(Color.red);
			} else {
				List<Message> messages;
				try {
					messages = new ArrayList<>(m.getChannel().getMessages(md).get());
					m.getChannel().bulkDelete(messages);
					embed.setTitle("Success!");
					embed.setDescription("You have successfully removed " + md + " messages from this channel!");
				} catch (InterruptedException | ExecutionException e1) {
					e1.printStackTrace();
				}
			}
		}
		MessageCreate.sendBack(embed);
	}
}
