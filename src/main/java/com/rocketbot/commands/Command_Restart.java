package com.rocketbot.commands;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import com.rocketbot.listeners.MessageCreate;
import com.rocketbot.main.Main;

public class Command_Restart {

	public Command_Restart(MessageCreateEvent e, Message m, String mc, String args[], EmbedBuilder embed) {
		embed.setTitle("Restarting Rocket Bot");
		embed.setDescription("in 10 seconds...");
		MessageCreate.sendBack(embed);
		System.out.print("Restarting Rocket (OWNER COMMAND");
		try {
			Main.api.disconnect();
			System.out.println("Bot disconnected from Discord API");
			Thread.sleep(10000);
			Main.login(Main.args);
			System.out.println("Bot connected to Discord API");
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
}
