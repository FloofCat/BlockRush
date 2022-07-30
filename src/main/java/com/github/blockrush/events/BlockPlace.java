package com.github.blockrush.events;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.blockrush.main.BlockRush;

import net.md_5.bungee.api.ChatColor;

public class BlockPlace implements Listener {

	private List<Material> woolTypes = Arrays.asList(Material.BLACK_WOOL, Material.BLUE_WOOL, Material.BROWN_WOOL,
			Material.CYAN_WOOL, Material.GRAY_WOOL, Material.GREEN_WOOL, Material.LIGHT_BLUE_WOOL,
			Material.LIGHT_GRAY_WOOL, Material.LIME_WOOL, Material.MAGENTA_WOOL, Material.ORANGE_WOOL,
			Material.PINK_WOOL, Material.PURPLE_WOOL, Material.RED_WOOL, Material.WHITE_WOOL, Material.YELLOW_WOOL);
	private BlockRush plugin;

	public BlockPlace(BlockRush main) {
		this.plugin = main;
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		if (!event.getPlayer().getWorld().getName().equals("blockrush")) {
			return;
		}
		
		if (event.getBlock().getLocation().getBlockY() >= 155 || event.getBlock().getLocation().getBlockY() <= 98) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.RED + "You cannot build here!");
		}

		if (event.getBlock().getType().equals(Material.WHITE_WOOL)) {
			Location blockLoc = event.getBlock().getLocation();
			this.runWoolPlacement(blockLoc);
		}
	}

	private void runWoolPlacement(Location blockLoc) {
		Collections.shuffle(woolTypes);
		blockLoc.getWorld().getBlockAt(blockLoc).setType(woolTypes.get(0));
	}

}
