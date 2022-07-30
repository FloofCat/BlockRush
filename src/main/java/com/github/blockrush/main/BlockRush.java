package com.github.blockrush.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.blockrush.commands.BRCommands;
import com.github.blockrush.events.BlockPlace;
import com.github.blockrush.events.FireballLaunch;
import com.github.blockrush.events.MiscEvents;
import com.github.blockrush.events.OnJoin;
import com.github.blockrush.events.OnMove;
import com.github.blockrush.events.PrimeTNT;
import com.github.blockrush.game.Game;
import com.github.blockrush.player.KitController;
import com.github.blockrush.player.PlayerDataController;
import com.github.blockrush.player.RespawnController;
import com.github.blockrush.scoreboards.GameScoreboard;

public class BlockRush extends JavaPlugin{
	
	private PlayerDataController playerDataController = new PlayerDataController(this);
	private KitController kitController = new KitController();
	private GameScoreboard gameScoreboard = new GameScoreboard(this, playerDataController);
	private Game game = new Game(this, playerDataController, kitController, gameScoreboard);
	private RespawnController respawnController = new RespawnController(playerDataController, gameScoreboard, kitController, game);
	@Override
	public void onEnable() {
		// Commands
		this.enableCommands();
		
		// Events
		this.enableEvents();
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public BlockRush getInstance() {
		return JavaPlugin.getPlugin(BlockRush.class);
	}
	
	public Game getGame() {
		return this.game;
	}
	
	private void enableCommands() {
		this.getCommand("opme").setExecutor(new BRCommands(game));
		this.getCommand("startblockrush").setExecutor(new BRCommands(game));
	}
	
	private void enableEvents() {
		Bukkit.getPluginManager().registerEvents(new BlockPlace(this), this);
		Bukkit.getPluginManager().registerEvents(new FireballLaunch(), this);
		Bukkit.getPluginManager().registerEvents(new PrimeTNT(), this);
		Bukkit.getPluginManager().registerEvents(new MiscEvents(), this);
		Bukkit.getPluginManager().registerEvents(new OnMove(this.respawnController), this);
		Bukkit.getPluginManager().registerEvents(new OnJoin(this, this.playerDataController), this);
	}
}
