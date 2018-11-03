package com.rocketbot.commands;

import com.rocketbot.listeners.MessageCreate;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Command_Ban {

	public Command_Ban(MessageCreateEvent e, Message m, String mc, String[] args, EmbedBuilder embed, JSONObject json) {
		if (args.length <= 1) {
			embed.setTitle("Error");
			embed.setDescription("You must provide a user to ban! [*ban @Ceaser#0001 Being a human]");
			embed.setColor(Color.red);
		} else {
			try {
				User user = null;
				Server server = m.getServer().get();
				String ncheckid = args[1].replace("<@", "").replace(">", "");
				List<User> users = new ArrayList<>(m.getServer().get().getMembers());
				for (User userm : users) {
					String checkid = userm.getIdAsString();
					if (ncheckid.equals(checkid)) {
						user = userm;
					}
				}
				if (!m.getAuthor().canBanUserFromServer(user)) {
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
					try {
						user.sendMessage("You have been banned from __" + server.getName() + "__ for **" + reason + "**!");
					} catch(NullPointerException eh) {
					}
					server.banUser(user);
					embed.setTitle("Success!");
					embed.setDescription("You have banned, " + user.getDiscriminatedName() + " from __"
							+ server.getName() + "__ for **" + reason + "**!");
				} else {
					user.sendMessage("You have been banned from __" + server.getName() + "__!");
					server.banUser(user);
					embed.setTitle("Success!");
					embed.setDescription(
							"You have banned, " + user.getDiscriminatedName() + " from __" + server.getName() + "__!");
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
