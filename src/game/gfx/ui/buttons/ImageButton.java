package game.gfx.ui.buttons;

import game.gfx.ui.ClickListener;
import java.awt.image.BufferedImage;

import game.shapes.Shape;
import game.shapes.Shape.Geometry;
import game.sound.SoundPlayer;
import java.awt.Graphics2D;

public class ImageButton extends Button {

    private BufferedImage[] images;

    public ImageButton(Geometry.Shapes s, BufferedImage[] images, ClickListener clickListener) {
        super(s, clickListener, SoundPlayer.BUTTON_HOVERING, SoundPlayer.BUTTON_PLAY);
        this.images = images;
    }

    @Override
    public void draw(Graphics2D g) {
        int x, y, width, height;
        if (shape.getShape() == Shape.Geometry.Shapes.CIRCULAR) {
            int radius = shape.get(Geometry.WIDTH);
            x = shape.get(Geometry.X) - radius;
            y = shape.get(Geometry.Y) - radius;
            height = width = radius * 2;
        } else {
            x = shape.get(Geometry.X);
            y = shape.get(Geometry.Y);
            width = shape.get(Geometry.WIDTH);
            height = shape.get(Geometry.HEIGHT);
        }
        if (shape.hasContained() && !changeContentOnClick) {
            g.drawImage(images[1], x, y, width, height, null);
        } else if (forceContentChange) {
            g.drawImage(images[1], x, y, width, height, null);
        } else {
            g.drawImage(images[0], x, y, width, height, null);
        }
    }

}
