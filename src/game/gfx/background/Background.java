package game.gfx.background;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.GameWindow;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Background {

    public BufferedImage image;
    public boolean fitGameWindow;

    private double x_coordinate;
    private double x_vector;

    private double y_coordinate;
    private double y_vector;

    private Integer width;
    private Integer height;

    public Background(String path) {
        this(
                path,
                false,
                0,
                0
        );
    }

    public Background(String path, boolean fitGameWindow) {
        this(
                path,
                fitGameWindow,
                0,
                0
        );
    }

    public Background(String path, boolean fitGameWindow, int x, int y) {
        this(
                path,
                fitGameWindow,
                x,
                y,
                0,
                0,
                0,
                0
        );
    }

    public Background(String path, int x, int y, int width, int height) {
        this(
                path,
                x,
                y,
                width,
                height,
                0,
                0
        );
    }

    public Background(String path, int x, int y, int width, int height, double x_vector, double y_vector) {
        this(
                path,
                false,
                x,
                y,
                width,
                height,
                x_vector,
                y_vector
        );
    }

    private Background(String path, boolean fitGameWindow, double x, double y, int width, int height, double x_vector, double y_vector) {
        try {
            image = ImageIO.read(getClass().getResourceAsStream(path));
            this.fitGameWindow = fitGameWindow;
            set(Coordinate.X, x);
            set(Coordinate.Y, y);
            set(Size.WIDTH, width);
            set(Size.HEIGHT, height);
            set(Vector.X, x_vector);
            set(Vector.Y, y_vector);
        } catch (IOException ex) {
            Logger.getLogger(Background.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }

    Background(Background background) {
        this(background.image,
                background.fitGameWindow,
                background.get(Coordinate.X),
                background.get(Coordinate.Y),
                background.get(Size.WIDTH),
                background.get(Size.HEIGHT),
                background.get(Vector.X),
                background.get(Vector.Y)
        );
    }

    public Background(Background background, boolean fitGameWindow) {
        this(
                background.image,
                fitGameWindow,
                background.get(Coordinate.X),
                background.get(Coordinate.Y),
                background.get(Size.WIDTH),
                background.get(Size.HEIGHT),
                background.get(Vector.X),
                background.get(Vector.Y)
        );
    }

    public Background(Background background, boolean fitGameWindow, int x, int y) {
        this(
                background.image,
                fitGameWindow,
                x,
                y,
                background.get(Size.WIDTH),
                background.get(Size.HEIGHT),
                background.get(Vector.X),
                background.get(Vector.Y)
        );
    }

    public Background(Background background, int x, int y, int width, int height) {
        this(
                background.image,
                background.fitGameWindow,
                x,
                y,
                width,
                height,
                background.get(Vector.X),
                background.get(Vector.Y)
        );
    }

    public Background(Background background, int x, int y, int width, int height, double x_vector, double y_vector) {
        this(
                background.image,
                background.fitGameWindow,
                x,
                y,
                width,
                height,
                x_vector,
                y_vector
        );
    }

    private Background(BufferedImage image, boolean fitGameWindow, double x, double y, int width, int height, double x_vector, double y_vector) {
        this.image = image;
        this.fitGameWindow = fitGameWindow;
        set(Coordinate.X, x);
        set(Coordinate.Y, y);
        set(Size.WIDTH, width);
        set(Size.HEIGHT, height);
        set(Vector.X, x_vector);
        set(Vector.Y, y_vector);
    }

    public final Integer get(Size s) {
        switch (s) {
            case WIDTH:
                return width;
            case HEIGHT:
                return height;
            default:
                throw new EnumConstantNotPresentException(s.getClass(), s.toString());
        }
    }

    public final Double get(Coordinate c) {
        switch (c) {
            case X:
                return x_coordinate;
            case Y:
                return y_coordinate;
            default:
                throw new EnumConstantNotPresentException(c.getClass(), c.toString());
        }
    }

    public final Double get(Vector v) {
        switch (v) {
            case X:
                return x_vector;
            case Y:
                return y_vector;
            default:
                throw new EnumConstantNotPresentException(v.getClass(), v.toString());
        }
    }

    public final Background set(Size s, int value) {
        switch (s) {
            case WIDTH:
                if ((width = value) == 0 && image != null) {
                    this.width = image.getWidth();
                }
                break;
            case HEIGHT:
                if ((height = value) == 0 && image != null) {
                    this.height = image.getHeight();
                }
                break;
            default:
                throw new EnumConstantNotPresentException(s.getClass(), s.toString());
        }
        return this;
    }

    public final Background set(Coordinate c, double value) {
        switch (c) {
            case X:
                this.x_coordinate = value % GameWindow.getWidth();
                break;
            case Y:
                this.y_coordinate = value % GameWindow.getHeight();
                break;
            default:
                throw new EnumConstantNotPresentException(c.getClass(), c.toString());
        }
        return this;
    }

    public final Background set(Vector v, double value) {
        switch (v) {
            case X:
                this.x_vector = value;
                break;
            case Y:
                this.y_vector = value;
                break;
            default:
                throw new EnumConstantNotPresentException(v.getClass(), v.toString());
        }
        return this;
    }

    public void update() {
        set(Coordinate.X, (x_coordinate += x_vector));
        set(Coordinate.Y, (y_coordinate += y_vector));
    }

    public void draw(Graphics2D g) {
        int backgroundWidth = fitGameWindow ? GameWindow.getWidth() : width, backgroundHeight = fitGameWindow ? GameWindow.getHeight() : height;
        g.drawImage(image, get(Coordinate.X).intValue(), get(Coordinate.Y).intValue(), backgroundWidth, backgroundHeight, null);
        if (get(Coordinate.X) < 0) {
            g.drawImage(image, get(Coordinate.X).intValue() + GameWindow.getWidth(), get(Coordinate.Y).intValue(), backgroundWidth, backgroundHeight, null);
        }
        if (get(Coordinate.X) > 0) {
            g.drawImage(image, get(Coordinate.X).intValue() - GameWindow.getWidth(), get(Coordinate.Y).intValue(), backgroundWidth, backgroundHeight, null);
        }
        if (get(Coordinate.Y) < 0) {
            g.drawImage(image, get(Coordinate.X).intValue(), get(Coordinate.Y).intValue() + GameWindow.getHeight(), backgroundWidth, backgroundHeight, null);
        }
        if (get(Coordinate.Y) > 0) {
            g.drawImage(image, get(Coordinate.X).intValue(), get(Coordinate.Y).intValue() - GameWindow.getHeight(), backgroundWidth, backgroundHeight, null);
        }
        if (get(Coordinate.X) < 0 && get(Coordinate.Y) < 0) {
            g.drawImage(image, get(Coordinate.X).intValue() + GameWindow.getWidth(), get(Coordinate.Y).intValue() + GameWindow.getHeight(), backgroundWidth, backgroundHeight, null);
        }
        if (get(Coordinate.X) > 0 && get(Coordinate.Y) > 0) {
            g.drawImage(image, get(Coordinate.X).intValue() - GameWindow.getWidth(), get(Coordinate.Y).intValue() - GameWindow.getHeight(), backgroundWidth, backgroundHeight, null);
        }
        if (get(Coordinate.X) < 0 && get(Coordinate.Y) > 0) {
            g.drawImage(image, get(Coordinate.X).intValue() + GameWindow.getWidth(), get(Coordinate.Y).intValue() - GameWindow.getHeight(), backgroundWidth, backgroundHeight, null);
        }
        if (get(Coordinate.X) > 0 && get(Coordinate.Y) < 0) {
            g.drawImage(image, get(Coordinate.X).intValue() - GameWindow.getWidth(), get(Coordinate.Y).intValue() + GameWindow.getHeight(), backgroundWidth, backgroundHeight, null);
        }
    }
// CLASS RELATED ENUMS:

    public static enum Coordinate {
        X, Y;
    }

    public static enum Vector {
        X, Y;
    }

    public static enum Size {
        WIDTH, HEIGHT;
    }

}
