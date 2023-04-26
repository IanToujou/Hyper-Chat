package net.toujoustudios.hyperchat.loader;

import net.toujoustudios.hyperchat.config.Config;
import net.toujoustudios.hyperchat.data.PlayerManager;
import net.toujoustudios.hyperchat.log.LogLevel;
import net.toujoustudios.hyperchat.log.Logger;
import net.toujoustudios.hyperchat.main.HyperChat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Loader {

    private static LoaderState state;
    private static boolean cancelled = false;

    public static void startLoading() {
        preInitialize();
        initialize();
        postInitialize();
    }

    public static void preInitialize() {

        if(cancelled) return;

        state = LoaderState.PRE_INIT;
        Config.initialize();

        if(cancelled) return;

        Logger.log(LogLevel.INFORMATION, "Pre initialization completed.");

    }

    public static void initialize() {
        if(cancelled) return;
        state = LoaderState.INIT;
        HyperChat.getInstance().registerEvents();
        HyperChat.getInstance().registerCommands();
        Logger.log(LogLevel.INFORMATION, "Initialization completed.");
    }

    public static void postInitialize() {

        if(cancelled) return;
        state = LoaderState.POST_INIT;

        for(Player player : Bukkit.getOnlinePlayers()) {
            if(player != null) {
                PlayerManager playerManager = PlayerManager.getPlayer(player);
                PlayerManager.getPlayers().put(player.getUniqueId(), playerManager);
            }
        }

        Logger.log(LogLevel.INFORMATION, "Post initialization completed.");
        state = LoaderState.END;

    }

    public static void cancel() {
        cancelled = true;
        HyperChat.getInstance().getServer().getPluginManager().disablePlugin(HyperChat.getInstance());
    }

    public static LoaderState getState() {
        return state;
    }

}
