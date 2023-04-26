package net.toujoustudios.hyperchat.event;

import net.toujoustudios.hyperchat.config.Config;
import net.toujoustudios.hyperchat.data.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        PlayerManager playerManager = PlayerManager.getPlayer(player);

        if(playerManager.isMuted()) {
            event.setCancelled(true);
            player.sendMessage(Config.MESSAGE_ERROR_MUTED_CHAT.replace("{Reason}", playerManager.getMuteReason()));
            return;
        }

        if(!Config.CHAT_DEFAULT_ENABLED) {
            return;
        }

        String message = Config.CHAT_DEFAULT_FORMAT;

        message = message.replace("{Player}", player.getDisplayName());
        message = message.replace("{DefaultColor}", Config.CHAT_DEFAULT_COLOR);
        message = message.replace("{Message}", event.getMessage());
        message = message.replace(Config.CHAT_COLOR_PREFIX, "§");

        if(Config.CHAT_EMOJI_ENABLED) {

            for(String emoji : Config.CHAT_EMOJI_LIST) {

                String[] strings = emoji.split("/");
                message = message.replaceAll("(?i):" + strings[0] + ":", strings[1] + Config.CHAT_DEFAULT_COLOR);

            }

        }

        event.setFormat(message);

    }

}
