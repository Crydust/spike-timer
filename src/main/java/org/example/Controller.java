package org.example;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import javax.swing.Timer;

public class Controller {
	private final Model model;
	private final Timer timer;

	public Controller(Model model) {
		this.model = model;
		this.timer = new Timer(1000, e -> this.tick(Instant.now()));
		this.timer.start();
	}

	public void start(String text, Instant now) {
		// TODO advanced parsing
		Duration duration = Duration.of(Long.parseLong(text), ChronoUnit.MINUTES);
		model.setTimerState(new TimerState(duration, now));
		model.setRunning(true);
	}

	public void tick(Instant now) {
		if (model.isRunning()) {
			model.setNow(now);
			if (model.getTimerState().getElapsedPercent(now) >= 1.0) {
				model.setRunning(false);
			}
		}
	}

	public void restart(Instant now) {
		model.setTimerState(new TimerState(model.getTimerState().getDuration(), now));
		model.setRunning(true);
	}

	public void stop() {
		timer.stop();
	}
}
