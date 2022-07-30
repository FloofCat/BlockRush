package com.github.blockrush.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class MiscEvents implements Listener {

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if(!e.getPlayer().getWorld().getName().equals("blockrush")) {
			return;
		}
		e.setCancelled(true);
	}

	@EventHandler
	public void onAttack(EntityDamageEvent e1) {
		if (e1.getEntity() instanceof Player) {
			Player player = (Player) e1.getEntity();
			if (!player.getWorld().getName().equals("blockrush")) {
				return;
			}
			player.setHealth(20);
		}

	}
}
