package me.nestorioxxx.game;

import lombok.Getter;
import me.nestorioxxx.PandaUHC;
import me.nestorioxxx.player.UHCPlayer;
import me.nestorioxxx.runnables.GameRunnable;
import me.nestorioxxx.utils.SitUtil;
import me.nestorioxxx.world.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;

public class UHCGame {


    @Getter private static UHCGame instance;
    public enum gameState { WHITELISTED, GENERATING, UNWHITELISTED, SCATTERING, PLAYING, ENDING}

    public gameState currentState;
    public int gameTime = 0;

    public ArrayList<UHCPlayer> gamePlayers = new ArrayList<>();
    public int playerCount;
    public GameRunnable runnable;
    public int currentBorder = 1500;
    public Player winner;


    public UHCGame() {
        instance = this;
        currentState = gameState.WHITELISTED;
    }

    public void start() {
        if (currentState == gameState.PLAYING || currentState == gameState.ENDING || currentState == gameState.GENERATING) return;
        if (!WorldManager.getInstance().genned) {
            PandaUHC.getInstance().getServer().getConsoleSender().sendMessage(ChatColor.RED + "World not generated!");
            return;
        } else {
            currentState = gameState.SCATTERING;
            for (UHCPlayer player : UHCPlayer.getUhcPlayers()) {
                player.currentState = UHCPlayer.PlayerState.PLAYING;

                gamePlayers.add(player);
                Location loc = new Location(Bukkit.getWorld("uhc_world"), new Random().nextInt(1500), 1, new Random().nextInt(1500));
                loc.setY(Bukkit.getWorld("uhc_world").getHighestBlockYAt(loc));

                loc.getChunk().load();

                player.playerObject.teleport(loc);

                SitUtil.freezePlayer(player.playerObject);
            }
            // Waiting for tps to stabilize.....
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (UHCPlayer player : gamePlayers) {
                        SitUtil.unFreezePlayer(player.playerObject);
                        player.startGame();
                        currentState = gameState.PLAYING;
                    }

                    runnable = new GameRunnable();
                    runnable.runTaskTimerAsynchronously(PandaUHC.getInstance(), 0, 20);
                    playerCount = gamePlayers.size();

                    for (UHCPlayer player : gamePlayers) {
                        player.currentState = UHCPlayer.PlayerState.PLAYING;
                        player.currentKills = 0;
                    }

                }
            }.runTaskLater(PandaUHC.getInstance(), 20 * 15);

        }
    }

    public void endGame() {
        for (UHCPlayer player : gamePlayers) {
            winner = player.playerObject;
            currentState = gameState.ENDING;
            // Do celebration then restart after 30 seconds
        }
    }

}
