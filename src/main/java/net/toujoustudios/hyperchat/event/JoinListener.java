package net.toujoustudios.hyperchat.event;

import net.toujoustudios.hyperchat.data.PlayerManager;
import net.toujoustudios.hyperchat.loader.Loader;
import net.toujoustudios.hyperchat.loader.LoaderState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        if(Loader.getState() != LoaderState.END) {
            player.kickPlayer("§cThe server is still loading. Please wait a moment.");
            return;
        }

        PlayerManager playerManager = PlayerManager.getPlayer(player);
        PlayerManager.getPlayers().put(player.getUniqueId(), playerManager);

    }

}
