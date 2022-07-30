package com.github.blockrush.player;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.github.blockrush.main.BlockRush;
import com.github.blockrush.scoreboards.GameScoreboard;

public class PlayerDataController {

	private HashMap<Player, Integer> playerLives = new HashMap<>();
	private HashMap<Player, ChatColor> playerColor = new HashMap<>();
	private BlockRush plugin;

	public PlayerDataController(BlockRush main) {
		this.plugin = main;
	}

	public void registerPlayer(Player player) {
		this.playerLives.put(player, 5);
		ChatColor playerColor = ChatColor.getByChar(Integer.toHexString(new Random().nextInt(16)));
		this.playerColor.put(player, playerColor);
	}

	public boolean ifExists(Player player) {
		return this.playerLives.containsKey(player);
	}

	public Integer getLives(Player player) {
		return this.playerLives.get(player);
	}

	public ChatColor getPlayerColor(Player player) {
		return this.playerColor.get(player);
	}

	public void decreaseLives(Player player) {
		// Reload Scoreboard
		
		this.playerLives.replace(player, this.getLives(player), this.getLives(player) - 1);
	}
}
