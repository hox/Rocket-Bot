package com.rocketbot.commands;

import com.rocketbot.listeners.MessageCreate;
import com.rocketbot.listeners.ServerJoin;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.json.simple.JSONObject;

import java.awt.*;

public class Command_SetPrefix {

    public Command_SetPrefix(MessageCreateEvent e, Message m, String mc, String[] args, EmbedBuilder embed, JSONObject json) {
        if (args.length < 2) {
            embed.setTitle("Error");
            embed.setDescription("You must provide a prefix to set for the server! [*setprefix PREFIX]");
            embed.setColor(Color.red);
        } else {
            if (m.getAuthor().canManageMessagesInTextChannel()) {
                String newprefix = args[1];
                if (newprefix.contains("\\\\")) {
                    embed.setTitle("Error");
                    embed.setDescription("You may not have `\"` in your new prefix!");
                    embed.setColor(Color.red);
                    MessageCreate.sendBack(embed);
                    return;
                }
                ServerJoin.onServerJoin(m.getServer().get().getIdAsString(), newprefix, json);
                embed.setTitle("Prefix Change");
                embed.setDescription("You have set the prefix of __" + m.getServer().get().getName() + "__ to `" + newprefix + "`");
            } else {
                embed.setTitle("Error");
                embed.setDescription("You do not have permissions to execute this command!");
                embed.setColor(Color.red);
            }
        }
        MessageCreate.sendBack(embed);
    }
}
