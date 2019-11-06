package game.states;

import game.gfx.drawingSurface.DrawingSurface;
import game.sound.SoundPlayer;

public enum StateManager {
    MAIN_MENU(new MainMenuState()), CHOOSE_WORLD(new ChooseWorldState()), SETTINGS(new SettingsState()), WORLD1(new World1State());
    private static State previousState;
    private static State currentState;
    private State state;

    public static SoundPlayer backgroundTrack = null;

    StateManager(State s) {
        state = s;
    }

    public State get() {
        return state;
    }

    public static State getCurrent() {
        return currentState;
    }

    public static void setPrevious() {
        backgroundTrack = currentState.getBackgroundTrack();
        if (previousState == null) {
            return;
        }
        State currentStateHolder = currentState;
        currentState = previousState;
        previousState = currentStateHolder;
        currentState.setObservers();
    }

    public void set() {
        previousState = currentState;
        currentState = this.get();
        if (previousState != null) {
            SettingsState.backgroundTrack = previousState.getBackgroundTrack();
        }
        backgroundTrack = currentState.getBackgroundTrack();
        currentState.setObservers();
    }

    public static void update() {
        currentState.update();
        playMusic();
    }

    public static void drawOn(DrawingSurface drawingSurface) {
        drawingSurface.draw(currentState);
    }

    private static void playMusic() {
        if (backgroundTrack != null
                && !backgroundTrack.isRunning()
                && !SoundPlayer.BUTTON_EXIT.isRunning()) {
            SoundPlayer.stopSounds();
            backgroundTrack.loop();
        }
    }
}
