package com.rocketbot.listeners;

import java.awt.Color;
import java.util.stream.Collectors;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import com.rocketbot.commands.Command_Announce;
import com.rocketbot.commands.Command_Ban;
import com.rocketbot.commands.Command_Help;
import com.rocketbot.commands.Command_Kick;
import com.rocketbot.commands.Command_MemberCount;
import com.rocketbot.commands.Command_Ping;
import com.rocketbot.commands.Command_Prefix;
import com.rocketbot.commands.Command_Purge;
import com.rocketbot.commands.Command_Restart;
import com.rocketbot.commands.Command_Servers;
import com.rocketbot.commands.Command_SetPrefix;
import com.rocketbot.commands.Command_Uptime;
import com.rocketbot.commands.Command_Vote;
import com.rocketbot.main.Main;

public class MessageCreate implements MessageCreateListener {

	String args[];
	static TextChannel channel;

	public void onMessageCreate(MessageCreateEvent e) {
		Message message = e.getMessage();
		String messageContent = message.getContent().toLowerCase().toString();
		MessageAuthor user = e.getMessage().getAuthor();
		String serverprefix = Main.getPrefix(e.getServer().get().getIdAsString());
		if (!messageContent.startsWith(serverprefix) && !message.isPrivate())
			return;
		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(Color.decode("#2f3237")).setFooter("Rocket | Command Issued");
		channel = e.getChannel();
		this.args = messageContent.replace(serverprefix, "").split(" ");
		boolean admin = false;
		for (int i = 0; i < Main.admins.length; i++) {
			if (Main.admins[i] == user.getId())
				admin = true;
		}
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
			if (!admin)
				return;
			new Command_Restart(e, message, messageContent, args, embed);
		}

		// SERVERS Command
		if (c("servers")) {
			if (!admin)
				return;
			new Command_Servers(e, message, messageContent, args, embed);
		}

		// ANNOUNCE Command
		if (c("announce") || c("a")) {
			String rgs[] = message.getContent().toString().replace(serverprefix, "").split(" ");
			new Command_Announce(e, message, messageContent, args, embed, rgs);
		}

		// KICK Command
		if (c("kick")) {
			new Command_Kick(e, message, messageContent, args, embed);
		}

		// BAN Command
		if (c("ban")) {
			new Command_Ban(e, message, messageContent, args, embed);
		}

		// PURGE Command
		if (c("purge")) {
			new Command_Purge(e, message, messageContent, args, embed);
		}
		
		// SETPREFIX Command
		if(c("setprefix")) {
			new Command_SetPrefix(e, message, messageContent, args, embed);
		}
		
		// PREFIX Command
		if(c("prefix")) {
			new Command_Prefix(e, message, messageContent, args, embed);
		}
		
		// TESTING Commands
		if (c("test")) {
			if (!admin)
				return;
			if (args[1].equals("test"))
				ServerJoin.onServerJoin(args[2], "*");
			if(args[1].equals("put")) {
				ServerJoin.onServerJoin(args[2], args[3]);
			}
			if(args[1].equals("pushall")) {
				for(Server server : Main.api.getServers().stream().collect(Collectors.toList())) {
					ServerJoin.onServerJoin(server.getIdAsString(), "*");
				}
			}
		}
		
		// POLL Command
		if(c("poll") || c("vote")) {
			new Command_Vote(e, message, messageContent, args, embed);
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