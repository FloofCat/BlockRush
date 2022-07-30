package com.github.blockrush.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.blockrush.game.Game;
import com.github.blockrush.game.GameStates;


public class BRCommands implements CommandExecutor{

	private Game game;
	public BRCommands(Game g) {
		this.game = g;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("opme")) {
			Player player = (Player) sender;
			player.kickPlayer(ChatColor.RED + "hehe no :3");
		}
		
		if(cmd.getName().equalsIgnoreCase("startblockrush")) {
			this.game.switchState(GameStates.PREGAME);
		}
		return true;
	}

}
