package net.toujoustudios.hyperchat.main;

import net.toujoustudios.hyperchat.command.MessageCommand;
import net.toujoustudios.hyperchat.command.MuteCommand;
import net.toujoustudios.hyperchat.command.UnMuteCommand;
import net.toujoustudios.hyperchat.data.PlayerManager;
import net.toujoustudios.hyperchat.event.ChatListener;
import net.toujoustudios.hyperchat.event.JoinListener;
import net.toujoustudios.hyperchat.event.QuitListener;
import net.toujoustudios.hyperchat.loader.Loader;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class HyperChat extends JavaPlugin {

    public static String PLUGIN_NAME = "HyperChat";
    public static String PLUGIN_VERSION = "1.0.1";

    private static HyperChat instance;
    private PluginManager pluginManager;

    @Override
    public void onEnable() {
        instance = this;
        pluginManager = Bukkit.getPluginManager();
        Loader.startLoading();
    }

    @Override
    public void onDisable() {
        PlayerManager.unloadAll();
    }

    @SuppressWarnings("all")
    public void registerCommands() {
        getCommand("message").setExecutor(new MessageCommand());
        getCommand("msg").setExecutor(new MessageCommand());
        getCommand("tell").setExecutor(new MessageCommand());
        getCommand("mute").setExecutor(new MuteCommand());
        getCommand("unmute").setExecutor(new UnMuteCommand());
    }

    public void registerEvents() {
        pluginManager.registerEvents(new JoinListener(), this);
        pluginManager.registerEvents(new QuitListener(), this);
        pluginManager.registerEvents(new ChatListener(), this);
    }

    public static HyperChat getInstance() {
        return instance;
    }

    public PluginManager getPluginManager() {
        return pluginManager;
    }

}
