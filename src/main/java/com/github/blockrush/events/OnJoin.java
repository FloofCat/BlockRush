package com.github.blockrush.events;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.github.blockrush.game.GameStates;
import com.github.blockrush.main.BlockRush;
import com.github.blockrush.player.PlayerDataController;

public class OnJoin implements Listener{

	private BlockRush plugin;
	private PlayerDataController playerDataController;
	public OnJoin(BlockRush main, PlayerDataController playerData) {
		this.plugin = main;
		this.playerDataController = playerData;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		if(this.plugin.getGame().getCurrentState().equals(GameStates.GAME_START)) {
			if(this.playerDataController.ifExists(event.getPlayer())) {
				return;
			}
			event.getPlayer().setGameMode(GameMode.SPECTATOR);
		}
	}
}
