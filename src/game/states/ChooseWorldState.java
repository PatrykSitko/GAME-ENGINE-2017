package game.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.GameWindow;
import game.gfx.background.Background;
import game.gfx.background.Backgrounds;
import game.gfx.fonts.Fonts;
import game.gfx.loaders.ImageLoader;
import game.gfx.ui.buttons.ImageButton;
import game.gfx.ui.buttons.TextButton;
import game.shapes.Shape.Geometry;
import game.sound.SoundPlayer;

public class ChooseWorldState implements State {

    private static int updateCounter;
    private static Background background;

    private static TextButton returnButton;

    private static ImageButton planet1;
    protected static final SoundPlayer BACKGROUND_TRACK = SoundPlayer.BACKGROUND_HAPPY;

    public ChooseWorldState() {
        returnButton
                = new TextButton(
                        Geometry.Shapes.RECTANGULAR,
                        "Return",
                        Fonts.PIXEL_ART,
                        75,
                        Color.GRAY,
                        Color.GREEN,
                        () -> {
                            SoundPlayer.BUTTON_RETURN.play();
                            StateManager.MAIN_MENU.set();
                        });
        returnButton.shape.set(Geometry.X, 20).set(Geometry.Y, GameWindow.getHeight() - 80);
        background
                = Backgrounds.STARFIELD.get().
                        set(Background.Vector.X, 1).
                        set(Background.Vector.Y, -0.5);

        planet1
                = new ImageButton(Geometry.Shapes.CIRCULAR,
                        new BufferedImage[]{
                            ImageLoader.loadImage("/planets/SunRed.png"),
                            ImageLoader.loadImage("/planets/SunBlue.png")},
                        () -> {
                            SoundPlayer.BUTTON_PLAY.play();
                            StateManager.WORLD1.set();
                        });
        planet1.shape.set(Geometry.X, GameWindow.getWidth() / 2).
                set(Geometry.Y, GameWindow.getHeight() / 2).
                set(Geometry.RADIUS, 200);
    }

    private void updateColor() {
        updateCounter++;
        returnButton.setHoveringColor(Color.RED);
        if (updateCounter >= ONE_SECOND && updateCounter < TWO_SECONDS) {
            returnButton.setHoveringColor(Color.YELLOW);
        }
        if (updateCounter >= TWO_SECONDS && updateCounter < THREE_SECONDS) {
            returnButton.setHoveringColor(Color.GREEN);
        }
        if (updateCounter >= THREE_SECONDS) {
            updateCounter = 0;
        }
    }

    @Override
    public StateManager getStateCase() {
        return StateManager.CHOOSE_WORLD;
    }

    @Override
    public void update() {
        updateColor();
        background.update();
    }

    @Override
    public void draw(Graphics2D g) {
        background.draw(g);
        returnButton.draw(g);
        planet1.draw(g);
    }

    @Override
    public void setObservers() {
        returnButton.shape.hasContained(false);
        planet1.shape.hasContained(false);
        GameWindow.getInputManager().deleteObservers();
        GameWindow.getInputManager().addObserver(returnButton);
        GameWindow.getInputManager().addObserver(planet1);
    }

    @Override
    public SoundPlayer getBackgroundTrack() {
        return BACKGROUND_TRACK;
    }

}
