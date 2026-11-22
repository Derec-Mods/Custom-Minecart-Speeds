package io.github.derexxd.customminecartspeeds;

import org.bukkit.World;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.EnumMap;
import java.util.Map;

public class CustomMinecartSpeeds extends JavaPlugin {

    private Map<EntityType, Double> speedLimits = new EnumMap<>(EntityType.class);

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadSpeeds();
        getServer().getPluginManager().registerEvents(new MinecartListener(this), this);
        CommandManager commandManager = new CommandManager(this);
        PluginCommand command = getCommand("custom-minecart-speeds");
        if (command != null) {
            command.setExecutor(commandManager);
            command.setTabCompleter(commandManager);
        }
    }

    public void loadSpeeds() {
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

    public Double getSpeedLimit(EntityType type) {
        return speedLimits.get(type);
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
}
