package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class FlameItem extends Item {

	public FlameItem(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}

	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý Bomber ăn Item
		if (e instanceof Bomber)
		{
			if (getted == false)
			{
				Music.getItem();
				Game.addBombRadius(1);
				this.remove();
				getted = true;
			}
			return true;
		}
		return false;
	}
	
	@Override
	public boolean getCollide(Entity e) {
		// TODO: xử lý Bomber ăn Item
		if (e instanceof Bomber)
		{
			return true;
		}
		return false;
	}

}
