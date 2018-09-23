package com.rocketbot.listeners;

import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;
import org.javacord.api.util.logging.ExceptionLogger;

import com.rocketbot.main.Main;

public class MemberJoin implements ServerMemberJoinListener {

	public void onServerMemberJoin(ServerMemberJoinEvent e) {
		long id = 488394986321346560l;
		if(e.getServer().getId() != Main.RB_ID) return;
		e.getUser().addRole(e.getServer().getRoleById(id).get()).exceptionally(ExceptionLogger.get());
	}
}
