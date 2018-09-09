package com.rocketbot.listeners;

import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;

import com.rocketbot.main.Main;

public class MemberJoin implements ServerMemberJoinListener {

	@Override
	public void onServerMemberJoin(ServerMemberJoinEvent e) {
		//Adding the auto user to DL
		if(e.getServer().getId() != Main.DL_ID) return;
		e.getServer().getRoleById(1l).get().addUser(e.getUser());
	}
}
