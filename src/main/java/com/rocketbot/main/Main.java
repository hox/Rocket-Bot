package com.rocketbot.main;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.discordbots.api.client.DiscordBotListAPI;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.rocketbot.listeners.MemberJoin;
import com.rocketbot.listeners.MemberLeave;
import com.rocketbot.listeners.MessageCreate;
import com.rocketbot.listeners.ServerJoin;
import com.rocketbot.listeners.ServerLeave;
import com.rocketbot.listeners.ServerVoiceChannelMemberJoin;
import com.rocketbot.listeners.ServerVoiceChannelMemberLeave;

public class Main {

	public static long RB_ID = 488361118394351636L;
	public static List<Long> admins;
	public static String defaultprefix = "*";
	public static DiscordApi api;
	private static Thread ut;
	private static Thread sgt;
	public static long Owner_ID = 223217915673968641L;
	public static String[] args;
	public static long B_ID = 473173191649394736L;
	private static DiscordBotListAPI DBLapi;
	public static String ver_id = "0.0.5";
	public static boolean devmode = true;
	private static final String USER_AGENT = "Mozilla/5.0";
	public static long lastResume = System.currentTimeMillis();
	public static long lastReconnect = System.currentTimeMillis();
	public static long lastRestart = System.currentTimeMillis();

	public static String format(long millis) {
		long days = TimeUnit.MILLISECONDS.toDays(millis);
		long hours = TimeUnit.MILLISECONDS.toHours(millis) % 24;
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60;
		String strMinute = minutes + " minute" + (minutes == 1 ? "" : "s");
		String strHours = hours + " hour" + (hours == 1 ? "" : "s");
		String strDays = days + " day" + (days == 1 ? "" : "s");
		if (days == 0 && hours == 0) {
			return strMinute;
		} else if (days == 0) {
			return strHours + ", " + strMinute;
		} else {
			return strDays + ", " + strHours;
		}
	}

	public static void main(String[] args) {
		Main.args = args;
		initRocketSuggestions();
		initRocket();
		fixBuggos();
		initAdmins();
		ut = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000 * 60 * 30);
					System.out.println("Landing Rocket...");
					Main.api.disconnect();
					System.out.println("Rocket Landed!");
					Thread.sleep(10000);
					System.out.println("Launching Rocket...");
					Main.initRocket();
					System.out.println("Launch Success!");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		});
		ut.start();
	}

	private static void initAdmins() {
		List<Long> users = api.getServerById(RB_ID).get().getRoleById(497170982084280330l).get().getUsers().stream()
				.map(User::getId).collect(Collectors.toList());
		admins = users;
	}

	private static void initRocketSuggestions() {
		sgt = new Thread(new Runnable() {
			@Override
			public void run() {
				com.rocketbot.suggestionsbot.Main.bot(Auth.suggestToken);
			}
		});
		sgt.start();
	}

	private static void fixBuggos() {
		/*
		 * for(Server server : api.getServers().stream().collect(Collectors.toList())) {
		 * ServerJoin.onServerJoin(server.getIdAsString(), "*"); }
		 */
	}

	private static void initDBL() {
		DBLapi = new DiscordBotListAPI.Builder().token(Auth.dbl_token).botId("473173191649394736").build();
	}

	public static void update() {
		api.updateActivity(ActivityType.LISTENING, "*help | " + ver_id + " | Rocket Bot");
		com.rocketbot.suggestionsbot.Main.api.updateActivity(ActivityType.LISTENING,
				"*suggest | " + ver_id + " | Rocket Suggestions Bot");
 		DBLapi.setStats(api.getServers().size());
	}

	public static void login(String[] args) {
		System.out.println("Launching Rocket...");
		api = new DiscordApiBuilder().setToken(Auth.token).login().join();
		System.out.println("Launch Success!");
		api.getChannelById(493452917194620959l).get().asServerTextChannel().get()
				.sendMessage(new EmbedBuilder().setTitle("Online!").setDescription("The bot has been turned on!")
						.setFooter("Rocket Bot | Online!").setColor(Color.GREEN));
	}

	public static void addListeners() {
		api.addMessageCreateListener(new MessageCreate());
		api.addServerMemberJoinListener(new MemberJoin());
		api.addServerMemberLeaveListener(new MemberLeave());
		api.addServerJoinListener(new ServerJoin());
		api.addServerLeaveListener(new ServerLeave());
		api.addServerVoiceChannelMemberJoinListener(new ServerVoiceChannelMemberJoin());
		api.addServerVoiceChannelMemberLeaveListener(new ServerVoiceChannelMemberLeave());
	}

	public static void initRocket() {
		login(args);
		initDBL();
		update();
		addListeners();
	}

	public static String getPrefix(String id) {
		try {
			JSONParser parser = new JSONParser();
			JSONObject data = (JSONObject) parser
					.parse(new FileReader((Main.devmode ? "f:\\\\config.json" : "./config/config.json")));

			return data.get(id).toString().replace("{\"prefix\":\"", "").replace("\"}", "");
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			return "ooga";
		}
	}

	@SuppressWarnings({"unused", "RedundantExplicitVariableType", "StringBufferMayBeStringBuilder"})
	private static String sendGET(String GetURL) throws IOException {
		URL obj = new URL(GetURL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return response.toString();
		} else {
			return null;
		}
	}
}