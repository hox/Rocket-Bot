package com.rocketbot.suggestionsbot;

import java.awt.Color;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

	public static DiscordApi api;

	public static void bot(String token) {
		api = new DiscordApiBuilder().setToken(token).login().join();
		api.addMessageCreateListener(new MessageCreate());
	}

	@SuppressWarnings("unchecked")
	static class MessageCreate implements MessageCreateListener {

		@Override
		public void onMessageCreate(MessageCreateEvent e) {
			Message message = e.getMessage();
			String messageContent = message.getContent().toLowerCase().toString();
			if (!messageContent.startsWith(com.rocketbot.main.Main.defaultprefix) && !message.isPrivate())
				return;
			String[] args2 = messageContent.replace(com.rocketbot.main.Main.defaultprefix, "").split(" ");
			if(!(message.getChannel().getId() == 488369250872983553l)) return;
			if (args2[0].startsWith("suggest")) {
				String path = (com.rocketbot.main.Main.devmode ? "f:\\\\config.json" : "./config/config.json");
				try {
					String[] args = message.getContent().replace(com.rocketbot.main.Main.defaultprefix, "").split(" ");
					String suggestion = "";
					for (int i = 1; i < args.length; i++) {
						suggestion += args[i] + " ";
					}
					JSONParser parser = new JSONParser();
					JSONObject data = (JSONObject) parser.parse(new FileReader(path));
					JSONArray suggestions = (JSONArray) data.get("Suggestions");
					suggestions.add(suggestion);
					data.put("Suggestions", suggestions);
					FileWriter file = new FileWriter(path);
					file.write(data.toJSONString());
					file.flush();
					file.close();
					EmbedBuilder embed = new EmbedBuilder();
					embed.setTitle("Suggestion");
					embed.setDescription("Your suggestion, `" + suggestion + "` has been sent to staff!");
					embed.setColor(Color.decode("#2f3237")).setFooter("Rocket | Command Issued");
					message.getChannel().sendMessage(embed);
				} catch (IOException | ParseException e2) {
					e2.printStackTrace();
				}
			}
		}
	}
}
