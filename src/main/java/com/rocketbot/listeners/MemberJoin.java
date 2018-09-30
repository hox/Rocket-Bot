package com.rocketbot.listeners;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;
import org.javacord.api.util.logging.ExceptionLogger;

import com.rocketbot.main.Main;

public class MemberJoin implements ServerMemberJoinListener {

	public void onServerMemberJoin(ServerMemberJoinEvent e) {
		long id = 488394986321346560l;
		if(e.getServer().getId() != Main.RB_ID) return;
		e.getUser().addRole(e.getServer().getRoleById(id).get()).exceptionally(ExceptionLogger.get());
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("New Member");
		embed.setDescription(e.getUser().getDiscriminatedName() +", has joined us!");
		embed.setColor(Color.green);
		Main.api.getChannelById(488369206186737675l).get().asServerTextChannel().get().sendMessage(embed);
	}
}
