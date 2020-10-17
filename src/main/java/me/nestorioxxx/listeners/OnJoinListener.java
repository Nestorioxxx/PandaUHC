package me.nestorioxxx.listeners;

import me.nestorioxxx.player.UHCPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class OnJoinListener implements Listener {
    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        new UHCPlayer(e.getPlayer());
    }

}
