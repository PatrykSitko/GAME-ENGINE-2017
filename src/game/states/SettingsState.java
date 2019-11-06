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

public class SettingsState implements State {

    private static int updateCounter;
    private Background background;

    private ImageButton muteButton;
    private TextButton returnButton;
    private static int volume = 100, volumeBackup;
    protected static SoundPlayer backgroundTrack = SoundPlayer.BACKGROUND_HAPPY;

    public SettingsState() {
        background
                = Backgrounds.STARFIELD.get().
                        set(Background.Vector.X, 1).
                        set(Background.Vector.Y, -0.5);
        muteButton
                = new ImageButton(
                        Geometry.Shapes.CIRCULAR,
                        new BufferedImage[]{ImageLoader.loadImage("/sounds/buttons/images/not-muted.png"),
                            ImageLoader.loadImage("/sounds/buttons/images/muted.png")},
                        () -> {
                            if (volume > 0) {
                                volumeBackup = volume;
                                volume = 0;
                            } else {
                                volume = volumeBackup;
                            }
                            SoundPlayer.setVolume(volume);
                        }
                );
        muteButton.shape.set(Geometry.X, GameWindow.getWidth() / 2)
                .set(Geometry.Y, GameWindow.getHeight() / 2)
                .set(Geometry.WIDTH, 100)
                .set(Geometry.HEIGHT, 100);
        muteButton.changeContentOnClick = true;
        returnButton = new TextButton(
                Geometry.Shapes.RECTANGULAR,
                "Return",
                Fonts.PIXEL_ART,
                75,
                Color.GRAY,
                Color.GREEN,
                () -> {
                    SoundPlayer.BUTTON_RETURN.play();
                    StateManager.setPrevious();
                });

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
        return StateManager.SETTINGS;
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
        muteButton.draw(g);
    }

    @Override
    public void setObservers() {
        muteButton.shape.hasContained(false);
        returnButton.shape.hasContained(false);
        GameWindow.getInputManager().deleteObservers();
        GameWindow.getInputManager().addObserver(muteButton);
        GameWindow.getInputManager().addObserver(returnButton);
    }

    @Override
    public SoundPlayer getBackgroundTrack() {
        return backgroundTrack;
    }

}
