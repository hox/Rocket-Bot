package com.rocketbot.commands;

import com.rocketbot.listeners.MessageCreate;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.ocpsoft.prettytime.PrettyTime;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Command_Vote {

    public Command_Vote(MessageCreateEvent e, Message m, String mc, String[] args, EmbedBuilder embed) {
        if (args.length <= 4) {
            embed.setTitle("Error");
            embed.setDescription("You must provide a vote to start! [vote #general(channel) 2(options) 30(minutes) Do you want eggs or bacon?(message)]");
            embed.setColor(Color.red);
            MessageCreate.sendBack(embed);
        } else {
            String message = "";
            String stringchannel = args[1];
            TextChannel channel;
            for (int i = 4; i < args.length; i++) {
                message += args[i] + " ";
            }
            int options, minutes;
            try {
                options = Integer.parseInt(args[2]);
            } catch (Exception ex) {
                embed.setTitle("Error");
                embed.setDescription("You must provide a valid integer for options! [*vote #general(channel) 2(options) 30(minutes) Do you want eggs or bacon?(message)]");
                embed.setColor(Color.red);
                MessageCreate.sendBack(embed);
                return;
            }
            try {
                minutes = Integer.parseInt(args[3]);
            } catch (Exception ex) {
                embed.setTitle("Error");
                embed.setDescription("You must provide a valid integer for minutes alotted! [*vote #general(channel) 2(options) 30(minutes) Do you want eggs or bacon?(message)]");
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
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                DateFormat dateFormat = new SimpleDateFormat("h:mm");
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.MINUTE, minutes);
                int AM_PM = cal.get(Calendar.AM_PM);
                boolean am = AM_PM == Calendar.AM;
                String hfa = dateFormat.format(cal.getTime()) + " " + (!am ? "pm" : "am");
                embed.setTitle("Vote");
                embed.setDescription(message);
                embed.addField("Completion Date:", hfa, true);
                embed.setFooter("Vote Started by - " + m.getAuthor().getDiscriminatedName());
                channel.sendMessage(embed);
                embed.setTitle("Vote");
                embed.setDescription("Your vote has been sent successfully!");
                embed.setFooter("Rocket | Command Issued");
                MessageCreate.sendBack(embed);
                return;
            } catch (Exception e2) {
                embed.setTitle("Error");
                embed.setDescription("The channel you provided was invalid. Please try again.");
                embed.setColor(Color.red);
            }
        }
    }
}
