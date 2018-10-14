package com.rocketbot.commands;

import com.rocketbot.listeners.MessageCreate;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.json.simple.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Command_ServerInfo {

	@SuppressWarnings("deprecation")
	public Command_ServerInfo(MessageCreateEvent e, Message m, String mc, String[] args, EmbedBuilder embed, JSONObject json) {
		try {
			Server server = e.getServer().get();
			String roles = "";
			List<Role> role = server.getRoles();
			for (Role aRole : role) {
				roles += aRole.getMentionTag() + " ";
			}
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			Date dt = Date.from(server.getCreationTimestamp());
			String S = sdf.format(dt);
			Date cD = sdf.parse(S);
			String creationDate = cD.getMonth() + "/" + cD.getDate() + "/" + (cD.getYear()+1900);
			String hfa;
			PrettyTime p = new PrettyTime();
			hfa = p.format(dt);
			embed.setTitle("Server Info");
			embed.addField("Server Owner", server.getOwner().getDiscriminatedName(), true);
			embed.addField("Member Count", server.getMembers().size() + "", true);
			embed.addField("Creation Date", creationDate + ", " + hfa, true);
			embed.addField("ID", server.getIdAsString(), true);

			embed.addField("Roles", roles, true);
			MessageCreate.sendBack(embed);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	}
}
