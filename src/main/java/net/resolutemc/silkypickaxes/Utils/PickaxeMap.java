package net.resolutemc.silkypickaxes.Utils;

import net.resolutemc.silkypickaxes.SilkyPickaxes;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.UUID;

public class PickaxeMap {

    private final HashMap<UUID, PermissionAttachment> playersWithPermission = new HashMap<>();

    public void activate(Player player) {
        String permissionString = "rosestacker.silktouch.*";
        PermissionAttachment attachment = player.addAttachment(SilkyPickaxes.getInstance());
        playersWithPermission.put(player.getUniqueId(), attachment);
        PermissionAttachment permission = playersWithPermission.get(player.getUniqueId());
        permission.setPermission(permissionString, true);
    }

    public void deactivate(Player player) {
        String permissionString = "rosestacker.silktouch.*";
        PermissionAttachment permission = playersWithPermission.get(player.getUniqueId());
        if (playersWithPermission.containsKey(player.getUniqueId())) {
            permission.unsetPermission(permissionString);
            playersWithPermission.remove(player.getUniqueId());
        }
    }



}
