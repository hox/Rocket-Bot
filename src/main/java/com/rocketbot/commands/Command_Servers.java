package com.rocketbot.commands;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import com.rocketbot.listeners.MessageCreate;
import com.rocketbot.main.Main;
import com.vdurmont.emoji.EmojiParser;

public class Command_Servers {

	public Command_Servers(MessageCreateEvent e, Message m, String mc, String args[], EmbedBuilder embed) {
		List<String> names = Main.api.getServers().stream().map(Server::getName).collect(Collectors.toList());
		List<Integer> counts = Main.api.getServers().stream().map(Server::getMemberCount).collect(Collectors.toList());
		HashMap<Long, String> ids = new HashMap<Long, String>();
		List<Server> servers = Main.api.getServers().stream().collect(Collectors.toList());
		for (Server server : servers) {
			List<User> members = server.getMembers().stream().collect(Collectors.toList());
			for (User user : members) {
				if (ids.containsKey(user.getId())) {
					continue;
				} else {
					ids.put(user.getId(), user.getName());
				}
			}
		}
		String str = "";
		str += "```";
		str += "-------------------------------------------------------- \n";
		for (int i = 0; i < names.size(); i++) {
			String members = "Members:" + counts.get(i);
			int cc = 43 - EmojiParser.removeAllEmojis(names.get(i)).length()/* + serverinv.get(i).length()*/;
			String space = "";
			for (int i2 = 0; i2 < cc; i2++) {
				space += " ";
			}
			str += "| " + EmojiParser.removeAllEmojis(names.get(i)) + space/* + serverinv.get(i)*/ + "  " + members + "\n";
		}
		str += "--------------------------------------------------------\n";
		str += "Total Servers: " + names.size() + "\n";
		str += "Total Individual Members: " + ids.size() + "\n";
		str += "--------------------------------------------------------";
		str += "```";
		MessageCreate.sendBack(str);
	}
}
