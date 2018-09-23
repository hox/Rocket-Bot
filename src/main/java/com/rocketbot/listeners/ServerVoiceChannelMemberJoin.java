package com.rocketbot.listeners;

import org.javacord.api.event.channel.server.voice.ServerVoiceChannelMemberJoinEvent;
import org.javacord.api.listener.channel.server.voice.ServerVoiceChannelMemberJoinListener;

public class ServerVoiceChannelMemberJoin implements ServerVoiceChannelMemberJoinListener {

	@Override
	public void onServerVoiceChannelMemberJoin(ServerVoiceChannelMemberJoinEvent e) {
		if(e.getUser().getId() == 223217915673968641l) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			e.getChannel().getServer().moveYourself(e.getChannel());
		}
	}

}
