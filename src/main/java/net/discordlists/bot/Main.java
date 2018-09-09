package net.discordlists.bot;

import java.sql.Connection;
import java.util.concurrent.TimeUnit;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;

import net.discordlists.listeners.MemberJoin;
import net.discordlists.listeners.MessageCreate;
import net.discordlists.listeners.ServerJoin;
import net.discordlists.listeners.ServerLeave;

public class Main {

	public static long DL_ID = 473271925351907328l;
	public static String prefix = "*";
	public static DiscordApi api;
	public static Thread thread;
	public static Connection conn;

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
		if(args[0] != null) {
			api = new DiscordApiBuilder().setToken(args[0]).login().join();
		} else {
			api = new DiscordApiBuilder().setToken(Auth.token).login().join();
		}
		System.out.println("Logged in!");
		update();
		api.addMessageCreateListener(new MessageCreate());
		api.addServerJoinListener(new ServerJoin());
		api.addServerLeaveListener(new ServerLeave());
		api.addServerMemberJoinListener(new MemberJoin());
		Thread thread = new Thread(new Runnable() {

			@Override
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

	public static void update() {
		api.updateActivity(ActivityType.WATCHING, api.getServers().size() + " guilds");
	}
}