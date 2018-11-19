package uet.oop.bomberman.entities.character.enemy.ai;

public class AIDoll extends AI {

	@Override
	public int calculateDirection() {
		// TODO: cài đặt thuật toán tìm đường đi
		int result =  random.nextInt(2);
			return result * 2 + 1;
	}

}
