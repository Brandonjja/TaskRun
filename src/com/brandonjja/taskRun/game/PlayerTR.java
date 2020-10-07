package com.brandonjja.taskRun.game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.tasks.ScoreboardTR;
import com.brandonjja.taskRun.tasks.TR_Task;

public class PlayerTR {
	private Player player;
	private List<TR_Task> taskList;
	private int totalTasksCompleted = 0;
	private ScoreboardTR board;
	private boolean hasScoreboard = true;
	
	public PlayerTR(Player player) {
		this.player = player;
	}
	
	public PlayerTR(Player player, List<TR_Task> taskList) {
		this.player = player;
		this.totalTasksCompleted = 0;
		this.hasScoreboard = true;
		setTaskList(taskList);
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void updatePlayer(Player player) {
		this.player = player;
	}
	
	public void setTaskList(List<TR_Task> taskList) {
		this.taskList = new ArrayList<>();
		for (TR_Task task : taskList) {
			TR_Task newTask = new TR_Task(task);
			this.taskList.add(newTask);
		}
		resetTasksCompleted();
	}
	
	public String getTaskListString() {
		if (taskList == null) {
			player.sendMessage(ChatColor.RED + "Error");
			return "null";
		}
		StringBuilder sb = new StringBuilder(ChatColor.GOLD + "---------- Tasks ----------\n");
		int ctr = 0;
		for (TR_Task task : taskList) {
			if (task.isComplete()) {
				sb.append(ChatColor.RED).append(++ctr).append(". ").append(task.toString()).append("\n");
				continue;
			}
			sb.append(ChatColor.YELLOW).append(++ctr).append(". ").append(task.toString()).append("\n");
		}
		return sb.toString();
	}
	
	public void finishTask() {
		totalTasksCompleted++;
	}
	
	public void checkEndGame() {
		if (totalTasksCompleted == TaskRun.currentGame.getTotalTasksToFinish()) {
			TaskRun.currentGame.triggerEndGame(player);
		}
	}
	
	public int getFinishedTasks() {
		return totalTasksCompleted;
	}
	
	private void resetTasksCompleted() {
		totalTasksCompleted = 0;
	}
	
	public List<TR_Task> getTaskList() {
		return taskList;
	}
	
	public void completeTask(int id) {
		for (TR_Task task : taskList) {
			if (task.getTaskID() == id) {
				task.completeTask(player, id);
				break;
			}
		}
	}
	
	public void removeTaskProgress(int id, int howMuch) {
		for (TR_Task task : taskList) {
			if (task.getTaskID() == id) {
				task.removeTaskProgress(player, id, howMuch);
				break;
			}
		}
	}
	
	public void setNewScoreboard() {
		this.board = new ScoreboardTR(this);
	}
	
	public ScoreboardTR getBoard() {
		return board;
	}
	
	public void updateScoreboard(TR_Task task, int oldScore) {
		board.updateTask(task, oldScore);
	}
	
	public boolean getHasScoreboard() {
		return hasScoreboard;
	}
	
	public void toggleScoreboard() {
		hasScoreboard = !hasScoreboard;
	}
}
