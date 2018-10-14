package com.rocketbot.commands;

import com.rocketbot.listeners.MessageCreate;
import com.rocketbot.main.Main;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Command_Vote {

    public Command_Vote(MessageCreateEvent e, Message m, String mc, String[] args, EmbedBuilder embed, String[] rgs, JSONObject json) {
        if (args.length <= 3) {
            embed.setTitle("Error");
            embed.setDescription("You must provide a vote to start! [vote #general(channel) 2(options) 30(minutes) Do you want eggs or bacon?(message)]");
            embed.setColor(Color.red);
            MessageCreate.sendBack(embed);
        } else {
            String message = "";
            String stringchannel = args[1];
            TextChannel channel;
            for (int i = 3; i < args.length; i++) {
                message += rgs[i] + " ";
            }
            int minutes;
            try {
                minutes = Integer.parseInt(args[2]);
            } catch (Exception ex) {
                embed.setTitle("Error");
                embed.setDescription("You must provide a valid integer for minutes alotted! [*vote #general(channel) 30(minutes) Do you want eggs or bacon?(message)]");
                embed.setColor(Color.red);
                MessageCreate.sendBack(embed);
                return;
            }
            try {
                channel = m.getServer().get().getChannelById(stringchannel.replace("<#", "").replace(">", "")).get()
                        .asTextChannel().get();
                if (!(m.getAuthor().canMentionEveryoneInTextChannel()
                        || channel.canWrite(m.getAuthor().asUser().get()))) {
                    embed.setTitle("Error");
                    embed.setDescription("You do not have permissions to execute this command!");
                    embed.setColor(Color.red);
                    return;
                }
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy h:mm");
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.MINUTE, minutes);
                int AM_PM = cal.get(Calendar.AM_PM);
                boolean am = AM_PM == Calendar.AM;
                String hfa = "`" + dateFormat.format(cal.getTime()) + " " + (!am ? "pm" : "am") + "`, **" + new PrettyTime().format(cal.getTime()) + "**";
                Date dateforconfig = cal.getTime();
                if(message.contains("\\\\")) {
                    embed.setTitle("Error");
                    embed.setDescription("You may not have `\"` in your poll!");
                    embed.setColor(Color.red);
                    MessageCreate.sendBack(embed);
                    return;
                }
                embed.setTitle("Vote");
                embed.setDescription(message);
                embed.addField("Completion Date:", hfa, true);
                embed.setFooter("Vote Started by - " + m.getAuthor().getDiscriminatedName());
                channel.sendMessage(embed);
                EmbedBuilder embed2 = new EmbedBuilder();
                embed2.setTitle("Vote");
                embed2.setDescription("Your vote has been sent successfully!");
                embed2.setFooter("Rocket | Command Issued");
                MessageCreate.sendBack(embed2);
                Long messageid = 1L;
                List<Message> messages = new ArrayList<>(channel.asTextChannel().get().getMessages(1).get());
                for(Message messagetocheck : messages) {
                    if(messagetocheck.getAuthor().asUser().get().equals(Main.api.getYourself())) {
                        messageid = messagetocheck.getId();
                        messagetocheck.addReaction("\uD83D\uDC4D");
                        messagetocheck.addReaction("\uD83D\uDC4E");
                    }
                }
                JSONObject data = json;
                JSONObject vote = new JSONObject();
                JSONArray votes = (JSONArray)data.get("votes");
                vote.put("enddate", dateforconfig.toString());
                vote.put("serverid", e.getServer().get().getIdAsString());
                vote.put("channelid", e.getChannel().getIdAsString());
                vote.put("message", message);
                vote.put("senderid", m.getAuthor().getIdAsString());
                vote.put("messageid", messageid.toString());
                vote.put("votes", 0);
                votes.add(vote);
                Main.setConfig(data);
                return;
            } catch (Exception e2) {
                embed.setTitle("Error");
                embed.setDescription("The channel you provided was invalid. Please try again.");
                embed.setColor(Color.red);
            }
        }
    }
}
