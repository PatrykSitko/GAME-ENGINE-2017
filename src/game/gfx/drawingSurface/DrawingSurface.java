package game.gfx.drawingSurface;

import java.awt.*;
import java.awt.image.BufferStrategy;

import game.GameWindow;
import game.input.InputManager;
import game.states.State;

public class DrawingSurface extends Canvas {

    private static final long serialVersionUID = 1L;

    private BufferStrategy bufferStrategy;
    private Graphics2D g;

    public DrawingSurface() {
        setBackground(Color.BLUE);
        setFocusable(true);
    }

    public void draw(State state) {
        bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(4);
            return;
        }
        g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, GameWindow.getWidth(), GameWindow.getHeight());
        state.draw(g);

        bufferStrategy.show();
        g.dispose();
    }

    public void addInputListener(InputManager inputManager) {
        addMouseListener(inputManager);
        addMouseMotionListener(inputManager);
        addKeyListener(inputManager);
    }

    public void clearInputListeners(InputManager inputManager) {
        inputManager.deleteObservers();
    }

}
