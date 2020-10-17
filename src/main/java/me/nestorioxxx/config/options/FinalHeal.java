package me.nestorioxxx.config.options;

import lombok.Getter;
import me.nestorioxxx.config.MatchOptions;
import me.nestorioxxx.utils.ItemCreatorUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;

public class FinalHeal extends MatchOptions {
    //DONE
    public Option option = Option.TEN;
    @Getter
    private static MatchOptions instance;

    public FinalHeal() {
        super("When final heal is", ItemCreatorUtil.ItemCreator(Material.SPECKLED_MELON, ChatColor.BLUE + "Final Heal"));
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
        FIVE(5), TEN(10), TWENTY(20), TWENTYFIVE(25);
        private int numVal;
        Option(int numVal) {
            this.numVal = numVal;
        }
        public int getNumVal() {
            return numVal;
        }
    }
}
