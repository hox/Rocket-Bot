package com.rocketbot.listeners;

import com.rocketbot.commands.*;
import com.rocketbot.main.Main;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.json.simple.JSONObject;

import java.awt.*;

public class MessageCreate implements MessageCreateListener {

	String args[];
	static TextChannel channel;

	public void onMessageCreate(MessageCreateEvent e) {
		if (e.getServer().get().getId() == 264445053596991498l)
			return;
		Message message = e.getMessage();
		String messageContent = message.getContent().toLowerCase();
		MessageAuthor user = e.getMessage().getAuthor();
		JSONObject json = Main.getConfig();
		String serverprefix = Main.getPrefix(e.getServer().get().getIdAsString(), json);
		boolean admin = false;
		for (int i = 0; i < Main.admins.size(); i++) {
			if (Main.admins.get(i) == user.getId())
				admin = true;
		}
		channel = e.getChannel();
		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(Color.decode("#2f3237")).setFooter("Rocket | Command Issued");
		this.args = messageContent.replace(serverprefix, "").split(" ");
		if(messageContent.equals("confirm") && admin) {
			Command_Kill.Command_Kill_Confirmation(e, message, messageContent, args, embed);
		}
		boolean atRocket = !messageContent.startsWith("<@" + Long.toString(Main.B_ID) + ">");
		if ((!messageContent.startsWith(serverprefix) && !message.isPrivate()) && atRocket)
			return;

		/*

										START OF COMMANDS

		*/

		// HELP Command
		if (c("help") || c("commands")) {
			new Command_Help(e, message, messageContent, args, embed, json);
		}

		// UPTIME Command
		if (c("uptime") || c("time")) {
			new Command_Uptime(e, message, messageContent, args, embed, json);
		}

		// PING Command
		if (c("ping") || c("ms") || c("delay")) {
			new Command_Ping(e, message, messageContent, args, embed, json);
		}

		// MEMBERCOUNT Command
		if (c("membercount") || c("members") || c("count")) {
			new Command_MemberCount(e, message, messageContent, args, embed, json);
		}

		// RESTART Command
		if (c("bot")) {
			if (!admin)
				return;
			if(args[1].equals("kill")) {
				new Command_Kill(e, message, messageContent, args, embed, json);
			}
			if(args[1].equals("restart")) {
				new Command_Restart(e, message, messageContent, args, embed, json);
			}
		}

		// ANNOUNCE Command
		if (c("announce") || c("a")) {
			String rgs[] = message.getContent().replace(serverprefix, "").split(" ");
			new Command_Announce(e, message, messageContent, args, embed, rgs, json);
		}

		// KICK Command
		if (c("kick")) {
			new Command_Kick(e, message, messageContent, args, embed, json);
		}

		// BAN Command
		if (c("ban")) {
			new Command_Ban(e, message, messageContent, args, embed, json);
		}

		// PURGE Command
		if (c("purge")) {
			new Command_Purge(e, message, messageContent, args, embed, json);
		}

		// SETPREFIX Command
		if (c("setprefix")) {
			new Command_SetPrefix(e, message, messageContent, args, embed, json);
		}

		// PREFIX Command
		if (c("prefix")) {
			new Command_Prefix(e, message, messageContent, args, embed, json);
		}

		// POLL Command
		if (c("poll") || c("vote")) {
			String rgs[] = message.getContent().replace(serverprefix, "").split(" ");
			new Command_Vote(e, message, messageContent, args, embed, rgs, json);
		}

		// BOT INFO Command
		if (c("bi") || c("botinfo")) {
			new Command_BotInfo(e, message, messageContent, args, embed, json);
		}

		// SERVER INFO Command
		if (c("si") || c("serverinfo")) {
			new Command_ServerInfo(e, message, messageContent, args, embed, json);
		}
	}

	public static void sendBack(EmbedBuilder embed) {
		channel.sendMessage(embed);
	}

	public static void sendBack(String message) {
		channel.sendMessage(message);
	}

	public boolean c(String s) {
		return args[0].equals(s);
	}
}