package uet.oop.bomberman.entities.character.enemy;


import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.enemy.ai.AIHigh;
import uet.oop.bomberman.entities.character.enemy.ai.AIMedium;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

public class Oneal extends Enemy {
	
	public Oneal(int x, int y, Board board) {
		super(x, y, board, Sprite.oneal_dead, Game.getBomberSpeed(), 200);
		
		_sprite = Sprite.oneal_left1;
		
		_ai = new AIHigh(_board.getBomber(), this,_board);
		_direction  = _ai.calculateDirection();
	}
	
	@Override
	protected void chooseSprite() {
		switch(_direction) {
			case 0:
			case 1:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, _animate, 60);
				else
					_sprite = Sprite.oneal_left1;
				break;
			case 2:
			case 3:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, _animate, 60);
				else
					_sprite = Sprite.oneal_left1;
				break;
		}
	}
	public boolean canMove(double x, double y) {
		// TODO: kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di
		// chuyển tới đó hay không
		double xa = _x, ya = _y - 16;
		if (_direction == 0) 
		{
			xa += Game.TILES_SIZE / 2;
			ya += Game.TILES_SIZE - 1;
		}
		if (_direction == 1) 
		{
			xa += 1;
			ya += Game.TILES_SIZE / 2;
		}
		if (_direction == 2) 
		{
			xa += Game.TILES_SIZE / 2;
			ya += 1;
		}
		if (_direction == 3) 
		{
			xa += Game.TILES_SIZE - 1;
			ya += Game.TILES_SIZE /2;
		}
		
		int xx = Coordinates.pixelToTile(xa) + (int) x;
		int yy = Coordinates.pixelToTile(ya) + (int) y;
		
		if (xx <= 0 || yy <= 0 || xx >= _board._levelLoader.getWidth() || yy >= _board._levelLoader.getHeight())
			return false;
		Entity a = _board.getEntity(xx, yy, this);
		
		return a.collide(this);
	}

}
