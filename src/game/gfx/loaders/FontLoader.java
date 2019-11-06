package game.gfx.loaders;

import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.io.File;
import java.io.IOException;

public interface FontLoader {
	static final Component component = new Component() {
		private static final long serialVersionUID = 1L;
	};

	public static Font loadFont(String path, float size) {
		try {
			return Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(Font.PLAIN, size);
		} catch (FontFormatException | IOException e) {
			System.exit(1);
		}
		return null;
	}

	public static FontMetrics getFontMetrics(Font font) {
		return component.getFontMetrics(font);
	}
}
