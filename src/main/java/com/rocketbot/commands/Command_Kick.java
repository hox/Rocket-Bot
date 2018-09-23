package com.rocketbot.commands;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import com.rocketbot.listeners.MessageCreate;

public class Command_Kick {

	public Command_Kick(MessageCreateEvent e, Message m, String mc, String[] args, EmbedBuilder embed) {
		if (args.length <= 1) {
			embed.setTitle("Error");
			embed.setDescription("You must provide a user to kick! [*kick @Ceaser#0001 Being a human]");
			embed.setColor(Color.red);
		} else {
			try {
				User user = null;
				Server server = m.getServer().get();
				String ncheckid = args[1].replace("<@", "").replace(">", "");
				List<User> users = m.getServer().get().getMembers().stream().collect(Collectors.toList());
				for (User userm : users) {
					String checkid = userm.getIdAsString();
					if (ncheckid.equals(checkid)) {
						user = userm;
					}
				}
				if (!m.getAuthor().canKickUserFromServer(user)) {
					embed.setTitle("Error");
					embed.setDescription("You do not have permissions to execute this command!");
					embed.setColor(Color.red);
					MessageCreate.sendBack(embed);
					return;
				}
				if (args.length > 2) {
					String reason = "";
					for (int i = 3; i < args.length; i++) {
						reason += args[i] + " ";
					}
					user.sendMessage("You have been kicked from __" + server.getName() + "__ for **" + reason + "**!");
					server.kickUser(user);
					embed.setTitle("Success!");
					embed.setDescription("You have kicked, " + user.getDiscriminatedName() + " from __"
							+ server.getName() + "__ for **" + reason + "**!");
				} else {
					user.sendMessage("You have been kicked from __" + server.getName() + "__!");
					server.kickUser(user);
					embed.setTitle("Success!");
					embed.setDescription(
							"You have kicked, " + user.getDiscriminatedName() + " from __" + server.getName() + "__!");
				}
			} catch (Exception e1) {
				embed.setTitle("Error");
				embed.setDescription("The user you entered was invalid!");
				embed.setColor(Color.red);
				e1.printStackTrace();
			}
		}
		MessageCreate.sendBack(embed);
	}
}
