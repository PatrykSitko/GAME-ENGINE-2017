package game.map.objects.entities.player;

import game.input.InputAction;
import game.map.bounds.MapBounds;
import game.map.objects.MapObject;
import game.shapes.Shape.Geometry;
import game.states.StateManager;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

public class Player extends MapObject implements Observer {

    private BufferedImage player;
    private InputAction lastInputAction;

    public Player(BufferedImage player, int x, int y, MapBounds mapBounds) {
        super(mapBounds);
        shape.setShape(Geometry.Shapes.RECTANGULAR);
        this.player = player;
        shape.set(Geometry.X, x);
        shape.set(Geometry.Y, y);
        shape.set(Geometry.WIDTH, 25);
        shape.set(Geometry.HEIGHT, 75);
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(player, shape.get(Geometry.X), shape.get(Geometry.Y), shape.get(Geometry.WIDTH), shape.get(Geometry.HEIGHT), null);
    }

    @Override
    public void update(Observable o, Object arg) {
        int previousX = shape.get(Geometry.X),
                previousY = shape.get(Geometry.Y);
        if (arg instanceof InputAction) {
            lastInputAction = (InputAction) arg;
        }
        if (arg instanceof KeyEvent && lastInputAction.equals(InputAction.KEY_PRESSED)) {
            switch (((KeyEvent) arg).getExtendedKeyCode()) {
                default:
                    break;
                case KeyEvent.VK_W:
                    shape.set(Geometry.Y, shape.get(Geometry.Y) - 1);
                    break;
                case KeyEvent.VK_S:
                    shape.set(Geometry.Y, shape.get(Geometry.Y) + 1);
                    break;
                case KeyEvent.VK_A:
                    shape.set(Geometry.X, shape.get(Geometry.X) - 1);
                    break;
                case KeyEvent.VK_D:
                    shape.set(Geometry.X, shape.get(Geometry.X) + 1);
                    break;
                case KeyEvent.VK_ESCAPE:
                    StateManager.MAIN_MENU.set();
                    break;
            }
            if (getMapBounds().collides(shape)) {
                shape.set(Geometry.X, previousX).set(Geometry.Y, previousY);
            }
        }
    }

}
