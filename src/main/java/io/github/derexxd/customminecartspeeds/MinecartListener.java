package io.github.derexxd.customminecartspeeds;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.world.EntitiesLoadEvent;

public class MinecartListener implements Listener {

    private CustomMinecartSpeeds plugin;

    public MinecartListener(CustomMinecartSpeeds plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onVehicleCreate(VehicleCreateEvent event) {
        if (event.getVehicle() instanceof Minecart minecart) {
            Double limit = plugin.getSpeedLimit(minecart.getType());
            if (limit != null) {
                minecart.setMaxSpeed(limit);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntitiesLoad(EntitiesLoadEvent event) {
        for (Entity entity : event.getEntities()) {
            if (entity instanceof Minecart minecart) {
                Double limit = plugin.getSpeedLimit(minecart.getType());
                if (limit != null) {
                    minecart.setMaxSpeed(limit);
                }
            }
        }
    }
}
