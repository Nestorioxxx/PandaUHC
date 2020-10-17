package me.nestorioxxx.scoreboard;

import io.github.thatkawaiisam.assemble.AssembleAdapter;
import me.nestorioxxx.PandaUHC;
import me.nestorioxxx.game.UHCGame;
import me.nestorioxxx.player.UHCPlayer;
import me.nestorioxxx.utils.DecimalUtil;
import me.nestorioxxx.world.WorldManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardManager implements AssembleAdapter {
    @Override
    public String getTitle(Player player) {
        return ChatColor.GREEN + "Panda UHC";
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> lines = new ArrayList<>();
        lines.add(ChatColor.BLACK + "-------------------------");
        if (UHCGame.getInstance().currentState == UHCGame.gameState.SCATTERING) {
            lines.add(ChatColor.GREEN + "Players are being scattered....");
        } else if (UHCGame.getInstance().currentState == UHCGame.gameState.PLAYING) {

            if (UHCGame.getInstance().gameTime % 60 < 10) {
                lines.add(UHCGame.getInstance().gameTime / 60 + ":0" + UHCGame.getInstance().gameTime % 60);
            } else {
                lines.add(UHCGame.getInstance().gameTime / 60 + ":" + UHCGame.getInstance().gameTime % 60);
            }

            lines.add("");
            lines.add(ChatColor.GREEN + "Kills: " + UHCPlayer.getUHCPlayer(player).currentKills);
            lines.add(ChatColor.GREEN + "Players left: " + UHCGame.getInstance().playerCount);
            lines.add("");
            lines.add(ChatColor.GREEN + "Current Border Radius: " + UHCGame.getInstance().currentBorder);
            lines.add("");
            lines.add("na1.pandauhc.com");

        } else if (UHCGame.getInstance().currentState == UHCGame.gameState.WHITELISTED) {
            lines.add(ChatColor.GREEN + "Whitelisted....");
        } else if (UHCGame.getInstance().currentState == UHCGame.gameState.UNWHITELISTED) {
            lines.add(ChatColor.GREEN + "Waiting for players!");
            lines.add("");
            lines.add(ChatColor.GREEN + "Players: " + PandaUHC.getInstance().getServer().getOnlinePlayers().size());
        }  else if (UHCGame.getInstance().currentState == UHCGame.gameState.ENDING) {
            lines.add("This game is over. Congrats to the winner!");
            lines.add("");
            lines.add(ChatColor.GREEN + "Kills: " + UHCPlayer.getUHCPlayer(player).currentKills);
        } else if (UHCGame.getInstance().currentState == UHCGame.gameState.GENERATING) {
            lines.add("Currently generating world....");
            lines.add("Generating world: " + WorldManager.getInstance().currentGenning.toString());
            lines.add("Progress: " + DecimalUtil.round(WorldManager.getInstance().fillTask.getPercentageCompleted()) + "%");
        }
        lines.add(ChatColor.BLACK + "-------------------------");
        return lines;
    }
}
