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
}
