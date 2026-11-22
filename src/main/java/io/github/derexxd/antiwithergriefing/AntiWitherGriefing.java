package io.github.derexxd.antiwithergriefing;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiWitherGriefing extends JavaPlugin implements Listener {

    private boolean preventBlockBreak;
    private boolean preventExplosions;
    private boolean preventPortals;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        preventBlockBreak = getConfig().getBoolean("prevent-block-break", true);
        preventExplosions = getConfig().getBoolean("prevent-explosions", true);
        preventPortals = getConfig().getBoolean("prevent-portals", true);
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        if (preventBlockBreak && event.getEntityType() == EntityType.WITHER) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent event) {
        EntityType type = event.getEntityType();
        if (preventExplosions && (type == EntityType.WITHER || type == EntityType.WITHER_SKULL)) {
            event.blockList().clear();
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityPortal(EntityPortalEvent event) {
        if (!preventPortals) {
            return;
        }
        EntityType type = event.getEntityType();
        if (type == EntityType.WITHER) {
            event.setCancelled(true);
        } else if (type == EntityType.WITHER_SKULL) {
            event.setCancelled(true);
            event.getEntity().remove();
        }
    }
}
