package com.rocketbot.listeners;

import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;

import com.rocketbot.main.Main;

public class MemberJoin implements ServerMemberJoinListener {

	public void onServerMemberJoin(ServerMemberJoinEvent e) {
		if(e.getServer().getId() != Main.RB_ID) return;
		e.getServer().getRoleById(488395211194630145l).get().addUser(e.getUser());
	}
}
