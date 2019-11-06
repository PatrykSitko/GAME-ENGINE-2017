package game.states;

import java.awt.Color;
import java.awt.Graphics2D;

import game.GameWindow;
import game.gfx.background.Background;
import game.gfx.background.Backgrounds;
import game.gfx.fonts.Fonts;
import game.gfx.ui.buttons.TextButton;
import game.gfx.writers.Writer;
import game.shapes.Shape.Geometry;
import game.sound.SoundPlayer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainMenuState implements State {

    private static int updateCounter;
    private int X_CenterCoordinate = GameWindow.getWidth() / 4;
    private int Y_TittleCoordinate = GameWindow.getHeight() / 2 - 350;
    private int Y_PlayCoordinate = GameWindow.getHeight() / 2 - 150;
    private int Y_SettingsCoordinate = GameWindow.getHeight() / 2 - 50;
    private int Y_ExitCoordinate = GameWindow.getHeight() / 2 + 50;

    private Background backgroundLayer1;
    private Background backgroundLayer2;
    private Background backgroundLayer3;
    private Background backgroundLayer4;
    private Background backgroundLayer5;

    private Color titleColor;

    private TextButton playButton, settingsButton, exitButton;

    protected static final SoundPlayer BACKGROUND_TRACK = SoundPlayer.BACKGROUND_HAPPY;

    public MainMenuState() {
        backgroundLayer1 = Backgrounds.FOREST_LAYER_1.get().set(Background.Vector.X, -0.2);
        backgroundLayer2 = Backgrounds.FOREST_LAYER_2.get().set(Background.Vector.X, 0.1);
        backgroundLayer3 = Backgrounds.FOREST_LAYER_3.get().set(Background.Vector.X, 0.3);
        backgroundLayer4 = Backgrounds.FOREST_LAYER_4.get().set(Background.Vector.X, 0.5);
        backgroundLayer5 = Backgrounds.FOREST_LAYER_5.get().set(Background.Vector.X, 0.7);
        playButton
                = new TextButton(
                        Geometry.Shapes.RECTANGULAR,
                        "Play",
                        Fonts.PIXEL_ART,
                        100,
                        Color.GRAY,
                        Color.GREEN,
                        () -> {
                            SoundPlayer.BUTTON_PLAY.play();
                            StateManager.CHOOSE_WORLD.set();
                        });
        playButton.shape.set(Geometry.X, X_CenterCoordinate + 310).
                set(Geometry.Y, Y_PlayCoordinate);
        settingsButton
                = new TextButton(
                        Geometry.Shapes.RECTANGULAR,
                        "Settings",
                        Fonts.PIXEL_ART,
                        100,
                        Color.GRAY,
                        Color.YELLOW,
                        () -> {
                            SoundPlayer.BUTTON_PLAY.play();
                            StateManager.SETTINGS.set();
                        });
        settingsButton.shape.set(Geometry.X, X_CenterCoordinate + 150).
                set(Geometry.Y, Y_SettingsCoordinate);
        exitButton = new TextButton(
                Geometry.Shapes.RECTANGULAR,
                "Exit",
                Fonts.PIXEL_ART,
                100,
                Color.GRAY,
                Color.RED,
                () -> {
                    SoundPlayer.stopSounds();
                    SoundPlayer.BUTTON_EXIT.play();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MainMenuState.class.getName()).log(Level.SEVERE, null, ex);
                        System.exit(1);
                    }
                    System.exit(0);
                });
        exitButton.shape.set(Geometry.X, X_CenterCoordinate + 320).
                set(Geometry.Y, Y_ExitCoordinate);
    }

    @Override
    public void setObservers() {
        playButton.shape.hasContained(false);
        settingsButton.shape.hasContained(false);
        exitButton.shape.hasContained(false);
        GameWindow.getInputManager().deleteObservers();
        GameWindow.getInputManager().addObserver(playButton);
        GameWindow.getInputManager().addObserver(settingsButton);
        GameWindow.getInputManager().addObserver(exitButton);
    }

    @Override
    public StateManager getStateCase() {
        return StateManager.MAIN_MENU;
    }

    @Override
    public void update() {
        setColor();
        backgroundLayer1.update();
        backgroundLayer2.update();
        backgroundLayer3.update();
        backgroundLayer4.update();
        backgroundLayer5.update();
    }

    @Override
    public void draw(Graphics2D g) {
        backgroundLayer1.draw(g);
        backgroundLayer2.draw(g);
        backgroundLayer3.draw(g);
        backgroundLayer4.draw(g);
        backgroundLayer5.draw(g);
        playButton.draw(g);
        settingsButton.draw(g);
        exitButton.draw(g);
        Writer.
                write(
                        g,
                        GameWindow.getTitle(),
                        GameWindow.getWidth() / 2,
                        Y_TittleCoordinate,
                        true,
                        titleColor,
                        Fonts.FIPPS.getFont(150));

    }

    private void setColor() {
        updateCounter++;
        titleColor = Color.RED;
        if (updateCounter >= ONE_SECOND && updateCounter < TWO_SECONDS) {
            titleColor = Color.YELLOW;
        }
        if (updateCounter >= TWO_SECONDS && updateCounter < THREE_SECONDS) {
            titleColor = Color.GREEN;
        }
        if (updateCounter >= THREE_SECONDS) {
            updateCounter = 0;
        }
    }

    @Override
    public SoundPlayer getBackgroundTrack() {
        return BACKGROUND_TRACK;
    }

}
