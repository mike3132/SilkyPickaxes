package net.resolutemc.silkypickaxes.Chat;

import org.bukkit.ChatColor;

public class ColorTranslate {

    public String chatColor(String chatColor) {
        return ChatColor.translateAlternateColorCodes('&', chatColor);
    }

}
