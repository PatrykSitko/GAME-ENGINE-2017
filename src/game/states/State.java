package game.states;

import java.awt.Graphics2D;

import game.GameWindow;
import game.sound.SoundPlayer;

public interface State {

    static final int ONE_SECOND = 1 * GameWindow.getFPS();
    static final int TWO_SECONDS = 2 * GameWindow.getFPS();
    static final int THREE_SECONDS = 3 * GameWindow.getFPS();

    void setObservers();

    StateManager getStateCase();

    void update();

    void draw(Graphics2D g);

    SoundPlayer getBackgroundTrack();

}
