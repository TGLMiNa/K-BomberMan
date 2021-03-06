package uet.oop.bomberman.input;

import java.awt.Event;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.EventSetDescriptor;

import startingMenu.startingGame;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.character.Character;

/**
 * Tiếp nhận và xử lý các sự kiện nhập từ bàn phím
 */
public class Keyboard implements KeyListener {
	
	private boolean[] keys = new boolean[120]; //120 is enough to this game
	public boolean up, down, left, right, space, A,S,D,W,X;
	
	public void update() {
		up = keys[KeyEvent.VK_UP];
		W =  keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN];
		S = keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT];
		A = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT];
		D = keys[KeyEvent.VK_D];
		space = keys[KeyEvent.VK_SPACE];
		X = keys[KeyEvent.VK_X];
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		if (e.getKeyCode() == 27)
		{
			startingGame.menu.setVisible(true);
			Board.Music.stop1();
			BombermanGame.frame.dispose();
		}
		if (e.getKeyCode() == KeyEvent.VK_ADD)
			Character._board.nextLevel();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

}
