package me.nestorioxxx.player;

import lombok.Getter;
import me.nestorioxxx.utils.ItemCreatorUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class UHCPlayer {
    @Getter private static ArrayList<UHCPlayer> uhcPlayers = new ArrayList<>();
    public enum PlayerState { LOBBY, PLAYING, SPECTATING}


    public Player playerObject;
    public PlayerState currentState;
    public UUID uuid;

    public int currentKills;


    public UHCPlayer(Player p) {
        this.currentState = PlayerState.LOBBY;
        this.playerObject = p;
        this.uuid = p.getUniqueId();

        uhcPlayers.add(this);
    }

    public static UHCPlayer getUHCPlayer(Player player) {
        for (UHCPlayer uhcplayer : getUhcPlayers()) {
            if (uhcplayer.uuid == player.getUniqueId())
                return uhcplayer;
        }
        return null;
    }

    public void reset() {
        currentState = PlayerState.LOBBY;
        playerObject.teleport(new Location(Bukkit.getWorld("Lobby"), 0,0,0));

        playerObject.getInventory().clear();

        playerObject.getInventory().setItem(0, ItemCreatorUtil.ItemCreator(Material.DIAMOND_SWORD, ChatColor.BLUE + "Practice", Collections.singletonList("Right click for practice!")));
        playerObject.getInventory().setItem(4, ItemCreatorUtil.ItemCreator(Material.WATCH, ChatColor.GOLD + "Config"));
        playerObject.getInventory().setItem(8, ItemCreatorUtil.ItemCreator(Material.COMPASS, ChatColor.BLUE + "Settings", Collections.singletonList("Coming soon!")));
    }

    public void startGame() {
        currentState = PlayerState.PLAYING;
        playerObject.getInventory().clear();
        playerObject.getInventory().setItem(0, new ItemStack(Material.BOOK));
    }

    public void remove() {
        uhcPlayers.removeIf(player -> player == this);
    }
}
