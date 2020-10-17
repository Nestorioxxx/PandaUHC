package me.nestorioxxx.utils;

import me.nestorioxxx.PandaUHC;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class BorderUtil {

    public static void bedrockBorder(int Radius, World world) {
        World w = world;
        int r = Radius;

        ArrayList<Block> block1 = new ArrayList<>();
        for (int i = r; i > -r; i--) {
            Location loc = new Location(w, r, 0, i);
            loc.setY(w.getHighestBlockYAt(loc) + 1);
            block1.add(loc.getBlock());
        }

        ArrayList<Block> block2 = new ArrayList<>();
        for (int i = r; i > -r; i--) {
            Location loc = new Location(w, -r, 0, i);
            loc.setY(w.getHighestBlockYAt(loc) + 1);
            block2.add(loc.getBlock());
        }

        ArrayList<Block> block3 = new ArrayList<>();
        for (int i = r; i > -r; i--) {
            Location loc = new Location(w, i, 0, r);
            loc.setY(w.getHighestBlockYAt(loc) + 1);
            block3.add(loc.getBlock());
        }

        ArrayList<Block> block4 = new ArrayList<>();
        for (int i = r; i > -r; i--) {
            Location loc = new Location(w, i, 0, -r);
            loc.setY(w.getHighestBlockYAt(loc) + 1);
            block4.add(loc.getBlock());
        }

        new BukkitRunnable() { @Override public void run() {
            for (Block block : block1) {
                block.setType(Material.BEDROCK);
            }
        } }.runTaskLater(PandaUHC.getInstance(), 2);
        new BukkitRunnable() { @Override public void run() {
            for (Block block : block2) {
                block.setType(Material.BEDROCK);
            }
        } }.runTaskLater(PandaUHC.getInstance(), 4);
        new BukkitRunnable() { @Override public void run() {
            for (Block block : block3) {
                block.setType(Material.BEDROCK);
            }
        } }.runTaskLater(PandaUHC.getInstance(), 6);
        new BukkitRunnable() { @Override public void run() {
            for (Block block : block4) {
                block.setType(Material.BEDROCK);
            }
        } }.runTaskLater(PandaUHC.getInstance(), 8);
    }

}
