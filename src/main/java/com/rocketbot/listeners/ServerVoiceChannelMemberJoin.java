package com.rocketbot.listeners;

import com.rocketbot.main.Main;
import org.javacord.api.event.channel.server.voice.ServerVoiceChannelMemberJoinEvent;
import org.javacord.api.listener.channel.server.voice.ServerVoiceChannelMemberJoinListener;

public class ServerVoiceChannelMemberJoin implements ServerVoiceChannelMemberJoinListener {

	@Override
	public void onServerVoiceChannelMemberJoin(ServerVoiceChannelMemberJoinEvent e) {
		if(e.getUser().getId() == 223217915673968641l) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			e.getChannel().getServer().moveYourself(e.getChannel());
			Main.voicechannel = e.getChannel().getIdAsString();
		}
	}

}
