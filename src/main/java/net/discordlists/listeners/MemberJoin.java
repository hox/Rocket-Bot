package net.discordlists.listeners;

import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;

import net.discordlists.bot.Main;

public class MemberJoin implements ServerMemberJoinListener {

	@Override
	public void onServerMemberJoin(ServerMemberJoinEvent e) {
		//Adding the auto user to DL
		if(e.getServer().getId() != Main.DL_ID) return;
		e.getServer().getRoleById(1l).get().addUser(e.getUser());
	}
}
