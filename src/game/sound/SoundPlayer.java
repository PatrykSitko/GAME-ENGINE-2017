package game.sound;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public enum SoundPlayer {
    BUTTON_HOVERING(0, "/sounds/buttons/hovering.wav", 0), BUTTON_BLOCKED(1, "/sounds/buttons/onClick.wav",
            0), BUTTON_RETURN(2, "/sounds/buttons/return.wav", 0), BUTTON_PLAY(3, "/sounds/buttons/play.wav",
            0), BUTTON_EXIT(4, "/sounds/buttons/exit.wav",
            0), BACKGROUND_HAPPY(5, "/sounds/background/happy.wav", 0), BACKGROUND_CHILLSONG(5, "/sounds/background/chillSong.wav", 0);
    private FloatControl gainControll;
    public final int id;
    private String path;
    private Clip clip;
    private int gap;

    private boolean createSoundOutput;

    SoundPlayer(int id, String path, int gap) {
        this.id = id;
        this.gap = gap;
        this.path = path;
        load();
    }

    private void load() {
        try (AudioInputStream ais = AudioSystem.getAudioInputStream(SoundPlayer.class.getResourceAsStream(path))) {
            AudioFormat baseFormat = ais.getFormat();
            AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
                    baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
            try (AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais)) {
                clip = AudioSystem.getClip();
                clip.open(dais);
                gainControll = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                changeVolume(100);
            } catch (LineUnavailableException e) {
                Logger.getLogger(SoundPlayer.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        } catch (IOException | UnsupportedAudioFileException e) {
            Logger.getLogger(SoundPlayer.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void play() {
        play(gap);
    }

    public void play(int framePosition) {
        if (createSoundOutput) {
            Clip c = clip;
            if (c == null) {
                return;
            }
            c.setFramePosition(framePosition);
            c.start();
        }
    }

    public void stop() {
        if (clip == null) {
            return;
        }
        if (clip.isRunning()) {
            clip.stop();
        }
    }

    public void resume() {
        if (createSoundOutput && !clip.isRunning()) {
            clip.start();
        }
    }

    public void loop() {
        loop(gap, gap, clip.getFrameLength() - 1);
    }

    public void loop(int framePosition) {
        loop(framePosition, gap, clip.getFrameLength() - 1);
    }

    public void loop(int start, int end) {
        loop(gap, start, end);
    }

    public void loop(int framePosition, int start, int end) {
        stop();
        if (createSoundOutput) {
            clip.setLoopPoints(start, end);
            if (clip.getFramePosition() > start && clip.getFramePosition() < clip.getFrameLength()) {
                resume();
            } else {
                clip.setFramePosition(framePosition);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
    }

    public void setPosition(int framePosition) {
        clip.setFramePosition(framePosition);
    }

    public int getFrames() {
        return clip.getFrameLength();
    }

    public int getPosition() {
        return clip.getFramePosition();
    }

    public void close() {
        stop();
        clip.close();
    }

    public boolean isRunning() {
        return clip.isRunning();
    }

    public static void stopSounds() {
        for (SoundPlayer sound : SoundPlayer.values()) {
            sound.stop();
        }
    }

    public void changeVolume(int max100min0) {
        max100min0 = max100min0 > 100 ? 100 : max100min0;
        max100min0 = max100min0 < 0 ? 0 : max100min0;
        double volume = max100min0 * 4 / 5;
        createSoundOutput = (0 <= volume);
        gainControll.setValue((float) (volume - 80) + 6.0206f);
    }

    public static void setVolume(int max100min0) {
        for (SoundPlayer clip : SoundPlayer.values()) {
            clip.changeVolume(max100min0);
        }
    }

    public static void globalMute(boolean mute) {
        for (SoundPlayer clip : SoundPlayer.values()) {
            clip.createSoundOutput = mute;
        }
        if (mute) {
            stopSounds();
        }

    }
}
