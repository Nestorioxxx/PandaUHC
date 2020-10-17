package me.nestorioxxx.listeners;

import com.wimbli.WorldBorder.Events.WorldBorderFillFinishedEvent;
import com.wimbli.WorldBorder.Events.WorldBorderFillStartEvent;
import me.nestorioxxx.PandaUHC;
import me.nestorioxxx.game.UHCGame;
import me.nestorioxxx.world.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class WorldListener implements Listener {
    @EventHandler
    public void onFill(WorldBorderFillStartEvent e) {
        WorldManager.getInstance().setFillTask(e.getFillTask());
    }

    @EventHandler
    public void onFillEnd(WorldBorderFillFinishedEvent e) {
        if(e.getWorld().getName().equalsIgnoreCase("uhc_world")) {
            Bukkit.getScheduler().runTaskLater(PandaUHC.getInstance(), () -> WorldManager.getInstance().createNether(), 20);
        } else if(e.getWorld().getName().equalsIgnoreCase("uhc_nether")) {
            WorldManager.getInstance().setCurrentlyGenerating(false);
            WorldManager.getInstance().setGenned(true);
            UHCGame.getInstance().currentState = UHCGame.gameState.WHITELISTED;
        }
    }
}
