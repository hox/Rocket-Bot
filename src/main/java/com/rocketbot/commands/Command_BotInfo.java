package com.rocketbot.commands;

import com.rocketbot.listeners.MessageCreate;
import com.rocketbot.main.Main;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.json.simple.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Command_BotInfo {

	public Command_BotInfo(MessageCreateEvent e, Message m, String mc, String[] args, EmbedBuilder embed, JSONObject json) {
		try {
			String contributors = "";
			for(int i = 0; i < Main.admins.size(); i++) {
				Long admin = Main.admins.get(i);
				int num = (i+1);
				if(num == Main.admins.size()) {
					contributors += Main.api.getUserById(admin).get().getDiscriminatedName();
				} else {
					contributors += Main.api.getUserById(admin).get().getDiscriminatedName() + ", ";
				}
			}
			HashMap<Long, String> ids = new HashMap<>();
			List<Server> servers = new ArrayList<>(Main.api.getServers());
			for (Server server : servers) {
				List<User> members = new ArrayList<>(server.getMembers());
				for (User user : members) {
					if (!ids.containsKey(user.getId())) {
						ids.put(user.getId(), user.getName());
					}
				}
			}
			DecimalFormat df = new DecimalFormat("###,###,###");
			String msize = df.format(ids.size());
			String ssize = df.format(servers.size());
			embed.setTitle("Bot Info");
			embed.setDescription("A description of the bot!");
			embed.addField("Owner", Main.api.getUserById(223217915673968641l).get().getDiscriminatedName(), true);
			embed.addField("Support Discord", "[Join Here](https://discord.gg/ptUbfH5)", true);
			embed.addField("Servers", ssize, true);
			embed.addField("Members", msize, true);
			embed.addField("Vote Link","[Vote Here](https://discordbots.org/bot/473173191649394736/vote)", true);
			embed.addField("Bot Version", Main.ver_id, true);
			embed.addField("Contributors", contributors, true);
			embed.addField("Website Link", "[Click Here](https://rocketbot.xyz)");
			MessageCreate.sendBack(embed);
		} catch (InterruptedException | ExecutionException e1) {
			e1.printStackTrace();
		}
	}
}
