# Custom Minecart Speeds

> If this project helped you, please consider leaving some stars [on the Github](https://github.com/Derec-Mods)!

Plugin for admin management meant to complement the Minecart Improvements experimental feature, letting servers configure speed individually for each Minecart type. You must have it enabled. Yes, you can enable minecart improvements on existing worlds, just use a level.dat editor

The new `minecartMaxSpeed` gamerule in Minecraft 1.21.2+ is awesome for high-speed transit, but cranking it up globally breaks hopper minecart sorting systems, item collection lines, and technical redstone contraptions. This plugin solves that by letting you keep fast rideable minecarts for travel while locking hopper carts, chest carts, or other utility carts to vanilla speed or any custom cap you choose.

## Current Features

*   Independent speed configuration for all 7 minecart variants: Rideable, Chest, Hopper, TNT, Furnace, Spawner, and Command Block
*   Built to complement the vanilla `minecart_improvements` experimental datapack and `/gamerule minecartMaxSpeed`
*   `gamerule` mode (default): Leaves carts untouched so they natively inherit whatever the global `minecartMaxSpeed` gamerule is set to
*   `default` mode: Locks specific carts (like Hopper Carts) to vanilla baseline speed (8 blocks/sec or 0.4 blocks/tick) so sorting systems never skip items
*   Custom numeric speeds: Supports custom speed limits in both blocks per second (e.g. `20`) and blocks per tick (e.g. `0.8`)
*   Zero movement tick overhead: Speeds are applied natively via listeners
*   Dynamic world updates: There's a reload command 

## Configuration

```yaml
minecart: gamerule
chest-minecart: gamerule
hopper-minecart: default
tnt-minecart: gamerule
furnace-minecart: gamerule
spawner-minecart: gamerule
command-block-minecart: gamerule
```

## Planned Features

*   Whatever Mojang adjusts in future minecart physics snapshots. Until then, I will continue spreading the pro-Minecart agenda across Reddit and Instagram 

## ❤️ Credits:

Coded by DerexXD
