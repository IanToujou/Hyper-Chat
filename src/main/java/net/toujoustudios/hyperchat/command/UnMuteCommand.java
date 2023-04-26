package net.toujoustudios.hyperchat.command;

import net.toujoustudios.hyperchat.config.Config;
import net.toujoustudios.hyperchat.data.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class UnMuteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if(!(commandSender instanceof Player player)) return false;

        PlayerManager playerManager = PlayerManager.getPlayer(player);

        if(!player.hasPermission("hyperchat.command.unmute")) {
            player.sendMessage(Config.MESSAGE_ERROR_NOPERMISSION);
            return false;
        }

        if(args.length != 1) {
            player.sendMessage(Config.MESSAGE_ERROR_SYNTAX.replace("{Usage}", this.getUsage()));
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null) {
            player.sendMessage(Config.MESSAGE_ERROR_INVALIDPLAYER);
            return false;
        }

        PlayerManager targetManager = PlayerManager.getPlayer(target);
        targetManager.unmute();
        player.sendMessage(Config.MESSAGE_NOTIFICATION_UNMUTE_SENDER.replace("{Player}", target.getName()));
        target.sendMessage(Config.MESSAGE_NOTIFICATION_UNMUTE_TARGET);

        return false;

    }

    public String getUsage() {
        return "/unmute <player>";
    }

}
