package com.rocketbot.listeners;

import com.rocketbot.main.Main;
import org.javacord.api.event.channel.server.voice.ServerVoiceChannelMemberLeaveEvent;
import org.javacord.api.listener.channel.server.voice.ServerVoiceChannelMemberLeaveListener;

public class ServerVoiceChannelMemberLeave implements ServerVoiceChannelMemberLeaveListener {

	public void onServerVoiceChannelMemberLeave(ServerVoiceChannelMemberLeaveEvent e) {
		if(e.getUser().getId() == 223217915673968641l) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			e.getChannel().getServer().moveYourself(null);
			Main.voicechannel = null;
		}
	}
}
