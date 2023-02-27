package org.example;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.Duration;
import java.time.Instant;

public class Model {
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private Instant now = Instant.now();
	private TimerState timerState = new TimerState(Duration.ZERO, now);
	private boolean running = false;
	private int elapsedPercent = 0;
	private int elapsedPerMille = 0;
	private String remainingFormatted = "0h 0m 0s";

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}

	public TimerState getTimerState() {
		return timerState;
	}

	public void setTimerState(TimerState timerState) {
		TimerState oldValue = this.timerState;
		this.timerState = timerState;
		this.pcs.firePropertyChange(PropertyName.TIMER_STATE.name(), oldValue, timerState);
	}

	public void setNow(Instant now) {
		Instant oldValue = this.now;
		this.now = now;
		this.pcs.firePropertyChange(PropertyName.NOW.name(), oldValue, now);
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		boolean oldValue = this.running;
		this.running = running;
		this.pcs.firePropertyChange(PropertyName.RUNNING.name(), oldValue, running);
	}

	public int getElapsedPercent() {
		return elapsedPercent;
	}

	public void setElapsedPercent(int elapsedPercent) {
		int oldValue = this.elapsedPercent;
		this.elapsedPercent = elapsedPercent;
		this.pcs.firePropertyChange(PropertyName.PERCENT.name(), oldValue, elapsedPercent);
	}

	public int getElapsedPerMille() {
		return elapsedPerMille;
	}

	public void setElapsedPerMille(int elapsedPerMille) {
		int oldValue = this.elapsedPerMille;
		this.elapsedPerMille = elapsedPerMille;
		this.pcs.firePropertyChange(PropertyName.PER_MILLE.name(), oldValue, elapsedPerMille);
	}

	public String getRemainingFormatted() {
		return remainingFormatted;
	}

	public void setRemainingFormatted(String remainingFormatted) {
		String oldValue = this.remainingFormatted;
		this.remainingFormatted = remainingFormatted;
		this.pcs.firePropertyChange(PropertyName.REMAINING_FORMATTED.name(), oldValue, remainingFormatted);
	}
}
