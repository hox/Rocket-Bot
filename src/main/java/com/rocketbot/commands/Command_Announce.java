package com.rocketbot.commands;

import com.rocketbot.listeners.MessageCreate;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.json.simple.JSONObject;

import java.awt.*;

public class Command_Announce {

	public Command_Announce(MessageCreateEvent e, Message m, String mc, String[] args2, EmbedBuilder embed,
							String[] args, JSONObject json) {
		if (args.length < 3) {
			embed.setTitle("Error");
			embed.setDescription(
					"You must provide which channel to announce it in and a message to announce! [*announce #general true/false(@everyone) Announcement]");
			embed.setColor(Color.red);
		} else {
			String message = "";
			String stringchannel = args[1];
			TextChannel channel;
			boolean atEveryone;
			try{
				atEveryone = Boolean.parseBoolean(args[2]);
			} catch(Exception e2) {
				embed.setTitle("Error");
				embed.setDescription("You must provide a valid true or false value for @everyone to be sent!");
				embed.setColor(Color.red);
				MessageCreate.sendBack(embed);
				return;
			}
			for (int i = 3; i < args.length; i++) {
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
				if(message.contains("\\\\")) {
					embed.setTitle("Error");
					embed.setDescription("You may not have `\"` in your poll!");
					embed.setColor(Color.red);
					MessageCreate.sendBack(embed);
					return;
				}
				if(atEveryone) channel.sendMessage("@everyone");
				embed.setTitle("Announcement");
				embed.setDescription(message);
				embed.setFooter("Announcement sent by - " + m.getAuthor().getDiscriminatedName());
				channel.sendMessage(embed);
				embed.setTitle("Announcement");
				embed.setDescription("Your announcement has been sent successfully!");
				embed.setFooter("Rocket | Command Issued");
				MessageCreate.sendBack(embed);
				return;
			} catch (Exception e2) {
				embed.setTitle("Error");
				embed.setDescription("The channel you provided was invalid. Please try again.");
				embed.setColor(Color.red);
			}
		}
		MessageCreate.sendBack(embed);
	}
}
