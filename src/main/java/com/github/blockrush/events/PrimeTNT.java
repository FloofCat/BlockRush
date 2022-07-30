package com.github.blockrush.events;

import org.bukkit.Material;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PrimeTNT implements Listener{

	@EventHandler
	public void placeTNT(BlockPlaceEvent event) {
		if(!event.getPlayer().getWorld().getName().equals("blockrush")) {
			return;
		}
		
		if(event.getBlockPlaced().getType().equals(Material.TNT)) {
			event.setCancelled(true);
			event.getBlockPlaced().getLocation().getWorld().spawn(event.getBlockPlaced().getLocation().add(0.5, 0.5, 0.5), TNTPrimed.class);	
		}
	}
}
