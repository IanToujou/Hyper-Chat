package net.toujoustudios.hyperchat.log;

import net.toujoustudios.hyperchat.main.HyperChat;
import org.bukkit.Bukkit;

public class Logger {

    public static void log(LogLevel level, String message) {
        Bukkit.getConsoleSender().sendMessage(level.getColor() + "[" + HyperChat.PLUGIN_NAME + " - " + level + "] " + message);
    }

}
