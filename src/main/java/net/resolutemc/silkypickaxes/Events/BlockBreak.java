package net.resolutemc.silkypickaxes.Events;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.resolutemc.silkypickaxes.Chat.ChatMessages;
import net.resolutemc.silkypickaxes.SilkyPickaxes;
import net.resolutemc.silkypickaxes.Utils.PickaxeMap;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class BlockBreak implements Listener {

    private final NamespacedKey singleUseKey = new NamespacedKey(SilkyPickaxes.getInstance(), "Single-Use-Pickaxe-Key");
    private final NamespacedKey multiUseKey = new NamespacedKey(SilkyPickaxes.getInstance(), "Multi-Use-Pickaxe-Key");
    private final boolean singleUseEconomy = SilkyPickaxes.getInstance().getConfig().getBoolean("Single-Use-Economy-Charge");
    private final boolean multiUseEconomy = SilkyPickaxes.getInstance().getConfig().getBoolean("Multi-Use-Economy-Charge");
    private final Economy economy = SilkyPickaxes.getInstance().getEconomy();
    private final ChatMessages chatMessages = new ChatMessages();
    private final PickaxeMap pickaxeMap = new PickaxeMap();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent bbe) {
        Player player = bbe.getPlayer();
        Block block = bbe.getBlock();

        if (!(block.getState() instanceof CreatureSpawner)) return;
        if (player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) return;
        if (player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(singleUseKey, PersistentDataType.STRING)) {
            if (player.isSneaking()) {
                chatMessages.sendPlayerMessage(player, "Player-Crouched");
                bbe.setCancelled(true);
                return;
            }
            pickaxeMap.activate(player);
            if (singleUseEconomy) {
                double amount = SilkyPickaxes.getInstance().getConfig().getDouble("Single-Use-Price");
                EconomyResponse response = economy.withdrawPlayer(player, amount);
                if (!response.transactionSuccess()) {
                    chatMessages.sendPlayerMessage(player, "Pickaxe-Use-Economy-Money-Missing-Message");
                    bbe.setCancelled(true);
                    return;
                }
                chatMessages.sendPlayerMoneyMessage(player, "Single-Use-Economy-Money-Taken-Message", String.valueOf(amount));
                chatMessages.sendPlayerMoneyMessage(player, "Pickaxe-Use-Economy-Balance-Left-Message", String.valueOf(response.balance));
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        pickaxeMap.deactivate(player);
                        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                    }
                }.runTaskLaterAsynchronously(SilkyPickaxes.getInstance(), 1L);
            }
            return;
        }
        if (player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(multiUseKey, PersistentDataType.STRING)) {
            if (player.isSneaking()) {
                chatMessages.sendPlayerMessage(player, "Player-Crouched");
                bbe.setCancelled(true);
                return;
            }
            pickaxeMap.activate(player);
            if (multiUseEconomy) {
                double amount = SilkyPickaxes.getInstance().getConfig().getDouble("Multi-Use-Price");
                EconomyResponse response = economy.withdrawPlayer(player, amount);
                if (!response.transactionSuccess()) {
                    chatMessages.sendPlayerMessage(player, "Pickaxe-Use-Economy-Money-Missing-Message");
                    bbe.setCancelled(true);
                    return;
                }
                chatMessages.sendPlayerMoneyMessage(player, "Multi-Use-Economy-Money-Taken-Message", String.valueOf(amount));
                chatMessages.sendPlayerMoneyMessage(player, "Pickaxe-Use-Economy-Balance-Left-Message", String.valueOf(response.balance));
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        pickaxeMap.deactivate(player);
                    }
                }.runTaskLaterAsynchronously(SilkyPickaxes.getInstance(), 1L);
            }
        }
    }
}
