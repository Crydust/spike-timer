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
		TimerState timerState = new TimerState(duration, now);
		model.setTimerState(timerState);
		model.setElapsedPercent(0);
		model.setElapsedPerMille(0);
		model.setRemainingFormatted(timerState.getRemainingFormatted(now));
		model.setRunning(true);
	}

	public void tick(Instant now) {
		if (model.isRunning()) {
			model.setNow(now);
			TimerState timerState = model.getTimerState();
			if (timerState.getElapsedPercent(now) >= 1.0) {
				model.setRunning(false);
				model.setElapsedPercent(100);
				model.setElapsedPerMille(1_000);
				model.setRemainingFormatted("Done!");
			} else {
				model.setTimerState(timerState);
				model.setElapsedPercent((int) Math.round(timerState.getElapsedPercent(now) * 100));
				model.setElapsedPerMille((int) Math.round(timerState.getElapsedPercent(now) * 1_000));
				model.setRemainingFormatted(timerState.getRemainingFormatted(now));
			}
		}
	}

	public void stop() {
		timer.stop();
	}
}
