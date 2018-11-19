package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.entities.tile.Tile;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.music.EntityMusic;

public abstract class Item extends Tile {

	protected boolean getted = false;
	protected EntityMusic Music = new EntityMusic();
	public Item(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
}
