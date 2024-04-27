package net.resolutemc.silkypickaxes.Chat;

import net.resolutemc.silkypickaxes.SilkyPickaxes;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class ChatMessages {

    public void sendPlayerMessage(Player player, String key) {
        File messagesConfig = new File(SilkyPickaxes.getInstance().getDataFolder(), "messages.yml");
        YamlConfiguration configMessages = YamlConfiguration.loadConfiguration(messagesConfig);
        String message = configMessages.getString("Messages.Prefix") + configMessages.getString("Messages." + key);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public void sendConsoleMessage(CommandSender sender, String key) {
        File messagesConfig = new File(SilkyPickaxes.getInstance().getDataFolder(), "messages.yml");
        YamlConfiguration configMessages = YamlConfiguration.loadConfiguration(messagesConfig);
        String message = configMessages.getString("Messages.Prefix") + configMessages.getString("Messages." + key);
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
