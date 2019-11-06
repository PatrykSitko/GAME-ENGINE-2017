package game;

import java.awt.Dimension;

import javax.swing.JFrame;

import game.gfx.drawingSurface.DrawingSurface;
import game.input.InputManager;
import game.states.StateManager;
import game.timers.Timer;

public class GameWindow implements Runnable {

    private static final int FPS = 144;
    private static JFrame frame;
    private static DrawingSurface drawingSurface;
    private static InputManager inputManager;
    private static double timePerTick, delta;
    private static Thread gameLoop;
    private static Timer refreshTimer;

    private static String gameWindowTitle;

    public GameWindow(String title) {
        super();
        gameWindowTitle = title;
        initComponents();
    }

    private void initComponents() {
        frame = new JFrame(gameWindowTitle);
        frame.add(drawingSurface = new DrawingSurface());
        inputManager = new InputManager();
        frame.setFocusable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920, 1080);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setUndecorated(false);

        timePerTick = 1_000_000_000 / FPS;

        refreshTimer = new Timer();

        drawingSurface.addInputListener(inputManager);
        drawingSurface.addMouseMotionListener(inputManager);
        drawingSurface.addKeyListener(inputManager);
        StateManager.MAIN_MENU.set();
    }

    public void setSize(int width, int height) {
        Dimension windowSize = new Dimension(width, height);
        frame.setSize(width, height);
        frame.setPreferredSize(windowSize);
        frame.setMinimumSize(windowSize);
        frame.setMaximumSize(windowSize);
        drawingSurface.setSize(width, height);
        drawingSurface.setPreferredSize(windowSize);
        drawingSurface.setMinimumSize(windowSize);
        drawingSurface.setMaximumSize(windowSize);
    }

    private boolean timeToRefresh() {
        refreshTimer.update();
        delta += refreshTimer.getTime() / timePerTick;
        if (delta >= 1) {
            delta -= 1;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void run() {
        while (true) {
            StateManager.drawOn(drawingSurface);
            if (timeToRefresh()) {
                StateManager.update();
            }
        }
    }

    public void start() {
        gameLoop = new Thread(this);
        gameLoop.setName("gameLoop");
        frame.pack();
        frame.setVisible(true);
        gameLoop.start();
    }

    public static String getTitle() {
        return gameWindowTitle;
    }

    public static int getWidth() {
        return frame.getWidth();
    }

    public static int getHeight() {
        return frame.getHeight();
    }

    public static InputManager getInputManager() {
        return inputManager;
    }

    public static int getFPS() {
        return FPS;
    }

    public static JFrame getFrame() {
        return frame;
    }
}
