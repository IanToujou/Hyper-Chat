package net.toujoustudios.hyperchat.command;

import net.toujoustudios.hyperchat.config.Config;
import net.toujoustudios.hyperchat.log.LogLevel;
import net.toujoustudios.hyperchat.log.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if(!(commandSender instanceof Player player)) {
            Logger.log(LogLevel.ERROR, "You cannot use this command in the console.");
            return false;
        }

        if(player.hasPermission("hyperchat.command.message")) {
            player.sendMessage(Config.MESSAGE_ERROR_NOPERMISSION);
            return false;
        }

        if(args.length == 0) {
            player.sendMessage(Config.MESSAGE_ERROR_SYNTAX.replace("{Usage}", this.getUsage()));
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null) {
            player.sendMessage(Config.MESSAGE_ERROR_INVALIDPLAYER.replace("{Prefix}", Config.MESSAGE_PREFIX));
            return false;
        }

        StringBuilder messageBuilder = new StringBuilder();
        for(int i = 1; i < args.length; i++) {
            messageBuilder.append(args[i]);
            if(i != (args.length - 1)) messageBuilder.append(" ");
        }

        String message = messageBuilder.toString();

        if(Config.CHAT_COLOR_ENABLED) {
            if(Config.CHAT_COLOR_USEPERMISSION) {
                if(player.hasPermission("hyperchat.chat.color")) {
                    message = message.replace(Config.CHAT_COLOR_PREFIX, "§");
                }
            } else {
                message = message.replace(Config.CHAT_COLOR_PREFIX, "§");
            }
        }

        if(Config.CHAT_EMOJI_ENABLED) {
            for(String emoji : Config.CHAT_EMOJI_LIST) {
                String[] strings = emoji.split("/");
                message = message.replaceAll("(?i):" + strings[0] + ":", strings[1] + Config.CHAT_PRIVATE_COLOR);
            }
        }

        player.sendMessage(Config.CHAT_PRIVATE_FORMAT_SENDER.replace("{Player}", target.getName()).replace("{Message}", message).replace("{PrivateColor}", Config.CHAT_PRIVATE_COLOR));
        target.sendMessage(Config.CHAT_PRIVATE_FORMAT_TARGET.replace("{Player}", player.getName()).replace("{Message}", message).replace("{PrivateColor}", Config.CHAT_PRIVATE_COLOR));

        if(Config.CHAT_PRIVATE_SOUND_ENABLED) target.playSound(target.getLocation(), Config.CHAT_PRIVATE_SOUND_TYPE, Config.CHAT_PRIVATE_SOUND_CATEGORY, 100, Config.CHAT_PRIVATE_SOUND_PITCH);

        return false;

    }

    public String getUsage() {
        return "/message <player> <message>";
    }

}
