package io.github.derexxd.customminecartspeeds;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.world.EntitiesLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class CustomMinecartSpeeds extends JavaPlugin implements Listener {

    private Map<EntityType, Double> speedLimits = new EnumMap<>(EntityType.class);

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadSpeeds();
        getServer().getPluginManager().registerEvents(this, this);
    }

    private void loadSpeeds() {
        speedLimits.clear();
        parseSpeed(EntityType.MINECART, "minecart");
        parseSpeed(EntityType.CHEST_MINECART, "chest-minecart", "chest_minecart", "chest");
        parseSpeed(EntityType.HOPPER_MINECART, "hopper-minecart", "hopper_minecart", "hopper");
        parseSpeed(EntityType.TNT_MINECART, "tnt-minecart", "tnt_minecart", "tnt");
        parseSpeed(EntityType.FURNACE_MINECART, "furnace-minecart", "furnace_minecart", "furnace");
        parseSpeed(EntityType.SPAWNER_MINECART, "spawner-minecart", "spawner_minecart", "spawner");
        parseSpeed(EntityType.COMMAND_BLOCK_MINECART, "command-block-minecart", "command_block_minecart", "command-block");

        for (World world : getServer().getWorlds()) {
            for (Minecart minecart : world.getEntitiesByClass(Minecart.class)) {
                Double limit = speedLimits.get(minecart.getType());
                if (limit != null) {
                    minecart.setMaxSpeed(limit);
                }
            }
        }
    }

    private void parseSpeed(EntityType type, String... keys) {
        String raw = null;
        for (String key : keys) {
            if (getConfig().contains(key)) {
                raw = getConfig().getString(key);
                break;
            }
        }
        if (raw == null || raw.equalsIgnoreCase("gamerule")) {
            return;
        }
        if (raw.equalsIgnoreCase("default")) {
            speedLimits.put(type, 0.4);
            return;
        }
        try {
            double val = Double.parseDouble(raw);
            if (val > 0.0) {
                double bpt = val > 1.0 ? val / 20.0 : val;
                speedLimits.put(type, bpt);
            }
        } catch (NumberFormatException ignored) {
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onVehicleCreate(VehicleCreateEvent event) {
        if (event.getVehicle() instanceof Minecart minecart) {
            Double limit = speedLimits.get(minecart.getType());
            if (limit != null) {
                minecart.setMaxSpeed(limit);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntitiesLoad(EntitiesLoadEvent event) {
        for (Entity entity : event.getEntities()) {
            if (entity instanceof Minecart minecart) {
                Double limit = speedLimits.get(minecart.getType());
                if (limit != null) {
                    minecart.setMaxSpeed(limit);
                }
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("customminecartspeeds.reload")) {
                sender.sendMessage("§cYou do not have permission to run this command.");
                return true;
            }
            reloadConfig();
            loadSpeeds();
            sender.sendMessage("§a[Custom-Minecart-Speeds] Configuration reloaded.");
            return true;
        }
        sender.sendMessage("§e[Custom-Minecart-Speeds] Use /" + label + " reload to reload configuration.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1 && "reload".startsWith(args[0].toLowerCase())) {
            return Collections.singletonList("reload");
        }
        return Collections.emptyList();
    }
}
