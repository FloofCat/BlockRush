package com.github.blockrush.player;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KitController {

	public void givePlayerKit(Player player) {
		player.getInventory().setItem(0, new ItemStack(Material.WHITE_WOOL, 64));
		player.getInventory().setItem(1, new ItemStack(Material.SHEARS, 1));
	}
}
