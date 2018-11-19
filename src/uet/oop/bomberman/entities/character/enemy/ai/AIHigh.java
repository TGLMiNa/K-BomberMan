package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.level.Coordinates;

public class AIHigh extends AI {

	Bomber _bomber;
	Enemy _e;
	Board _board;
	int count = 0;
	
	public AIHigh(Bomber bomber, Enemy e, Board board) {
		_bomber = bomber;
		_e = e;
		_board = board;
	}

	@Override
	public int calculateDirection() {
		count++;
		if (count >= 5 )
		{
			double xa = _e.getX(), ya = _e.getY() - 16;
			if (_e.getDirection() == 0) 
			{
				xa += Game.TILES_SIZE / 2;
				ya += Game.TILES_SIZE - 1;
			}
			if (_e.getDirection() == 1) 
			{
				xa += 1;
				ya += Game.TILES_SIZE / 2;
			}
			if (_e.getDirection() == 2) 
			{
				xa += Game.TILES_SIZE / 2;
				ya += 1;
			}
			if (_e.getDirection() == 3) 
			{
				xa += Game.TILES_SIZE - 1;
				ya += Game.TILES_SIZE /2;
			}
			Entity e = null;
			int result = AStar.getResult(31, 13,  Coordinates.pixelToTile(xa), Coordinates.pixelToTile(ya) ,_board);
			if (result != - 1)
			{
				return result;
				
			}
		}
		return random.nextInt(4);
	}

}
