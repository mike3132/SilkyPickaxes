package net.resolutemc.silkypickaxes;

import net.milkbowl.vault.economy.Economy;
import net.resolutemc.silkypickaxes.Chat.ColorTranslate;
import net.resolutemc.silkypickaxes.Commands.GiveCommand;
import net.resolutemc.silkypickaxes.Commands.TabComplete;
import net.resolutemc.silkypickaxes.Configs.ConfigCreator;
import net.resolutemc.silkypickaxes.Events.AnvilEvent;
import net.resolutemc.silkypickaxes.Events.BlockBreak;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class SilkyPickaxes extends JavaPlugin {

    private static SilkyPickaxes INSTANCE;
    private final ColorTranslate colorTranslate = new ColorTranslate();
    private Economy economy = null;

    @Override
    public void onEnable() {
        // Plugin startup logic
        INSTANCE = this;
        getServer().getConsoleSender().sendMessage(colorTranslate.chatColor("&bSilky &dPickaxes &7>  &2ENABLED"));

        if (!Bukkit.getPluginManager().isPluginEnabled("RoseStacker")) {
            getLogger().log(Level.SEVERE, "Please install RoseStacker > https://www.spigotmc.org/resources/rosestacker.82729/");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        if (!setupEconomy() ) {
            getLogger().log(Level.SEVERE, "Please install Vault > https://www.spigotmc.org/resources/vault.34315/");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        Bukkit.getPluginManager().registerEvents(new BlockBreak(), this);
        Bukkit.getPluginManager().registerEvents(new AnvilEvent(), this);

        registerCommands();
        saveDefaultConfig();
        ConfigCreator.MESSAGES.create();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(colorTranslate.chatColor("&bSilky &dPickaxes &7> &4DISABLED"));
    }

    public static SilkyPickaxes getInstance() {
        return INSTANCE;
    }

    public Economy getEconomy() {
        return economy;
    }

    private void registerCommands() {
        new GiveCommand();
        new TabComplete();
    }
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

}
