package me.nestorioxxx.runnables;

import com.wimbli.WorldBorder.WorldBorder;
import me.nestorioxxx.PandaUHC;
import me.nestorioxxx.config.options.BorderShrink;
import me.nestorioxxx.config.options.FinalHeal;
import me.nestorioxxx.config.options.GoldenHead;
import me.nestorioxxx.config.options.PVPTime;
import me.nestorioxxx.game.UHCGame;
import me.nestorioxxx.player.UHCPlayer;
import me.nestorioxxx.utils.BorderUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Recipe;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;

import static org.bukkit.Bukkit.getServer;

public class GameRunnable extends BukkitRunnable {

    int nextBorder = 0;
    int finalHealTime;
    int pvpTime;

    boolean pvpEnabled = false;
    boolean meetup = false;
    boolean finalBorder = false;

    boolean oneMinuteWarning = false;
    boolean tenSecondTimer = false;

    boolean oneMinutePvpWarning = false;
    boolean tenSecondPvpTimer = false;
    boolean fiveMinutePvpWarning = false;

    boolean oneMinuteHealWarning = false;

    boolean finalHealed = false;

    int taskID = 0;

    public GameRunnable() {
        //set "world" to uhc_world
        Bukkit.getWorld("uhc_world").setPVP(false);
        Bukkit.getWorld("uhc_world").setGameRuleValue("naturalRegeneration", "false");

        nextBorder = BorderShrink.Option.valueOf(BorderShrink.getInstance().getOption()).getNumVal() * 60;
        finalHealTime = FinalHeal.Option.valueOf(FinalHeal.getInstance().getOption()).getNumVal() * 60;
        pvpTime = PVPTime.Option.valueOf(PVPTime.getInstance().getOption()).getNumVal() * 60;

        if (GoldenHead.getInstance().getOption().equalsIgnoreCase("off")) {
            Iterator<Recipe> iter = getServer().recipeIterator();
            while (iter.hasNext()) {
                Recipe r = iter.next();
                if (r == PandaUHC.getInstance().goldenHead) {
                    iter.remove();
                }
            }
        }
    }

