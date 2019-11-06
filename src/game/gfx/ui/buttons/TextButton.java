package game.gfx.ui.buttons;

import java.awt.Color;
import java.awt.Graphics2D;

import game.gfx.fonts.Fonts;
import game.gfx.ui.ClickListener;
import game.gfx.writers.Writer;
import game.shapes.Shape.Geometry;
import game.sound.SoundPlayer;

public class TextButton extends Button {

    public String text;
    private Fonts font;
    private int fontSize;
    private Color normalState;
    private Color hoveringState;

    public TextButton(Geometry.Shapes s, String text, Fonts font, int fontSize, Color normalState, Color hoveringState,
            ClickListener clickListener) {
        super(s, clickListener, SoundPlayer.BUTTON_HOVERING, SoundPlayer.BUTTON_PLAY);
        shape.setShape(s);
        this.text = text;
        this.font = font;
        this.fontSize = fontSize;
        this.normalState = normalState;
        this.hoveringState = hoveringState;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setHoveringColor(Color c) {
        hoveringState = c;
    }

    @Override
    public void draw(Graphics2D g) {
        Color c;
        if (shape.hasContained() || forceContentChange && !changeContentOnClick) {
            c = hoveringState;
        } else {
            c = normalState;
        }
        writeText(g, c);
    }

    private void writeText(Graphics2D g, Color c) {
        if (shape.get(Geometry.WIDTH) != g.getFontMetrics(font.getFont(fontSize)).stringWidth(text)) {
            shape.set(Geometry.WIDTH, g.getFontMetrics(font.getFont(fontSize)).stringWidth(text));
        }
        if (shape.get(Geometry.HEIGHT) != g.getFontMetrics(font.getFont(fontSize)).getAscent()) {
            shape.set(Geometry.HEIGHT, g.getFontMetrics(font.getFont(fontSize)).getAscent());
        }
        Writer.write(g, text, shape.get(Geometry.X), shape.get(Geometry.Y) + shape.get(Geometry.HEIGHT) / 2 + 18, false, c,
                font.getFont(fontSize));
    }
}
