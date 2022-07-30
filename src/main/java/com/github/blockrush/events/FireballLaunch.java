package com.github.blockrush.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class FireballLaunch implements Listener{

	@SuppressWarnings("deprecation")
	@EventHandler
	public void fireballThrow(PlayerInteractEvent event){
		if(!event.getPlayer().getWorld().getName().equals("blockrush")) {
			return;
		}
		
        Action eventAction = event.getAction();
        Player player = event.getPlayer();
 
       if (eventAction == Action.RIGHT_CLICK_AIR || eventAction == Action.RIGHT_CLICK_BLOCK){
            if (player.getItemInHand().getType().equals(Material.FIRE_CHARGE)){
                 player.launchProjectile(SmallFireball.class).setVelocity(player.getLocation().getDirection().multiply(0.5));
            }
        }
    }
}
