package com.rocketbot.commands;

import com.rocketbot.listeners.MessageCreate;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.json.simple.JSONObject;

import java.net.InetAddress;

public class Command_Ping {

	public Command_Ping(MessageCreateEvent e, Message m, String mc, String[] args, EmbedBuilder embed, JSONObject json) {
		embed.setTitle("Ping!");
		long ping1 = System.currentTimeMillis();
		try {
			InetAddress address = InetAddress.getByName("70.32.1.32");
			boolean reachable = address.isReachable(10000);
			if (!reachable) {
				embed.setDescription("I cannot find the discord api at this time!");
				MessageCreate.sendBack(embed);
				return;
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		long ping2 = System.currentTimeMillis();
		long ping = ping2 - ping1;
		embed.setDescription("Delay to discord services: **" + ping + "ms**!");
		MessageCreate.sendBack(embed);
	}
}
