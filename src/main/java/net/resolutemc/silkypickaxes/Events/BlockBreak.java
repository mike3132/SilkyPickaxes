package net.resolutemc.silkypickaxes.Events;

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

    private final PickaxeMap pickaxeMap = new PickaxeMap();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent bbe) {
        Player player = bbe.getPlayer();
        Block block = bbe.getBlock();

        if (!(block.getState() instanceof CreatureSpawner)) return;
        if (player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) return;
        if (player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(singleUseKey, PersistentDataType.STRING)) {
            pickaxeMap.activate(player);
            new BukkitRunnable() {
                @Override
                public void run() {
                    pickaxeMap.deactivate(player);
                    player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() -1);
                }
            }.runTaskLaterAsynchronously(SilkyPickaxes.getInstance(), 1L);
            return;
        }
        if (player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(multiUseKey, PersistentDataType.STRING)) {
            pickaxeMap.activate(player);
            new BukkitRunnable() {
                @Override
                public void run() {
                    pickaxeMap.deactivate(player);
                }
            }.runTaskLaterAsynchronously(SilkyPickaxes.getInstance(), 1L);
            return;
        }
    }
}
