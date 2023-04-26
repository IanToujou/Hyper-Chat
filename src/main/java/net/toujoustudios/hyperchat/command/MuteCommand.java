package net.toujoustudios.hyperchat.command;

import net.toujoustudios.hyperchat.config.Config;
import net.toujoustudios.hyperchat.data.PlayerManager;
import net.toujoustudios.hyperchat.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if(!(commandSender instanceof Player player)) return false;

        PlayerManager playerManager = PlayerManager.getPlayer(player);

        if(!player.hasPermission("hyperchat.command.mute")) {
            player.sendMessage(Config.MESSAGE_ERROR_NOPERMISSION);
            return false;
        }

        if(args.length == 0) {
            player.sendMessage(Config.MESSAGE_ERROR_SYNTAX.replace("{Usage}", this.getUsage()));
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null) {
            player.sendMessage(Config.MESSAGE_ERROR_INVALIDPLAYER);
            return false;
        }

        StringBuilder reasonBuilder = new StringBuilder();
        if(args.length > 1) {
            for(int i = 1; i < args.length; i++) {
                reasonBuilder.append(args[i]);
                if(i != (args.length - 1)) reasonBuilder.append(" ");
            }
        } else {
            reasonBuilder.append("None ");
        }

        String reason = reasonBuilder.toString();
        reason = StringUtil.dropLastChar(reason);

        PlayerManager targetManager = PlayerManager.getPlayer(target.getUniqueId());

        targetManager.mute(reason);
        player.sendMessage(Config.MESSAGE_NOTIFICATION_MUTE_SENDER.replace("{Player}", target.getName()).replace("{Reason}", reasonBuilder.toString()));
        target.sendMessage(Config.MESSAGE_NOTIFICATION_MUTE_TARGET.replace("{Reason}", reason));

        return false;

    }

    public String getUsage() {
        return "/mute <player> [<reason>]";
    }

}
