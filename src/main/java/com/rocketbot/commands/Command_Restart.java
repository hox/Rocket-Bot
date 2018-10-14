package com.rocketbot.commands;

import com.rocketbot.listeners.MessageCreate;
import com.rocketbot.main.Main;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.json.simple.JSONObject;

public class Command_Restart {

	public Command_Restart(MessageCreateEvent e, Message m, String mc, String[] args, EmbedBuilder embed, JSONObject json) {
		embed.setTitle("Restarting Rocket Bot");
		embed.setDescription("Rocket will now restart");
		MessageCreate.sendBack(embed);
		Main.api.disconnect();
		System.out.println("Bot disconnected from Discord API");
		Main.initRocket();
		System.out.println("Bot connected to Discord API");
		if(Main.voicechannel != null) {
			Main.api.getVoiceChannelById(Main.voicechannel).get().asServerChannel().get().getServer().moveYourself(Main.api.getVoiceChannelById(Main.voicechannel).get().asServerVoiceChannel().get());
		}
	}
}
