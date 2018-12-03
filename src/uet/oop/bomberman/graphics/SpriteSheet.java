package uet.oop.bomberman.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Tất cả sprite (hình ảnh game) được lưu trữ vào một ảnh duy nhất
 * Class này giúp lấy ra các sprite riêng từ 1 ảnh chung duy nhất đó
 */
public class SpriteSheet {

	private String _path;
	public final int SIZE;
	public int[] _pixels;
	
	public static SpriteSheet tiles = new SpriteSheet("/textures/classic.png", 256);
	
	public static SpriteSheet bomber = new SpriteSheet("/textures/bomber.png", 256);
	
	public static SpriteSheet texture2 = new SpriteSheet("/textures/texture2.png", 256);
	
	public SpriteSheet(String path, int size) {
		_path = path;
		SIZE = size;
		_pixels = new int[SIZE * SIZE];
		load();
	}	
	private void load() {
		try {
			URL a = SpriteSheet.class.getResource(_path);
			BufferedImage image = ImageIO.read(a);
			int w = 256;
			int h = 256;
			image.getRGB(0, 0, w, h, _pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
