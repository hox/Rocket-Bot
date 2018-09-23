package com.rocketbot.commands;

import java.util.ArrayList;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import com.rocketbot.listeners.MessageCreate;

public class Command_Help {

	ArrayList<ArrayList<String>> commands;
	ArrayList<String> command;
	EmbedBuilder embed;

	public Command_Help(MessageCreateEvent e, Message m, String mc, String args[], EmbedBuilder embed) {
		commands = new ArrayList<ArrayList<String>>();
		command = new ArrayList<String>();
		this.embed = embed;
		// INIT Complete
		add("**Help Command**", "Brings up this dialouge with commands to help you! [help, commands]");
		add("**Uptime Command**", "Shows you the current uptime of the bot! [uptime, time]");
		add("**Ping Command**", "Shows you the current ping to discord services! [ping, ms, delay]");
		add("**Members Command**", "Shows you the current membercount of the server! [members, membercount, count]");
		add("**Announce Command**", "Announces a message in the specified channel! [a, announce]");
		add("**Kick Command**", "Kicks the specified member from your discord server! [kick]");
		add("**Ban Command**", "Bans the specified member from your discord server! [ban]");
		add("**Purge Command**", "Purges the specified amount of messages! [purge]");
		add("**SetPrefix Command**", "Sets the prefix of the server! [setprefix]");
		add("**Prefix Command**", "Gets the current prefix of the server! [prefix]");
		// FINISH & COMPILE
		complete();
	}

	public void add(String title, String description) {
		command = new ArrayList<String>();
		command.add(title);
		command.add(description);
		commands.add(command);
		command = new ArrayList<String>();
	}

	public void complete() {
		for (int i = 0; i < commands.size(); i++) {
			embed.addField(commands.get(i).get(0), commands.get(i).get(1));
		}
		MessageCreate.sendBack(embed);
	}
}
