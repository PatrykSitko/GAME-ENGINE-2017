/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.states;

import game.GameWindow;
import game.gfx.background.Background;
import game.gfx.background.Backgrounds;
import game.gfx.loaders.ImageLoader;
import game.map.bounds.MapBounds;
import game.map.objects.entities.player.Player;
import game.sound.SoundPlayer;
import game.timers.Timer;
import java.awt.Graphics2D;
import java.util.Random;

/**
 *
 * @author Patryk Sitko
 */
public class World1State implements State {

    private static Background dungeon,
            cloud1,
            cloud2,
            cloud3,
            cloud4,
            cloud5,
            cloud6,
            cloud7,
            cloud8,
            cloud9,
            cloud10,
            cloud11,
            cloud12;

    private static Random random;
    private Timer cloudVectorUpdateTimer;
    protected static final SoundPlayer BACKGROUND_TRACK = SoundPlayer.BACKGROUND_CHILLSONG;

    private static Player player;

    public World1State() {
        player = new Player(ImageLoader.loadImage("/levels/player.png"), 600, 300, new MapBounds(ImageLoader.loadImage("/levels/collisions/dungeon1_collisions.png")));
        cloudVectorUpdateTimer = new Timer();
        random = new Random();
        dungeon = Backgrounds.DUNGEON.get();
        cloud1 = Backgrounds.CLOUD1.get();
        cloud2 = Backgrounds.CLOUD2.get();
        cloud3 = Backgrounds.CLOUD3.get();
        cloud4 = Backgrounds.CLOUD4.get();
        cloud5 = Backgrounds.CLOUD5.get();
        cloud6 = Backgrounds.CLOUD6.get();
        cloud7 = Backgrounds.CLOUD7.get();
        cloud8 = Backgrounds.CLOUD8.get();
        cloud9 = Backgrounds.CLOUD9.get();
        cloud10 = Backgrounds.CLOUD10.get();
        cloud11 = Backgrounds.CLOUD11.get();
        cloud12 = Backgrounds.CLOUD12.get();
        setCloudSpeed();
    }

    @Override
    public void setObservers() {
        GameWindow.getInputManager().deleteObservers();
        GameWindow.getInputManager().addObserver(player);
    }

    @Override
    public StateManager getStateCase() {
        return StateManager.WORLD1;
    }

    @Override
    public void update() {
        cloudVectorUpdateTimer.update();
        if (cloudVectorUpdateTimer.getTime() == 10_000) {
            cloudVectorUpdateTimer = new Timer();
            setCloudSpeed();
        }
        dungeon.update();
        cloud1.update();
        cloud2.update();
        cloud3.update();
        cloud4.update();
        cloud5.update();
        cloud6.update();
        cloud7.update();
        cloud8.update();
        cloud9.update();
        cloud10.update();
        cloud11.update();
        cloud12.update();
        player.update();
    }

    @Override
    public void draw(Graphics2D g) {
        player.draw(g);
        dungeon.draw(g);
        cloud1.draw(g);
        cloud2.draw(g);
        cloud3.draw(g);
        cloud4.draw(g);
        cloud5.draw(g);
        cloud6.draw(g);
        cloud7.draw(g);
        cloud8.draw(g);
        cloud9.draw(g);
        cloud10.draw(g);
        cloud11.draw(g);
        cloud12.draw(g);
    }

    private int randomSpeed() {
        return (random.nextInt(90) + 10) / 100;
    }

    private void setCloudSpeed() {
        cloud1.set(Background.Vector.X, 0.13 + randomSpeed());
        cloud2.set(Background.Vector.X, 0.2 + randomSpeed());
        cloud3.set(Background.Vector.X, 0.23 + randomSpeed());
        cloud4.set(Background.Vector.X, 0.22 + randomSpeed());
        cloud5.set(Background.Vector.X, 0.17 + randomSpeed());
        cloud6.set(Background.Vector.X, 0.18 + randomSpeed());
        cloud7.set(Background.Vector.X, 0.15 + randomSpeed());
        cloud8.set(Background.Vector.X, 0.26 + randomSpeed());
        cloud9.set(Background.Vector.X, 0.24 + randomSpeed());
        cloud10.set(Background.Vector.X, 0.19 + randomSpeed());
        cloud11.set(Background.Vector.X, 0.24 + randomSpeed());
        cloud12.set(Background.Vector.X, 0.19 + randomSpeed());
    }

    @Override
    public SoundPlayer getBackgroundTrack() {
        return BACKGROUND_TRACK;
    }

}
