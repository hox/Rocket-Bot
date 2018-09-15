package com.rocketbot.listeners;

import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;

import com.rocketbot.main.Main;

public class MemberJoin implements ServerMemberJoinListener {

	public void onServerMemberJoin(ServerMemberJoinEvent e) {
		if(e.getServer().getId() != Main.RB_ID) return;
		e.getUser().addRole(e.getServer().getRoleById(488535542544334858l).get());
	}
}
