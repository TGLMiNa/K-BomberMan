package uet.oop.bomberman.entities.character;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.level.Coordinates;
import uet.oop.bomberman.level.FileLevelLoader;
import uet.oop.bomberman.music.BomberMusic;

import java.util.Iterator;
import java.util.List;

public class Bomber extends Character {

	private List<Bomb> _bombs;
	protected Keyboard _input;
	private BomberMusic Music = new BomberMusic();

	/**
	 * nếu giá trị này < 0 thì cho phép đặt đối tượng Bomb tiếp theo, cứ mỗi lần đặt
	 * 1 Bomb mới, giá trị này sẽ được reset về 0 và giảm dần trong mỗi lần update()
	 */
	protected int _timeBetweenPutBombs = 0;

	public Bomber(int x, int y, Board board) {
		super(x, y, board);
		_bombs = _board.getBombs();
		_input = _board.getInput();
		_sprite = Sprite.player_right;
	}

	@Override
	public void update() {
		clearBombs();
		if (!_alive) {
			afterKill();
			return;
		}

		if (_timeBetweenPutBombs < -7500)
			_timeBetweenPutBombs = 0;
		else
			_timeBetweenPutBombs--;

		animate();

		calculateMove();

		detectPlaceBomb();
	}

	@Override
	public void render(Screen screen) {
		calculateXOffset();

		if (_alive)
			chooseSprite();
		else
			_sprite = Sprite.player_dead1;

		screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);
	}

	public void calculateXOffset() {
		int xScroll = Screen.calculateXOffset(_board, this);
		Screen.setOffset(xScroll, 0);
	}

	/**
	 * Kiểm tra xem có đặt được bom hay không? nếu có thì đặt bom tại vị trí hiện
	 * tại của Bomber
	 */
	private void detectPlaceBomb() {
		// TODO: kiểm tra xem phím điều khiển đặt bom có được gõ và giá trị
		// _timeBetweenPutBombs, Game.getBombRate() có thỏa mãn hay không
		// TODO: Game.getBombRate() sẽ trả về số lượng bom có thể đặt liên tiếp tại thời
		// điểm hiện tại
		// TODO: _timeBetweenPutBombs dùng để ngăn chặn Bomber đặt 2 Bomb cùng tại 1 vị
		// trí trong 1 khoảng thời gian quá ngắn
		// TODO: nếu 3 điều kiện trên thỏa mãn thì thực hiện đặt bom bằng placeBomb()
		// TODO: sau khi đặt, nhớ giảm số lượng Bomb Rate và reset _timeBetweenPutBombs
		// về 0
		if (_input.space == true && Game.getBombRate() > 0 && _timeBetweenPutBombs < 0) {
			placeBomb((int) _x, (int) _y);
			Game.addBombRate(-1);
			_timeBetweenPutBombs = 25;
		}
	}

	protected void placeBomb(int x, int y) {
		// TODO: thực hiện tạo đối tượng bom, đặt vào vị trí (x, y)
		Music.putBomb();
		Bomb b = new Bomb(Coordinates.pixelToTile(x + _sprite.getSize() / 2),
				Coordinates.pixelToTile(y - Game.TILES_SIZE / 2), _board);
		_bombs.add(b);
	}

	private void clearBombs() {
		Iterator<Bomb> bs = _bombs.iterator();

		Bomb b;
		while (bs.hasNext()) {
			b = bs.next();
			if (b.isRemoved()) {
				bs.remove();
				Game.addBombRate(1);
			}
		}

	}

	@Override
	public void kill() {
		if (!_alive)
			return;
		Music.bomberDeath();
		_alive = false;
	}

	@Override
	protected void afterKill() {
		if (_timeAfter > 0)
			--_timeAfter;
		else {
			_board.endGame();
		}
	}

	@Override
	protected void calculateMove() {
		// TODO: xử lý nhận tín hiệu điều khiển hướng đi từ _input và gọi move() để thực
		// hiện di chuyển
		// TODO: nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển
		int x = 0, y = 0;
		if (_input.down == true)
			y++;
		if (_input.up == true)
			y--;
		if (_input.right == true)
			x++;
		if (_input.left == true)
			x--;

		if (x != 0 || y != 0) {
			_moving = true;
			move(x * Game.getBomberSpeed(), y * Game.getBomberSpeed());
		} else
			_moving = false;
	}

	@Override
	public boolean canMove(double x, double y) {
		// // TODO: kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di
		// // chuyển tới đó hay không
		// System.out.println(_board.getEntity(13, 1, null));
		// double x1 = _x;
		// double y1 = _y;
		// x = x1 + x;
		// y = y1 + y;
		// if ( _direction == 0) {
		// double xt = (x1) / Game.TILES_SIZE;
		// double yt = ((y1) - Game.TILES_SIZE) / Game.TILES_SIZE;
		// if (xt - (int) xt >= 0.2) {
		// if (!(_board.getEntity(xt - 1, yt - 1, this).collide(this))) {
		// x = ((int) x / Game.TILES_SIZE * Game.TILES_SIZE) + Game.TILES_SIZE;
		//
		// }
		// }
		//
		// }
		//
		// if (_direction == 3) {
		// double xt = (x1) / Game.TILES_SIZE;
		// double yt = ((y1) - Game.TILES_SIZE) / Game.TILES_SIZE;
		// if (yt - (int) yt >= 0.2) {
		// if (!(_board.getEntity(xt - 1, yt + 1, this).collide(this))) {
		// y = ((int) y / Game.TILES_SIZE * Game.TILES_SIZE) + Game.TILES_SIZE;
		// }
		// }
		//
		// }
		//
		// if (_direction == 2) {
		// double xt = (x1) / Game.TILES_SIZE;
		// double yt = ((y1) - Game.TILES_SIZE) / Game.TILES_SIZE;
		// if (xt - (int) xt >= 0.2) {
		// if (!(_board.getEntity(xt + 1, yt + 1, this).collide(this))) {
		// x = ((int) x / Game.TILES_SIZE * Game.TILES_SIZE) + Game.TILES_SIZE;
		//
		// }
		// }
		// if (y > ((int) y / Game.TILES_SIZE * Game.TILES_SIZE)) {
		// y = ((int) y / Game.TILES_SIZE * Game.TILES_SIZE) + Game.TILES_SIZE;
		// }
		// }
		//
		// if (_direction == 1) {
		// double xt = (x1) / Game.TILES_SIZE;
		// double yt = ((y1) - Game.TILES_SIZE) / Game.TILES_SIZE;
		// if (yt - (int) yt >= 0.2) {
		// if (!(_board.getEntity(xt + 1, yt + 1, this).collide(this))) {
		// y = ((int) y / Game.TILES_SIZE * Game.TILES_SIZE) + Game.TILES_SIZE;
		// }
		// }
		// if (x > ((int) x / Game.TILES_SIZE * Game.TILES_SIZE)) {
		// x = ((int) x / Game.TILES_SIZE * Game.TILES_SIZE) + Game.TILES_SIZE;
		// }
		// }
		//
		// // System.out.println(y + " " + x);
		// // System.out.println("------------------------------");
		// // if (FileLevelLoader._map[Coordinates.pixelToTile(y -
		// // Game.TILES_SIZE)][Coordinates.pixelToTile(x)] == ' '
		// // || FileLevelLoader._map[Coordinates.pixelToTile(y -
		// // Game.TILES_SIZE)][Coordinates
		// // .pixelToTile(x)] == 'p')
		// // return true;
		// // return false;
		// double xt = (x) / Game.TILES_SIZE;
		// double yt = ((y) - Game.TILES_SIZE) / Game.TILES_SIZE;
		//
		// Entity a = _board.getEntity(xt, yt, this);
		// if (!a.collide(this))
		// return false;
		// return true;
		Entity up = _board.getEntity((_x + x) / Game.TILES_SIZE, ((_y + y) - 13) / Game.TILES_SIZE, this);
		Entity right = _board.getEntity((_x + x + 11) / Game.TILES_SIZE, ((_y + y) - 13) / Game.TILES_SIZE, this);
		Entity down = _board.getEntity((_x + x) / Game.TILES_SIZE, ((_y + y) - 1) / Game.TILES_SIZE, this);
		Entity left = _board.getEntity((_x + x + 11) / Game.TILES_SIZE, ((_y + y) - 1) / Game.TILES_SIZE, this);
		return up.collide(this) && right.collide(this) && down.collide(this) && left.collide(this);

	}

	@Override
	public void move(double xa, double ya) {
		// TODO: sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính
		// toán hay không và thực hiện thay đổi tọa độ _x, _y
		// TODO: nhớ cập nhật giá trị _direction sau khi di chuyển
		if (ya < 0)
			_direction = 0;
		if (ya > 0)
			_direction = 2;
		if (xa > 0)
			_direction = 1;
		if (xa < 0)
			_direction = 3;

		if (canMove(xa, ya)) {
			_y += ya;
			_x += xa;
		}

		// if (canMove(xa, 0)) {
		// _x += xa;
		// }
	}

	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý va chạm với Flame
		// TODO: xử lý va chạm với Enemy
		if (e instanceof Flame || e instanceof Enemy) {
			kill();
		}
		return true;
	}
	@Override
	public boolean getCollide(Entity e) {
		// TODO: xử lý va chạm với Flame
		// TODO: xử lý va chạm với Enemy
		if (e instanceof Flame || e instanceof Enemy) {
			return false;
		}
		return true;
	}

	private void chooseSprite() {
		switch (_direction) {
		case 0:
			_sprite = Sprite.player_up;
			if (_moving) {
				_sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, _animate, 20);
			}
			break;
		case 1:
			_sprite = Sprite.player_right;
			if (_moving) {
				_sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
			}
			break;
		case 2:
			_sprite = Sprite.player_down;
			if (_moving) {
				_sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, _animate, 20);
			}
			break;
		case 3:
			_sprite = Sprite.player_left;
			if (_moving) {
				_sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, _animate, 20);
			}
			break;
		default:
			_sprite = Sprite.player_right;
			if (_moving) {
				_sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
			}
			break;
		}
	}
}
