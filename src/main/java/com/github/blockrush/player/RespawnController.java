package com.github.blockrush.player;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.blockrush.game.Game;
import com.github.blockrush.game.GameStates;
import com.github.blockrush.scoreboards.GameScoreboard;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class RespawnController {

	private PlayerDataController playerDataController;
	private GameScoreboard gameScoreboard;
	private KitController kitController;
	private Game game;

	public RespawnController(PlayerDataController playerData, GameScoreboard gameScord, KitController kitCon, Game g) {
		this.playerDataController = playerData;
		this.gameScoreboard = gameScord;
		this.kitController = kitCon;
		this.game = g;
	}

	public void respawnPlayer(Player player) {
		player.setGameMode(GameMode.SPECTATOR);
		player.teleport(new Location(Bukkit.getWorld("blockrush"), 100, 123, 100));

		for (Player players : Bukkit.getOnlinePlayers()) {
			players.sendMessage(ChatColor.RED + "[💀] " + ChatColor.AQUA + player.getName() + ChatColor.GOLD
					+ " was girlbossed, and died to the void.");
		}
		// Decrease Scoreboard + Stat
		this.gameScoreboard.updateLives(this.playerDataController.getLives(player),
				this.playerDataController.getLives(player) - 1, player);
		this.playerDataController.decreaseLives(player);
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
				new TextComponent(ChatColor.RED + "You have " + this.playerDataController.getLives(player) + " left!"));

		if (this.testGameOver()) {
			for(Player players : Bukkit.getOnlinePlayers()) {
				players.sendTitle(ChatColor.BOLD + "" + ChatColor.GOLD + "Game Over!", ChatColor.AQUA + player.getName() + " is the winner!", 0, 100, 0);
				players.playSound(players.getLocation(), Sound.ENTITY_WITHER_DEATH, 1, 0);
				players.setGameMode(GameMode.SPECTATOR);
				players.getInventory().clear();
			}
			this.game.switchState(GameStates.ENDGAME);
		}

		if (this.playerDataController.getLives(player) == 0) {
			player.sendMessage(ChatColor.RED + "[💀] " + ChatColor.GOLD + "You will no longer respawn.");
			return;
		}

		this.runRespawnRunnable(player);
	}

	private boolean testGameOver() {
		int aliveCount = 0;
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (this.playerDataController.getLives(player) != 0) {
				aliveCount++;
			}
		}

		if (aliveCount == 1) {
			return true;
		} else {
			return false;
		}
	}

	private void runRespawnRunnable(Player player) {
		new BukkitRunnable() {
			int tempRes = 3;

			@Override
			public void run() {
				tempRes--;

				if (tempRes == 0) {
					cancel();
					player.setHealth(20);
					kitController.givePlayerKit(player);
					player.setGameMode(GameMode.SURVIVAL);
					player.teleport(new Location(Bukkit.getWorld("blockrush"), 100, 123, 100));
					player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 255));
				}

				player.sendTitle(ChatColor.GREEN + "" + tempRes, ChatColor.GOLD + "Respawning in...", 0, 20, 0);

			}

		}.runTaskTimer(null, 0, 20);
	}
}
