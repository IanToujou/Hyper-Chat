package net.toujoustudios.hyperchat.event;

import net.toujoustudios.hyperchat.data.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerManager.getPlayer(player).unload();
    }

}
