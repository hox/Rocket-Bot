package net.discordlists.listeners;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.server.ServerLeaveEvent;
import org.javacord.api.listener.server.ServerLeaveListener;
import org.javacord.api.util.logging.ExceptionLogger;

import net.discordlists.bot.Main;
import net.discordlists.colors.ConsoleColor;

public class ServerLeave implements ServerLeaveListener {

	public void onServerLeave(ServerLeaveEvent e) {
		System.out.println(ConsoleColor.BLUE_BRIGHT + "Server Leave " + ConsoleColor.RED_BOLD_BRIGHT + "-> "
				+ ConsoleColor.WHITE + " " + e.getServer().getName());
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Server Leave");
		embed.setDescription("The bot has left the __**" + e.getServer().getName() + "**__ Discord Server!");
		embed.setColor(Color.GREEN);
		embed.setFooter("Discord Lists | Updates Channel");
		Main.api.getTextChannelById(476043401113108510l).get().sendMessage(embed).exceptionally(ExceptionLogger.get());
	}

	public static void test() {
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Server Leave");
		embed.setDescription("The bot has left the __**TEST SERVER**__ Discord Server!");
		embed.setColor(Color.GREEN);
		embed.setFooter("Discord Lists | Updates Channel");	
		Main.api.getTextChannelById(476043401113108510l).get().sendMessage(embed).exceptionally(ExceptionLogger.get());
	}
}
