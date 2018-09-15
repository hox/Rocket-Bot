package com.rocketbot.main;

import java.util.concurrent.TimeUnit;

import org.discordbots.api.client.DiscordBotListAPI;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;

import com.rocketbot.listeners.MemberJoin;
import com.rocketbot.listeners.MessageCreate;

public class Main {

	public static long RB_ID = 488361118394351636l;
	public static long owner_id = 223217915673968641l;
	public static String prefix = "*";
	public static DiscordApi api;
	public static Thread thread;
	public static String[] args;
	public static long B_ID = 473173191649394736l;
	public static DiscordBotListAPI DBLapi;
	
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
		login(args);
		initDBL();
		update();
		addListeners();
		/*JSONObject obj = JSONUtils.getJSONObjectFromFile("/config.json");  
		String[] names = JSONObject.getNames(obj);
		for(String string : names) {
			System.out.println(string);
		}   */                                 
		Thread thread = new Thread(new Runnable() {

			public void run() {
				while (true) {
					try {
						Thread.sleep(1000 * 60);
						update();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}

		});
		thread.start(); 
	}

	private static void initDBL() {
		DBLapi = new DiscordBotListAPI.Builder().token(Auth.dbl_token).botId("473173191649394736").build();
	}

	public static void update() {
		api.updateActivity(ActivityType.WATCHING, "over " + api.getServers().size() + " guilds");
		DBLapi.setStats(api.getServers().size());
		//System.out.println("Update");
	}

	public static void login(String[] args) {
		Main.args = args;
		try {
			System.out.println("Launching Rocket Bot...");
			Thread.sleep(1000);
			try {
				System.out.println("Attempting to use token from arguments...");
				api = new DiscordApiBuilder().setToken(args[0]).login().join();
			} catch(Exception e) {
				api = new DiscordApiBuilder().setToken(Auth.token).login().join();
				System.out.println("Using Auth class, Token not found in arguments");
			}
			Thread.sleep(500);
			System.out.println("Success, Logged in!");
		} catch (Exception e) {
			System.out.println("Launch failed!");
			e.printStackTrace();
		}
	}
	
	public static void addListeners() {
		api.addMessageCreateListener(new MessageCreate());
		api.addServerMemberJoinListener(new MemberJoin());
	}
}