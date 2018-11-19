package uet.oop.bomberman.entities.character.enemy.ai;
import java.util.*;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.level.Coordinates;

public class AStar {
	public static final int DIAGONAL_COST = 14;
	public static final int V_H_COST = 10;

	static class Cell {
		int heuristicCost = 0; // Heuristic cost
		int finalCost = 0; // G+H
		int i, j;
		Cell parent;

		Cell(int i, int j) {
			this.i = i;
			this.j = j;
		}

		@Override
		public String toString() {
			return "[" + this.i + ", " + this.j + "]";
		}
	}

	// Blocked cells are just null Cell values in grid
	static Cell[][] grid;

	static PriorityQueue<Cell> open;

	static boolean closed[][];
	static int startI, startJ;
	static int endI, endJ;

	public static void setBlocked(int i, int j) {
		grid[i][j] = null;
	}

	public static void setStartCell(int i, int j) {
		startI = i;
		startJ = j;
	}

	public static void setEndCell(int i, int j) {
		endI = i;
		endJ = j;
	}

	static void checkAndUpdateCost(Cell current, Cell t, int cost) {
		if (t == null || closed[t.i][t.j])
			return;
		int t_final_cost = t.heuristicCost + cost;

		boolean inOpen = open.contains(t);
		if (!inOpen || t_final_cost < t.finalCost) {
			t.finalCost = t_final_cost;
			t.parent = current;
			if (!inOpen)
				open.add(t);
		}
	}

	public static void AStar() {

		// add the start location to open list.
		open.add(grid[startI][startJ]);

		Cell current;

		while (true) {
			current = open.poll();
			if (current == null)
				break;
			closed[current.i][current.j] = true;

			if (current.equals(grid[endI][endJ])) {
				return;
			}

			Cell t;
			if (current.i - 1 >= 0) {
				t = grid[current.i - 1][current.j];
				checkAndUpdateCost(current, t, current.finalCost + V_H_COST);
			}

			if (current.j - 1 >= 0) {
				t = grid[current.i][current.j - 1];
				checkAndUpdateCost(current, t, current.finalCost + V_H_COST);
			}

			if (current.j + 1 < grid[0].length) {
				t = grid[current.i][current.j + 1];
				checkAndUpdateCost(current, t, current.finalCost + V_H_COST);
			}

			if (current.i + 1 < grid.length) {
				t = grid[current.i + 1][current.j];
				checkAndUpdateCost(current, t, current.finalCost + V_H_COST);
			}

		}
	}

	public static int getResult(int x, int y, int ei, int ej, Board _board) {

		Bomber bomber = _board.getBomber();
		grid = new Cell[x][y];
		closed = new boolean[x][y];
		open = new PriorityQueue<>((Object o1, Object o2) -> {
			Cell c1 = (Cell) o1;
			Cell c2 = (Cell) o2;

			return c1.finalCost < c2.finalCost ? -1 : c1.finalCost > c2.finalCost ? 1 : 0;
		});
		// Set start position

		int si = Coordinates.pixelToTile(bomber.getX());
		int sj = Coordinates.pixelToTile(bomber.getY() - Game.TILES_SIZE);
		setStartCell(si,sj); // Setting to 0,0 by default. Will be useful for the UI part

		// Set End Location
		setEndCell(ei, ej);

		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				grid[i][j] = new Cell(i, j);
				grid[i][j].heuristicCost = Math.abs(i - endI) + Math.abs(j - endJ);
			}
		}
		grid[si][sj].finalCost = 0;

		/*
		 * Set blocked cells. Simply set the cell values to null for blocked cells.
		 */
		
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				if (! _board.getEntity(i, j,null).getCollide(bomber) && !(i == si && j == sj) )
				{
					setBlocked(i, j);
				}
			}
		}
		
		AStar();
		
		if (closed[endI][endJ]) {
			// Trace back the path
			Cell current = grid[endI][endJ];
			Cell parent = current.parent;
			if (current == null || parent == null)
				return -1;
			if (current.i < parent.i)
				return 1;
			else if (current.i > parent.i)
				return 3;
			else if (current.j < parent.j)
				return 2;
			else if (current.j > parent.j)
				return 0;
		} 
		return -1;
	}
}