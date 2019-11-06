package game.gfx.writers;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public interface Writer {

	public static void write(Graphics2D g, String text, int xPos, int yPos, boolean center, Color c, Font f) {
		g.setFont(f);
		g.setColor(c);
		if (center) {
			FontMetrics fontMetrics = g.getFontMetrics();
			xPos = xPos - fontMetrics.stringWidth(text) / 2;
			yPos = (yPos - fontMetrics.getHeight() / 2) + fontMetrics.getAscent();
		}
		g.drawString(text, xPos, yPos);
	}
}
