package com.github.blockrush.scoreboards;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.github.blockrush.game.Game;
import com.github.blockrush.game.GameStates;
import com.github.blockrush.main.BlockRush;
import com.github.blockrush.player.PlayerDataController;

import net.md_5.bungee.api.ChatColor;

public class GameScoreboard {

	private ScoreboardManager manager;
	private Scoreboard scoreboard;
	private Objective objective;
	private BlockRush plugin;
	private PlayerDataController playerDataController;
	private String lastCount = ChatColor.WHITE + "00:00";
	private Integer timer = 0;
	private Game game;
	private HashMap<Player, Integer> scoreNumber = new HashMap<>();
	public GameScoreboard(BlockRush main, PlayerDataController playerData) {
		this.plugin = main;
		this.playerDataController = playerData;
		this.game = this.plugin.getGame();
	}
	
	@SuppressWarnings("deprecation")
	public void init() {
		this.manager = Bukkit.getScoreboardManager();
		this.scoreboard = this.manager.getNewScoreboard();
		this.objective = this.scoreboard.registerNewObjective("br-gameobj", "dummy");
	}
	
	public void enableScoreboard() {
		this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		this.objective.setDisplayName(ChatColor.BOLD + "" + ChatColor.GOLD + "BlockRush");
		
		Score score1 = this.objective.getScore(ChatColor.BOLD + "" + ChatColor.WHITE + "Time Elapsed:            ");	
		Score score2 = this.objective.getScore(ChatColor.BOLD + "" + ChatColor.AQUA + "Lives:");
		
		int countdown = 7;
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			Score score = this.objective.getScore("  " + this.playerDataController.getPlayerColor(player) + player.getName() + ": " + ChatColor.WHITE + this.playerDataController.getLives(player));
			this.scoreNumber.put(player, countdown);
			score.setScore(countdown);
			countdown--;
		}
		
		score1.setScore(10);
		this.startTimer();
		score2.setScore(8);
	
		for(Player players : Bukkit.getOnlinePlayers()) {
			players.setScoreboard(this.scoreboard);
		}
	}
	
	public void updateLives(int oldVal, int newVal, Player player) {
		this.scoreboard.resetScores("  " + this.playerDataController.getPlayerColor(player) + player.getName() + ": " + ChatColor.WHITE + oldVal);
		
		Score score = this.objective.getScore("  " + this.playerDataController.getPlayerColor(player) + player.getName() + ": " + ChatColor.WHITE + newVal);
		score.setScore(this.scoreNumber.get(player));
		
		for(Player players : Bukkit.getOnlinePlayers()) {
			players.setScoreboard(this.scoreboard);
		}
	}
	
	private void startTimer() {
		new BukkitRunnable() {

			@Override
			public void run() {
				timer++;
				Score score = objective.getScore(ChatColor.WHITE + secToMin(timer));
				scoreboard.resetScores(lastCount);
				score.setScore(9);
				
				lastCount = secToMin(timer);
				
				if(!game.getCurrentState().equals(GameStates.GAME_START)) {
					cancel();
				}
			}
			
		}.runTaskTimer(plugin, 0, 20);
	}
	
	private String secToMin(int i) {

        int ms = i / 60;
        int ss = i % 60;
        String m = (ms < 10 ? "0" : "") + ms;
        String s = (ss < 10 ? "0" : "") + ss;
        String f = m + ":" + s;
        return f;
    }
	
	
}
