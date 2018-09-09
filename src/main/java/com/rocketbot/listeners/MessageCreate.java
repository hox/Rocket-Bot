package com.rocketbot.listeners;

import java.awt.Color;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import com.rocketbot.commands.Command_Help;
import com.rocketbot.commands.Command_MemberCount;
import com.rocketbot.commands.Command_Ping;
import com.rocketbot.commands.Command_Test;
import com.rocketbot.commands.Command_Uptime;
import com.rocketbot.main.Main;

public class MessageCreate implements MessageCreateListener {

	String args[];
	static TextChannel channel;

	public void onMessageCreate(MessageCreateEvent e) {

		// Check if message has a prefix next to it
		Message message = e.getMessage();
		String messageContent = message.getContent().toLowerCase().toString();
		MessageAuthor user = e.getMessage().getAuthor();
		if (!messageContent.startsWith(Main.prefix) && !message.isPrivate())
			return;
		// Pass Complete: Checked for Private Channel & Prefix check
		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(Color.cyan).setFooter("Discord Lists | Command Issued");
		channel = e.getChannel();
		this.args = messageContent.replace(Main.prefix, "").split(" ");
		// Checking if message is private (In private channel)

		if (e.getMessage().isPrivate()) {
			// Pass Complete: Checked for private channel

		} else {
			// Else Complete: Returned the message as a public channel

		}
		// Global commands { MUST BE AVAILABLE TO BOT & PUBLIC SERVER }

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

		// MemberCount Command
		if (c("membercount") || c("members") || c("count")) {
			new Command_MemberCount(e, message, messageContent, args, embed);
		}

		// Test Command
		if (c("test")) {
			try {
				if (user != Main.api.getApplicationInfo().get().getOwner())
					return;
				new Command_Test(e, message, messageContent, args, embed);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			} catch (ExecutionException e1) {
				e1.printStackTrace();
			}
		}

	}

	public static void sendBack(EmbedBuilder embed) {
		channel.sendMessage(embed);
	}

	public boolean c(String s) {
		return args[0].startsWith(s);
	}
}