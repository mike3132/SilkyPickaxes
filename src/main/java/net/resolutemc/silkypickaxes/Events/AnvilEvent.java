package net.resolutemc.silkypickaxes.Events;

import net.resolutemc.silkypickaxes.Chat.ChatMessages;
import net.resolutemc.silkypickaxes.SilkyPickaxes;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class AnvilEvent implements Listener {

    private final ChatMessages chatMessages = new ChatMessages();
    private final NamespacedKey singleUseKey = new NamespacedKey(SilkyPickaxes.getInstance(), "Single-Use-Pickaxe-Key");
    private final NamespacedKey multiUseKey = new NamespacedKey(SilkyPickaxes.getInstance(), "Multi-Use-Pickaxe-Key");

    private final boolean singleUseAnvil = SilkyPickaxes.getInstance().getConfig().getBoolean("Singe-Use-Anvil-Use");
    private final boolean multiUseAnvil = SilkyPickaxes.getInstance().getConfig().getBoolean("Singe-Use-Anvil-Use");

    @EventHandler
    public void playerClickAnvil(InventoryClickEvent ice) {
        Player player = (Player) ice.getWhoClicked();
        Inventory blockInventory = ice.getClickedInventory();
        ItemStack item = ice.getCurrentItem();

        if (blockInventory == null) return;
        if (blockInventory.getType().equals(InventoryType.ANVIL)) {
            if (ice.getSlot() == 2) {
                if (item.getItemMeta() == null) return;
                if (item.getItemMeta().getPersistentDataContainer().has(singleUseKey, PersistentDataType.STRING)) {
                    if (singleUseAnvil) return;
                    ice.setCancelled(true);
                    chatMessages.sendPlayerMessage(player, "Anvil-disabled-message");
                    player.playSound(player, Sound.BLOCK_ANVIL_DESTROY, 1, 1);
                    return;
                }
                if (item.getItemMeta().getPersistentDataContainer().has(multiUseKey, PersistentDataType.STRING)) {
                    if (multiUseAnvil) return;
                    ice.setCancelled(true);
                    chatMessages.sendPlayerMessage(player, "Anvil-disabled-message");
                    player.playSound(player, Sound.BLOCK_ANVIL_DESTROY, 1, 1);
                }
            }
        }
    }
}
