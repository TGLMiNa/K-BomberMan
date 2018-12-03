package uet.oop.bomberman;

import uet.oop.bomberman.gui.Frame;

public class BombermanGame extends Thread {
	
	private boolean isMultiPlayer;
	public static Frame frame;
	public BombermanGame(boolean isMultiPlayer)
	{
		this.isMultiPlayer = isMultiPlayer;
	}
	public void run() {
		if (!isMultiPlayer)
			new Frame(false);
		else
			new Frame(true);
		//System.out.println(123);
	}
	public static void print()
	{
		System.out.println(frame);
	}
}
