package com.rocketbot.listeners;

import java.awt.Color;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import com.rocketbot.commands.Command_Announce;
import com.rocketbot.commands.Command_Help;
import com.rocketbot.commands.Command_MemberCount;
import com.rocketbot.commands.Command_Ping;
import com.rocketbot.commands.Command_Restart;
import com.rocketbot.commands.Command_Servers;
import com.rocketbot.commands.Command_Uptime;
import com.rocketbot.configuration.JSONUtils;
import com.rocketbot.main.Main;

public class MessageCreate implements MessageCreateListener {

	String args[];
	static TextChannel channel;

	public void onMessageCreate(MessageCreateEvent e) {

		// Check if message has a prefix next to it
		Message message = e.getMessage();
		String messageContent = message.getContent().toLowerCase().toString();
		MessageAuthor user = e.getMessage().getAuthor();
		if (!messageContent.startsWith(Main.prefix) && !message.isPrivate()) {
			return;
		}
		// Pass Complete: Checked for Private Channel & Prefix check
		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(Color.cyan).setFooter("Rocket | Command Issued");
		channel = e.getChannel();
		this.args = messageContent.replace(Main.prefix, "").split(" ");

		// HELP Command
		if (c("help") || c("commands")) {
			new Command_Help(e, message, messageContent, args, embed);
		}

		// UPTIME Command
		if (c("uptime") || c("time")) {
			new Command_Uptime(e, message, messageContent, args, embed);
		}

		// PING Command
		if (c("ping") || c("ms") || c("delay")) {
			new Command_Ping(e, message, messageContent, args, embed);
		}

		// MEMBERCOUNT Command
		if (c("membercount") || c("members") || c("count")) {
			new Command_MemberCount(e, message, messageContent, args, embed);
		}

		// RESTART Command
		if (c("restart")) {
			if (user.getId() != Main.owner_id)
				return;
			new Command_Restart(e, message, messageContent, args, embed);
		}

		// SERVERS Command
		if (c("servers")) {
			if (user.getId() != Main.owner_id)
				return;
			new Command_Servers(e, message, messageContent, args, embed);
		}

		// ANNOUNCE Command 
		if(c("announce") || c("a")) {
			String rgs[] = message.getContent().toString().replace(Main.prefix, "").split(" ");;
			new Command_Announce(e, message, messageContent, args, embed, rgs);
		}
		
		// Testing commands
		if (c("test")) {
			if (user.getId() != Main.owner_id)
				return;
			if (args[1].equals("get")) {
				embed.setTitle("Get Req").setDescription(JSONUtils.getJSONObjectFromFile(args[2]).toString());
			}
			
			if (args[1].equals("put")) {
				embed.setTitle("Put Req").setDescription("" + JSONUtils.write(args[2], args[3]));
			}
			
			if (args[1].equals("delete")) {

			}
			sendBack(embed);
		}
	}

	public static void sendBack(EmbedBuilder embed) {
		channel.sendMessage(embed);
	}
	
	public static void sendBack(String message) {
		channel.sendMessage(message);
	}

	public boolean c(String s) {
		return args[0].startsWith(s);
	}
}