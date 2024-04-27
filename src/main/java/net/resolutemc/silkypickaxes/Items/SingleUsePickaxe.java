package net.resolutemc.silkypickaxes.Items;

import net.resolutemc.silkypickaxes.Chat.ColorTranslate;
import net.resolutemc.silkypickaxes.SilkyPickaxes;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class SingleUsePickaxe {
    ColorTranslate colorTranslate = new ColorTranslate();

    private ItemStack pickaxe;

    public SingleUsePickaxe() {
        this.createPickaxe();
    }

    public ItemStack getPickaxe() {
        return this.pickaxe;
    }

    public void createPickaxe() {
        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        for (String configLore : SilkyPickaxes.getInstance().getConfig().getStringList("Single-Use-Pickaxe-Lore")) {
            lore.add(colorTranslate.chatColor("" + configLore));
        }

        meta.setDisplayName(colorTranslate.chatColor(SilkyPickaxes.getInstance().getConfig().getString("Single-Use-Pickaxe-Name")));
        meta.setLore(lore);
        NamespacedKey key = new NamespacedKey(SilkyPickaxes.getInstance(), "Single-Use-Pickaxe-Key");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "SingleUse-Key");
        meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
        meta.setCustomModelData(SilkyPickaxes.getInstance().getConfig().getInt("Single-Use-Model-Data"));
        item.setItemMeta(meta);
        pickaxe = item;
    }


}
