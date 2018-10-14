package com.rocketbot.commands;

import com.rocketbot.listeners.MessageCreate;
import com.rocketbot.main.Main;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Command_Kill {

    public Command_Kill(MessageCreateEvent e, Message m, String mc, String[] args, EmbedBuilder embed, JSONObject json) {
        embed.setTitle("Bot Shutdown");
        embed.setDescription("You just started a shutdown sequence for Rocket, if you are sure you want to shutdown rocket please reply with `confirm`.");
        MessageCreate.sendBack(embed);
        Main.lastToUseKill = m.getUserAuthor().get();
    }

    public static void Command_Kill_Confirmation(MessageCreateEvent e, Message m, String mc, String[] args, EmbedBuilder embed) {
        try {
            ArrayList<Message> asr = new ArrayList<>(e.getChannel().getMessages(3).get());
            if(asr.get(0).getAuthor().asUser().get().equals(Main.lastToUseKill)) {
                embed.setTitle("Bot Shutdown");
                embed.setDescription("Killing bot...");
                MessageCreate.sendBack(embed);
                Main.ut.join();
                Main.sgt.join();
                Main.votes.join();
                Main.api.disconnect();
                return;
            }
        } catch (InterruptedException | ExecutionException e1) {
            e1.printStackTrace();
        }
    }
}
