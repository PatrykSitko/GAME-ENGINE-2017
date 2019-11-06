package game.gfx.fonts;

import java.awt.Font;
import java.util.HashMap;

import game.gfx.loaders.FontLoader;

public enum Fonts {
	FIPPS("res/fonts/fipps/Fipps-Regular.ttf"), PIXEL_ART("res/fonts/pixel_art/pixelart.ttf");
	private HashMap<Integer, Font> fonts;

	Fonts(String fontPath) {
		fonts = new HashMap<>();
		fillHashMap(fontPath);
	}

	private void fillHashMap(String fontPath) {
		for (int size = 10; size <= 500; size++)
			fonts.put(size, FontLoader.loadFont(fontPath, size));
	}

	public Font getFont(int size) {
		size = size < 10 ? 10 : size;
		size = size > 500 ? 500 : size;
		return fonts.get(size);
	}

}
