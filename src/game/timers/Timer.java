package game.timers;

public class Timer {

	private long timer, now, lastTime;

	public Timer() {
		lastTime = System.nanoTime();
	}

	public long getTime() {
		return timer;
	}

	public void update() {
		now = System.nanoTime();
		timer = now - lastTime;
		lastTime = now;
	}

}
