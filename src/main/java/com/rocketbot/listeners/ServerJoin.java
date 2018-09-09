package com.rocketbot.listeners;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.server.ServerJoinEvent;
import org.javacord.api.listener.server.ServerJoinListener;
import org.javacord.api.util.logging.ExceptionLogger;

import com.rocketbot.colors.ConsoleColor;
import com.rocketbot.main.Main;

public class ServerJoin implements ServerJoinListener {

	public void onServerJoin(ServerJoinEvent e) {
		System.out.println(ConsoleColor.BLUE_BRIGHT + "Server Join " + ConsoleColor.RED_BOLD_BRIGHT + "-> "
				+ ConsoleColor.WHITE + " " + e.getServer().getName());
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Server Join");
		embed.setDescription("The bot has joined the __**" + e.getServer().getName() + "**__ Discord Server!");
		embed.setColor(Color.GREEN);
		embed.setFooter("Discord Lists | Updates Channel");
		Main.api.getTextChannelById(476043401113108510l).get().sendMessage(embed).exceptionally(ExceptionLogger.get());
	}

	public static void test() {
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Server Join");
		embed.setDescription("The bot has joined the __**TEST SERVER**__ Discord Server!");
		embed.setColor(Color.GREEN);
		embed.setFooter("Discord Lists | Updates Channel");
		Main.api.getTextChannelById(476043401113108510l).get().sendMessage(embed).exceptionally(ExceptionLogger.get());
	}
}