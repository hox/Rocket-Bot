package com.rocketbot.commands;

import java.awt.Color;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import com.rocketbot.listeners.MessageCreate;

public class Command_Vote {

	public Command_Vote(MessageCreateEvent e, Message m, String mc, String[] args, EmbedBuilder embed) {
		if(args.length <= 5) {
			embed.setTitle("Error");
			embed.setDescription("You must provide a vote to start! [*vote #general(channel) 2(options) 30(minutes) Do you want eggs or bacon?(message)]");
			embed.setColor(Color.red);
		} else {
			String message = "";
			String stringchannel = args[1];
			TextChannel channel;
			for (int i = 4; i < args.length; i++) {
				message += args[i] + " ";
			}
			try {
				channel = m.getServer().get().getChannelById(stringchannel.replace("<#", "").replace(">", "")).get()
						.asTextChannel().get();
				if (!(m.getAuthor().canMentionEveryoneInTextChannel()
						|| channel.canWrite(m.getAuthor().asUser().get()))) {
					embed.setTitle("Error");
					embed.setDescription("You do not have permissions to execute this command!");
					embed.setColor(Color.red);
					return;
				}
				embed.setTitle("Vote");
				embed.setDescription(message);
				embed.setFooter("Vote Started by - " + m.getAuthor().getDiscriminatedName());
				channel.sendMessage(embed);
				embed.setTitle("Vote");
				embed.setDescription("Your vote has been sent successfully!");
				embed.setFooter("Rocket | Command Issued");
				MessageCreate.sendBack(embed);
				return;
			} catch (Exception e2) {
				embed.setTitle("Error");
				embed.setDescription("The channel you provided was invalid. Please try again.");
				embed.setColor(Color.red);
			}
		}
	}
}
