package com.rocketbot.main;

import com.rocketbot.listeners.*;
import org.discordbots.api.client.DiscordBotListAPI;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {

    public static long RB_ID = 488361118394351636L;
    public static List<Long> admins;
    public static String defaultprefix = "*";
    public static DiscordApi api;
    public static Thread ut;
    public static Thread sgt;
    public static Thread votes;
    public static long Owner_ID = 223217915673968641L;
    public static String[] args;
    public static long B_ID = 473173191649394736L;
    public static String voicechannel;
    private static DiscordBotListAPI DBLapi;
    public static String ver_id = "1.2.0";
    public static boolean devmode = false;
    private static final String USER_AGENT = "Mozilla/5.0";
    public static long lastResume = System.currentTimeMillis();
    public static long lastReconnect = System.currentTimeMillis();
    public static long lastRestart = System.currentTimeMillis();
    public static User lastToUseKill = null;
    public static String path = (devmode ? "http://127.0.0.1:59438/config" : Auth.pathToConfig);

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
        startProcesses(args);
    }

    private static void startProcesses(String[] args) {
        Main.args = args;
        initRocketSuggestions();
        initRocket();
        initAdmins();
        votes = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(60000);
                    JSONObject data = getConfig();
                    JSONArray votes = (JSONArray) data.get("votes");
                    //noinspection ForLoopReplaceableByForEach
                    for (int i = 0; i < votes.size(); i++) {
                        JSONObject vote = (JSONObject) votes.get(i);
                        String timeuntilend = (String) vote.get("enddate");
                        String serverid = (String) vote.get("serverid");
                        String channelid = (String) vote.get("channelid");
                        String message = (String) vote.get("message");
                        String messageid = (String) vote.get("messageid");
                        String senderid = (String) vote.get("senderid");
                        //Time check
                        Calendar current = Calendar.getInstance();
                        Calendar then = Calendar.getInstance();
                        //noinspection deprecation
                        then.setTime(new Date(Date.parse(timeuntilend)));
                        boolean timeissame = false;
                        if ((current.get(Calendar.MINUTE) == then.get(Calendar.MINUTE)) && (current.get(Calendar.DATE) == then.get(Calendar.DATE)) && (current.get(Calendar.HOUR) == then.get(Calendar.HOUR))) {
                            timeissame = true;
                        }
                        if (timeissame) {
                            EmbedBuilder embed = new EmbedBuilder();
                            String answer;
                            List<Reaction> reactions = Main.api.getMessageById(Long.parseLong(messageid), Main.api.getChannelById(Long.parseLong(channelid)).get().asTextChannel().get()).get().getReactions();
                            int evotes = 0;
                            for (Reaction reaction : reactions) {
                                if (reaction.getEmoji().asUnicodeEmoji().get().equals("\uD83D\uDC4D")) {
                                    evotes += reaction.getCount();
                                } else if (reaction.getEmoji().asUnicodeEmoji().get().equals("\uD83D\uDC4E")) {
                                    evotes -= reaction.getCount();
                                }
                            }
                            if (evotes >= 1) answer = "\uD83D\uDC4D";
                            else if (evotes <= -1) answer = "\ud83d\udc4e";
                            else
                                answer = "TIE";
                            embed.setTitle("Vote");
                            embed.setDescription(message);
                            embed.addField("Final", "Vote Completed: " + (answer.equals("TIE") ? "TIE" : answer + " wins!"));
                            embed.setFooter("Vote Started by - " + api.getUserById(Long.parseLong(senderid)).get().getDiscriminatedName());
                            Main.api.getMessageById(Long.parseLong(messageid), Main.api.getChannelById(Long.parseLong(channelid)).get().asTextChannel().get()).get().edit(embed);
                        }
                        if (current.getTimeInMillis() > then.getTimeInMillis()) {
                            setConfig(data);
                            return;
                        }
                        run();
                    }
                } catch ( InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        votes.start();
        //noinspection InfiniteRecursion
        ut = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000 * 60 * 60 * 24);
                    System.out.println("Landing Rocket...");
                    Main.api.disconnect();
                    System.out.println("Rocket Landed!");
                    Thread.sleep(10000);
                    System.out.println("Launching Rocket...");
                    Main.initRocket();
                    System.out.println("Launch Success!");
                    run();
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

    @SuppressWarnings("unchecked")
    public static String getPrefix(String id, JSONObject config) {
        JSONObject data = config;
        try {
            return data.get(id).toString().replace("{\"prefix\":\"", "").replace("\"}", "");
        } catch (NullPointerException e) {
            JSONObject prefix = new JSONObject();
            prefix.put("prefix", "*");
            data.put(id, prefix);
            setConfig(data);
            return getPrefix(id, getConfig());
        }
    }

    @SuppressWarnings({"unused", "RedundantExplicitVariableType", "StringBufferMayBeStringBuilder"})
    private static String sendGET(String GetURL) {
        try {
            URL obj = new URL(GetURL);
            HttpURLConnection con;
            con = (HttpURLConnection) obj.openConnection();
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
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void sendPost(String postUrl, String json) throws Exception {
        URL url = new URL(postUrl);
        URLConnection con = url.openConnection();
        HttpURLConnection http = (HttpURLConnection)con;
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        byte[] out = json.getBytes(StandardCharsets.UTF_8);
        int length = out.length;
        http.setFixedLengthStreamingMode(length);
        http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        http.connect();
        try(OutputStream os = http.getOutputStream()) {
            os.write(out);
        }
    }

    public static JSONObject getConfig() {
        try {
            String raw = sendGET(path);
            String json = raw.substring(1, raw.length() - 1).replaceAll("\\\\", "");
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(json);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setConfig(JSONObject json) {
        try {
            sendPost(path, json.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}