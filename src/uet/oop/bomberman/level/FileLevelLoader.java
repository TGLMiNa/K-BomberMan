package uet.oop.bomberman.level;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Balloon;
import uet.oop.bomberman.entities.character.enemy.Doll;
import uet.oop.bomberman.entities.character.enemy.Kondoria;
import uet.oop.bomberman.entities.character.enemy.Minvo;
import uet.oop.bomberman.entities.character.enemy.Oneal;
import uet.oop.bomberman.entities.character.enemy.Ovape;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public class FileLevelLoader extends LevelLoader {

	/**
	 * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được từ ma
	 * trận bản đồ trong tệp cấu hình
	 */
	public static char[][] _map;

	public FileLevelLoader(Board board, int level) throws LoadLevelException {
		super(board, level);
	}

	@Override
	public void loadLevel(int level) {
		// TODO: đọc dữ liệu từ tệp cấu hình /levels/Level{level}.txt
		// TODO: cập nhật các giá trị đọc được vào _width, _height, _level, _map
		Scanner reader;
		try {
			reader = new Scanner(new File("res/levels/Level" + level +".txt"));
			_level = level;
			int num = reader.nextInt();
			_height = reader.nextInt();
			_width = reader.nextInt();
			_map = new char[_height][_width];
			String s = reader.nextLine();
			for (int i = 0; i < _height; i++) {
				s = reader.nextLine();
				for (int j = 0; j < _width; j++)
					_map[i][j] = s.charAt(j);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void createEntities() {
		// TODO: tạo các Entity của màn chơi
		// TODO: sau khi tạo xong, gọi _board.addEntity() để thêm Entity vào game

		// TODO: phần code mẫu ở dưới để hướng dẫn cách thêm các loại Entity vào game
		// TODO: hãy xóa nó khi hoàn thành chức năng load màn chơi từ tệp cấu hình
		// thêm Wall

		for (int y = 0; y < _height; y++) {
			for (int x = 0; x < _width; x++) {
				int pos = x + y * _width;
				
				//THêm wall
				if (_map[y][x] == '#')
				{
				_board.addEntity(pos, new Wall(x, y, Sprite.wall));
				}
				
				//thêm grass
				if (_map[y][x] == ' ')
				_board.addEntity(pos, new Grass(x, y, Sprite.grass));

				// thêm Bomber
				if (_map[y][x] == 'p') {
					_board.addCharacter(new Bomber(Coordinates.tileToPixel(x),
							Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
					Screen.setOffset(0, 0);
					_board.addEntity(pos, new Grass(x, y, Sprite.grass));
				}

				// thêm Enemy
				if (_map[y][x] == '1') {
					_board.addCharacter(new Balloon(Coordinates.tileToPixel(x),
							Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
					_board.addEntity(pos, new Grass(x, y, Sprite.grass));
				}

				if (_map[y][x] == '2') {
					_board.addCharacter(new Oneal(Coordinates.tileToPixel(x),
							Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
					_board.addEntity(pos, new Grass(x, y, Sprite.grass));
				}
				
				if (_map[y][x] == '3') {
					_board.addCharacter(new Doll(Coordinates.tileToPixel(x),
							Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
					_board.addEntity(pos, new Grass(x, y, Sprite.grass));
				}
				
				if (_map[y][x] == '4') {
					_board.addCharacter(new Minvo(Coordinates.tileToPixel(x),
							Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
					_board.addEntity(pos, new Grass(x, y, Sprite.grass));
				}
				
				if (_map[y][x] == '5') {
					_board.addCharacter(new Kondoria(Coordinates.tileToPixel(x),
							Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
					_board.addEntity(pos, new Grass(x, y, Sprite.grass));
				}
				
				if (_map[y][x] == '6') {
					_board.addCharacter(new Ovape(Coordinates.tileToPixel(x),
							Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
					_board.addEntity(pos, new Grass(x, y, Sprite.grass));
				}

				// thêm Brick
				if (_map[y][x] == '*') {
					_board.addEntity(pos,
							new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new Brick(x, y, Sprite.brick)));
				}

				// thêm Item kèm Brick che phủ ở trên
				if (_map[y][x] == 'b') {
					_board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass),
							new BombItem(x, y, Sprite.powerup_bombs), new Brick(x, y, Sprite.brick)));
				}

				if (_map[y][x] == 'f') {
					_board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass),
							new FlameItem(x, y, Sprite.powerup_flames), new Brick(x, y, Sprite.brick)));
				}

				if (_map[y][x] == 's') {
					_board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass),
							new SpeedItem(x, y, Sprite.powerup_speed), new Brick(x, y, Sprite.brick)));
				}
				
				//Thêm portal
				if (_map[y][x] == 'x') {
					_board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass),
							new Portal(x, y, Sprite.portal), new Brick(x, y, Sprite.brick)));
				}
			}
		}
	}

}
