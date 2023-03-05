package be.crydust.spiketimer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.Duration;
import java.time.Instant;

public class Model {
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private TimerState timerState = new TimerState(Duration.ZERO, Instant.now());
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
		this.timerState = timerState;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		boolean oldValue = this.running;
		this.running = running;
		this.pcs.firePropertyChange(PropertyName.RUNNING.name(), oldValue, running);
	}

	public void setElapsedPercent(int elapsedPercent) {
		int oldValue = this.elapsedPercent;
		this.elapsedPercent = elapsedPercent;
		this.pcs.firePropertyChange(PropertyName.PERCENT.name(), oldValue, elapsedPercent);
	}

	public void setElapsedPerMille(int elapsedPerMille) {
		int oldValue = this.elapsedPerMille;
		this.elapsedPerMille = elapsedPerMille;
		this.pcs.firePropertyChange(PropertyName.PER_MILLE.name(), oldValue, elapsedPerMille);
	}

	public void setRemainingFormatted(String remainingFormatted) {
		String oldValue = this.remainingFormatted;
		this.remainingFormatted = remainingFormatted;
		this.pcs.firePropertyChange(PropertyName.REMAINING_FORMATTED.name(), oldValue, remainingFormatted);
	}
}
