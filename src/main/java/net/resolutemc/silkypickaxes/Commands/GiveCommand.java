package net.resolutemc.silkypickaxes.Commands;

import net.resolutemc.silkypickaxes.Chat.ChatMessages;
import net.resolutemc.silkypickaxes.Chat.ColorTranslate;
import net.resolutemc.silkypickaxes.Items.ToolFactory;
import net.resolutemc.silkypickaxes.SilkyPickaxes;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCommand implements CommandExecutor {

    private final ToolFactory toolFactory = new ToolFactory();
    private final ColorTranslate colorTranslate = new ColorTranslate();
    private final ChatMessages chatMessages = new ChatMessages();

    public GiveCommand() {
        SilkyPickaxes.getInstance().getCommand("SilkyPickaxe").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("SilkyPickaxes.Command")) {
            chatMessages.sendConsoleMessage(sender, "No-permissions-placeholder");
            return false;
        }
        if (args.length == 0) {
            chatMessages.sendConsoleMessage(sender, "Not-enough-args-placeholder");
            return false;
        }
        if (args[0].equalsIgnoreCase("Reload")) {
            if (!sender.hasPermission("SilkySpawners.Command.Reload")) {
                chatMessages.sendConsoleMessage(sender, "No-permissions-placeholder");
                return false;
            }
            sender.sendMessage(colorTranslate.chatColor("&bSilky &dSpawners &7> &aPlugin config reloaded in &2" + (System.currentTimeMillis() - 1) + " &ams"));
            SilkyPickaxes.getInstance().reloadConfig();
            return false;
        }
        if (args[0].equalsIgnoreCase("List")) {
            if (!sender.hasPermission("SilkySpawners.Command.List")) {
                chatMessages.sendConsoleMessage(sender, "No-permissions-placeholder");
                return false;
            }
            chatMessages.sendConsoleMessage(sender, "Tools-list-placeholder-header");
            chatMessages.sendConsoleMessage(sender, "Tools-list-A");
            chatMessages.sendConsoleMessage(sender, "Tools-list-B");
            chatMessages.sendConsoleMessage(sender, "Tools-list-placeholder-footer");
            return false;
        }

        if (!sender.hasPermission("SilkySpawners.Command.Give")) {
            chatMessages.sendConsoleMessage(sender, "No-permissions-placeholder");
            return false;
        }
        if (!args[0].equalsIgnoreCase("Give")) {
            chatMessages.sendConsoleMessage(sender, "Not-give-placeholder");
            return false;
        }
        if (args.length < 2) {
            chatMessages.sendConsoleMessage(sender, "Not-player-placeholder");
            return false;
        }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            chatMessages.sendConsoleMessage(sender, "Player-not-found-placeholder");
            return false;
        }
        if (args.length < 3) {
            chatMessages.sendConsoleMessage(sender, "Not-pickaxe-placeholder");
            return false;
        }
        ItemStack itemStack = toolFactory.getItem(args[2]);
        if (itemStack == null) {
            chatMessages.sendConsoleMessage(sender, "Pickaxe-not-found-placeholder");
            return false;
        }
        int amount = 1;
        if (args.length >= 4) {
            amount = Integer.parseInt(args[3]);
        }

        for (int i = 0; i < amount; i++) {
            if (target.getInventory().firstEmpty() == -1) {
                chatMessages.sendPlayerMessage(target, "Player-inventory-full-placeholder");
                target.getLocation().getWorld().dropItem(target.getLocation(), itemStack);
                return false;
            }
            chatMessages.sendPlayerMessage(target, "Player-give-tool-placeholder");
            target.getInventory().addItem(itemStack);
        }
        return false;
    }
}
