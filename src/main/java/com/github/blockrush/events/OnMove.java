package com.github.blockrush.events;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.github.blockrush.player.RespawnController;

public class OnMove implements Listener{

	private RespawnController respawnController;
	public OnMove(RespawnController respawn) {
		this.respawnController = respawn;
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if(!event.getPlayer().getWorld().getName().equals("blockrush")) {
			return;
		}
		
		if(event.getPlayer().getGameMode().equals(GameMode.SPECTATOR)) {
			return;
		}
		
		if(event.getPlayer().getLocation().getY() <= 85) {
			this.respawnController.respawnPlayer(event.getPlayer());
		}
	}
}
