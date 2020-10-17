package me.nestorioxxx.config.options;

import lombok.Getter;
import me.nestorioxxx.config.MatchOptions;
import me.nestorioxxx.utils.ItemCreatorUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;

public class Nether extends MatchOptions{
    //Nether is bugged atm, just keep it as OFF
    public Option option = Option.OFF;
    @Getter
    private static MatchOptions instance;

    public Nether() {
        super("If nether is on", ItemCreatorUtil.ItemCreator(Material.NETHER_BRICK, ChatColor.BLUE + "Nether"));
        instance = this;
    }

    public String getOption() {
        return option.name();
    }

    public void setOption(String name) {
        option = Option.valueOf(name);
    }

    public ArrayList<String> getOptionOptions() {
        ArrayList<String> array = new ArrayList<>();
        for (Option op : Option.values()) {
            array.add(op.name());
        }
        return array;
    }

    public enum Option {
        ON, OFF;
    }
}
