package me.nestorioxxx.listeners;

import me.nestorioxxx.config.options.DeathLightning;
import me.nestorioxxx.game.UHCGame;
import me.nestorioxxx.player.UHCPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Iterator;

public class DeathListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1 , (short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(e.getEntity().getName());
        item.setItemMeta(meta);
        e.getDrops().add(item);

        if (DeathLightning.getInstance().getOption().equalsIgnoreCase("on")) {
            Bukkit.broadcastMessage("test");
            Bukkit.getWorld("uhc_world").strikeLightning(e.getEntity().getLocation());
        }

        if (UHCPlayer.getUHCPlayer(e.getEntity()).currentState == UHCPlayer.PlayerState.PLAYING) {
            Iterator<UHCPlayer> iter = UHCGame.getInstance().gamePlayers.iterator();
            while (iter.hasNext()) {
                Player p = iter.next().playerObject;
                if (p == e.getEntity()) {
                    iter.remove();
                }
            }
            if (e.getEntity().getKiller() != null) {
                if (e.getEntity().getKiller() instanceof Player) {
                    UHCPlayer.getUHCPlayer(e.getEntity().getKiller()).currentKills++;
                }
            }
            if (e.getEntity().hasPermission("uhc.spectate")) {
                e.getEntity().teleport(new Location(Bukkit.getWorld("uhc_world"), 0, 100, 0));
                e.getEntity().setGameMode(GameMode.SPECTATOR);
                UHCPlayer.getUHCPlayer(e.getEntity()).currentState = UHCPlayer.PlayerState.SPECTATING;
            } else {
                UHCPlayer.getUHCPlayer(e.getEntity()).reset();
                UHCPlayer.getUHCPlayer(e.getEntity()).currentState = UHCPlayer.PlayerState.LOBBY;
            }
        }
    }
}
