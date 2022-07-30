package com.github.blockrush.game;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;

import com.github.blockrush.main.BlockRush;
import com.github.blockrush.player.KitController;
import com.github.blockrush.player.PlayerDataController;
import com.github.blockrush.scoreboards.GameScoreboard;

import net.md_5.bungee.api.ChatColor;

public class Game {

	private BlockRush plugin;
	private PlayerDataController playerDataController;
	private Integer tutCountdown = 25;
	private GameStates currentGameState;
	private KitController kitController;
	private GameScoreboard gameScoreboard;
	private List<ItemStack> randomItems = Arrays.asList(new ItemStack(Material.FIRE_CHARGE, 1),
			new ItemStack(Material.TNT, 1));

	public Game(BlockRush main, PlayerDataController playerData, KitController kits, GameScoreboard gameSC) {
		this.plugin = main;
		this.playerDataController = playerData;
		this.kitController = kits;
		this.gameScoreboard = gameSC;
	}

	public void switchState(GameStates state) {
		switch (state) {
		case ENDGAME:
			this.currentGameState = GameStates.ENDGAME;
			for(Player player : Bukkit.getOnlinePlayers()) {
				player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
			}
			
			break;
		case GAME_START:
			this.currentGameState = GameStates.GAME_START;
			
			// Give Kits
			for(Player player : Bukkit.getOnlinePlayers()) {
				player.setGameMode(GameMode.SURVIVAL);
				player.getInventory().clear();
				this.kitController.givePlayerKit(player);
			}
			this.gameScoreboard.enableScoreboard();

			this.giveRandomItems();
			break;

		case PREGAME:
			this.currentGameState = GameStates.PREGAME;
			// Registering all Players
			for (Player player : Bukkit.getOnlinePlayers()) {
				this.playerDataController.registerPlayer(player);
				player.setGameMode(GameMode.SPECTATOR);
				player.teleport(new Location(Bukkit.getWorld("blockrush"), 100, 123, 100));
			}
			
			this.gameScoreboard.init();

			// Tutorial
			this.runTutorialRunnable();

			break;
		default:
			break;

		}
	}

	private void runTutorialRunnable() {
		new BukkitRunnable() {

			@Override
			public void run() {
				tutCountdown--;

				if (tutCountdown == 0) {
					cancel();

					// Bukkit Command for Breaking the Glass
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "");
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule doWeatherCycle false");
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule doDaylightCycle false");
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "time set day");

					switchState(GameStates.GAME_START);
				}

				if (tutCountdown == 24) {
					// Page 1
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw @a [\"\",{\"text\":\"--------------------------------------\",\"bold\":true,\"color\":\"gold\"},\"\\n\",\"\\n\",{\"text\":\"Welcome to BlockRush!\",\"bold\":true,\"italic\":true,\"color\":\"dark_purple\"},\"\\n\",{\"text\":\"In this game, players have 5 lives.\",\"bold\":true,\"italic\":true,\"color\":\"red\"},\"\\n\",{\"text\":\"With the help of blocks & special items,\",\"bold\":true,\"italic\":true,\"color\":\"red\"},\"\\n\",{\"text\":\"be the last one standing.\",\"bold\":true,\"italic\":true,\"color\":\"red\"},\"\\n\",\"\\n\",{\"text\":\"--------------------------------------\",\"bold\":true,\"color\":\"gold\"}]");
				}

				if (tutCountdown == 17) {
					// Page 2
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw @a [\"\",{\"text\":\"--------------------------------------\",\"bold\":true,\"color\":\"gold\"},\"\\n\",\"\\n\",{\"text\":\"Players get special items every 30 seconds.\",\"bold\":true,\"italic\":true,\"color\":\"dark_purple\"},\"\\n\",{\"text\":\"Such items may include fireballs &\",\"bold\":true,\"italic\":true,\"color\":\"red\"},\"\\n\",{\"text\":\"TNT, which auto explodes! You also have\",\"bold\":true,\"italic\":true,\"color\":\"red\"},\"\\n\",{\"text\":\"wool and shears to build up and protect yourself!\",\"bold\":true,\"italic\":true,\"color\":\"red\"},\"\\n\",\"\\n\",{\"text\":\"--------------------------------------\",\"bold\":true,\"color\":\"gold\"}]");
				}

				if (tutCountdown == 10) {
					// Page 3
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw @a [\"\",{\"text\":\"--------------------------------------\",\"bold\":true,\"color\":\"gold\"},\"\\n\",\"\\n\",{\"text\":\"Good luck!\",\"bold\":true,\"italic\":true,\"color\":\"dark_purple\"},\"\\n\",{\"text\":\"They are build barriers as well, so be careful!\",\"bold\":true,\"italic\":true,\"color\":\"red\"},\"\\n\",{\"text\":\"This is the new journey, tribute.\",\"bold\":true,\"italic\":true,\"color\":\"red\"},\"\\n\",{\"text\":\"Build, clutch and win.\",\"bold\":true,\"italic\":true,\"color\":\"red\"},\"\\n\",\"\\n\",{\"text\":\"--------------------------------------\",\"bold\":true,\"color\":\"gold\"}]");
					for(Player player : Bukkit.getOnlinePlayers()) {
						player.sendMessage(ChatColor.RED+ "BlockRush begins in 10 seconds.");
					}
				}

				if (tutCountdown == 3) {
					for (Player player : Bukkit.getOnlinePlayers()) {
						player.sendTitle(ChatColor.WHITE + "3", ChatColor.GOLD + "Game begins in...", 0, 20, 0);
					}
				}

				if (tutCountdown == 2) {
					for (Player player : Bukkit.getOnlinePlayers()) {
						player.sendTitle(ChatColor.WHITE + "2", ChatColor.GOLD + "Game begins in...", 0, 20, 0);
					}
				}

				if (tutCountdown == 1) {
					for (Player player : Bukkit.getOnlinePlayers()) {
						player.sendTitle(ChatColor.WHITE + "1", ChatColor.GOLD + "Game begins in...", 0, 20, 0);
					}
				}
			}

		}.runTaskTimer(plugin, 0, 20);
	}

	private void giveRandomItems() {
		new BukkitRunnable() {

			@Override
			public void run() {
				if (currentGameState.equals(GameStates.GAME_START)) {
					Collections.shuffle(randomItems);
					for (Player player : Bukkit.getOnlinePlayers()) {
						player.getInventory().addItem(randomItems.get(0));
						player.sendMessage(ChatColor.RED + "[➤] " + ChatColor.GOLD + "All players got a special item!");
					}
				} else {
					cancel();
				}
			}

		}.runTaskTimer(plugin, 0, 600);
	}
	
	public GameStates getCurrentState() {
		return this.currentGameState;
	}
}
