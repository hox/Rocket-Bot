package com.rocketbot.commands;

import java.awt.Color;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import com.rocketbot.listeners.MessageCreate;

public class Command_Announce {

	public Command_Announce(MessageCreateEvent e, Message m, String mc, String args2[], EmbedBuilder embed, String args[]) {
		if(args.length < 3) {
			embed.setTitle("Error");
			embed.setDescription("You must provide which channel to announce it in and a message to announce! [*announce #general Announcement]");
			embed.setColor(Color.red);
			return;
		} else {
			String message = "";
			String stringchannel = args[1];
			TextChannel channel;
			for(int i = 2; i < args.length; i++) {
				message += args[i] + " ";
			}
			try {
				channel = m.getServer().get().getChannelById(stringchannel.replace("<#", "").replace(">", "")).get().asTextChannel().get();	
				if(!(m.getAuthor().canMentionEveryoneInTextChannel() || channel.canWrite(m.getAuthor().asUser().get()))) {
					embed.setTitle("Error");
					embed.setDescription("You do not have permission to send a message in the specified channel!");
					embed.setColor(Color.red);
					return;
				}
				embed.setTitle("Announcement");
				embed.setDescription(message);
				embed.setFooter("Announcement sent by - <@" + m.getAuthor().getIdAsString() + ">");
				channel.sendMessage(embed);
				embed.setTitle("Announcement");
				embed.setDescription("You announcement has been sent successfully!");
				embed.setFooter("Rocket | Command Issued");
				MessageCreate.sendBack(embed);
				return;
			} catch(Exception e2) {
				embed.setTitle("Error");
				embed.setDescription("The channel you provided was invalid. Please try again."); 
			}
		}
		MessageCreate.sendBack(embed);
	}
}