    public void run() {
        UHCGame.getInstance().gameTime++;
        UHCGame.getInstance().playerCount = UHCGame.getInstance().gamePlayers.size();

        //GAME WIN LOGIC
        //if (UHCGame.getInstance().gamePlayers.size() < 2) { UHCGame.getInstance().endGame(); }

        //FINAL HEAL LOGIC
        if (UHCGame.getInstance().gameTime >= (finalHealTime) - 60 && !oneMinuteHealWarning) {
            oneMinuteHealWarning = true;
            for (UHCPlayer player : UHCPlayer.getUhcPlayers()) {
                player.playerObject.sendMessage(ChatColor.GREEN + "One minute until final heal!");
            }
        }
        if (UHCGame.getInstance().gameTime >= (finalHealTime) - 10 && !finalHealed) {
            finalHealed = true;
            new BukkitRunnable() {
                @Override
                public void run () {
                    int i = (finalHealTime) - UHCGame.getInstance().gameTime;
                    if (i <= 0) {
                        for (UHCPlayer player : UHCPlayer.getUhcPlayers()) {
                            if (player.currentState == UHCPlayer.PlayerState.PLAYING) {
                                player.playerObject.setHealth(20);
                                player.playerObject.sendMessage(ChatColor.GREEN + "All players have been given final heal. Please don't ask for another one.");
                            }
                        }
                    } else {
                        for (UHCPlayer player : UHCPlayer.getUhcPlayers()) {
                            player.playerObject.sendMessage(ChatColor.GREEN + "" + i + "seconds until final heal");
                        }
                    }
                }
            }.runTaskTimerAsynchronously(PandaUHC.getInstance(), 0, 20);
        }

        // PVP LOGIC

        if (UHCGame.getInstance().gameTime >= (pvpTime) - 60 * 5 && !fiveMinutePvpWarning) {
            fiveMinutePvpWarning = true;
            for (UHCPlayer player : UHCPlayer.getUhcPlayers()) {
                player.playerObject.sendMessage(ChatColor.GREEN + "5 minutes until pvp enables!");
            }
        }

        if (UHCGame.getInstance().gameTime >= (pvpTime) - 60 && !oneMinutePvpWarning) {
            oneMinutePvpWarning = true;
            for (UHCPlayer player : UHCPlayer.getUhcPlayers()) {
                player.playerObject.sendMessage(ChatColor.GREEN + "One minute until pvp enables!");
            }
        }

        if (UHCGame.getInstance().gameTime >= (pvpTime) - 10 && !tenSecondPvpTimer) {
            tenSecondPvpTimer = true;
            new BukkitRunnable() {
                @Override
                public void run () {
                    int i = (pvpTime) - UHCGame.getInstance().gameTime;
                    if (i <= 0) {
                        pvpEnabled = true;
                        for (UHCPlayer player : UHCPlayer.getUhcPlayers()) {
                            player.playerObject.sendMessage(ChatColor.GREEN + "Pvp has been enabled! Players can now attack each others!");
                        }
                        Bukkit.getWorld("uhc_world").setPVP(true);
                    } else {
                        for (UHCPlayer player : UHCPlayer.getUhcPlayers()) {
                            player.playerObject.sendMessage(ChatColor.GREEN + "" + i + "seconds until pvp is enabled");
                        }
                    }
                }
            }.runTaskTimerAsynchronously(PandaUHC.getInstance(), 0, 20);
        }

        // <--- PVP LOGIC
        // ALL BORDER LOGIC
        if (UHCGame.getInstance().gameTime >= nextBorder - 60 && !oneMinuteWarning) {
            oneMinuteWarning = true;
            for (UHCPlayer player : UHCPlayer.getUhcPlayers()) {
                player.playerObject.sendMessage(ChatColor.GREEN + "One minute until border shrinks to " + nextBorder(UHCGame.getInstance().currentBorder) + " radius!");
            }
        }

        if (UHCGame.getInstance().gameTime >= nextBorder - 10 && !tenSecondTimer) {
            tenSecondTimer = true;
            taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(PandaUHC.getInstance(), () -> {
                    int i = nextBorder - UHCGame.getInstance().gameTime;
                    if (i <= 0) {
                        BorderUtil.bedrockBorder(nextBorder(UHCGame.getInstance().currentBorder), Bukkit.getWorld("uhc_world"));
                        WorldBorder.plugin.getWorldBorder("uhc_world").setData(0, 0, nextBorder(UHCGame.getInstance().currentBorder), false);
                        for (UHCPlayer player : UHCPlayer.getUhcPlayers()) {
                            player.playerObject.sendMessage(ChatColor.GREEN + "Border has shrunk to " + nextBorder(UHCGame.getInstance().currentBorder) + " radius!");
                        }
                        UHCGame.getInstance().currentBorder = nextBorder(UHCGame.getInstance().currentBorder);

                        if (nextBorder(UHCGame.getInstance().currentBorder) != UHCGame.getInstance().currentBorder) {
                            for (UHCPlayer player : UHCPlayer.getUhcPlayers()) {
                                player.playerObject.sendMessage(ChatColor.GREEN + "Border will shrink to " + nextBorder(UHCGame.getInstance().currentBorder) + " radius in five minutes!");
                            }
                            nextBorder += 60 * 5;
                            oneMinuteWarning = false;
                            tenSecondTimer = false;
                            endTask(taskID);
                        }
                    } else {
                        for (UHCPlayer player : UHCPlayer.getUhcPlayers()) {
                            player.playerObject.sendMessage(ChatColor.GREEN + "" + i + " seconds until border shrinks to " + nextBorder(UHCGame.getInstance().currentBorder) + " radius!");
                        }
                    }
            }, 0, 20);
         }
        // <----- BORDER LOGIC

    }

    public int nextBorder(int radius) {
        if (radius - 500 >= 500) {
            return radius - 500;
        } else if (radius == 500) {
            return 250;
        } else if (radius == 250) {
            return 100;
        } else if (radius == 100) {
            return 50;
        }
        return radius;
    }

    public void endTask(int task) {
        Bukkit.getScheduler().cancelTask(task);
    }
}
