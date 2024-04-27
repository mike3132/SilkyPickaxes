package net.resolutemc.silkypickaxes.Items;

import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class ToolFactory {

    public ItemStack getItem(String string) {
        switch (string.toUpperCase(Locale.ROOT)) {
            case "SINGLEUSE":
                SingleUsePickaxe singleUsePickaxe = new SingleUsePickaxe();
                return singleUsePickaxe.getPickaxe();
            case "MULTIUSE":
                MultiUsePickaxe multiUsePickaxe = new MultiUsePickaxe();
                return multiUsePickaxe.getPickaxe();
        }
        return null;
    }

}
