package me.nestorioxxx.world;

import com.wimbli.WorldBorder.WorldBorder;
import com.wimbli.WorldBorder.WorldFillTask;
import lombok.Getter;
import lombok.Setter;
import me.nestorioxxx.PandaUHC;
import me.nestorioxxx.game.UHCGame;
import me.nestorioxxx.utils.BorderUtil;
import org.bukkit.*;

import java.io.File;

public class WorldManager {
    @Getter static private WorldManager instance;
    public World uhcWorld;
    public World netherWorld;

    @Setter public WorldFillTask fillTask;

    @Setter public Boolean genned;
    @Setter boolean currentlyGenerating = false;

    public enum Worlds { UHC_WORLD, UHC_NETHER }
    public Worlds currentGenning;

    int diamondRate = 0;
    int goldRate = 0;
    public WorldManager() {
        instance = this;
        String path = PandaUHC.getInstance().getServer().getWorldContainer().getAbsolutePath();
        File file = new File(path, "uhc_world");
        if (file.exists()) {
            genned = true;
            uhcWorld = new WorldCreator("uhc_world").createWorld();
        } else {
            genned = false;
        }
    }

    public void generate() {
        //set ore rates to whatever input was
        currentlyGenerating = true;
        Bukkit.broadcastMessage(ChatColor.GREEN + "The world generation process has been started!");
        createUhcWorld();
    }

    public void createUhcWorld() {
        currentGenning = Worlds.UHC_WORLD;
        uhcWorld = Bukkit.createWorld(new WorldCreator("uhc_world").environment(World.Environment.NORMAL).type(WorldType.NORMAL));
        BorderUtil.bedrockBorder(1500, uhcWorld);

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb shape square");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb uhc_world set 1500 1500 0 0");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb uhc_world fill 1000");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb fill confirm");
        UHCGame.getInstance().currentState = UHCGame.gameState.GENERATING;
    }

    public void createNether() {
        currentGenning = Worlds.UHC_NETHER;
        netherWorld = Bukkit.createWorld(new WorldCreator("uhc_nether").environment(World.Environment.NETHER).type(WorldType.NORMAL));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb shape square");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb uhc_nether set 400 400 0 0");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb uhc_nether fill 1000");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb fill confirm");
    }
}
