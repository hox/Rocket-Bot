package com.rocketbot.commands;

import java.util.List;
import java.util.stream.Collectors;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;

import com.rocketbot.listeners.MessageCreate;
import com.rocketbot.main.Main;
import com.vdurmont.emoji.EmojiParser;

public class Command_Servers {

	public Command_Servers(MessageCreateEvent e, Message m, String mc, String args[], EmbedBuilder embed) {
		List<String> names = Main.api.getServers().stream().map(Server::getName).collect(Collectors.toList());
		List<Integer> counts = Main.api.getServers().stream().map(Server::getMemberCount).collect(Collectors.toList());
		String str = "";
		str += "```";
		str += "-------------------------------------------------------- \n";
		for (int i = 0; i < names.size(); i++) {

			String members = "Members:" + counts.get(i);
			int cc = 43 - EmojiParser.removeAllEmojis(names.get(i)).length();
			String space = "";
			for (int i2 = 0; i2 < cc; i2++) {
				space += " ";
			}
			str += "| " + EmojiParser.removeAllEmojis(names.get(i)) + space + members + "\n";
		}
		str += "--------------------------------------------------------";
		str += "```";
		MessageCreate.sendBack(str);
	}
}
