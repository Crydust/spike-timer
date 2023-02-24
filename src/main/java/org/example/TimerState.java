package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;

public class TimerState {
	private final Duration duration;
	private final Instant startTime;

	public TimerState(Duration duration) {
		this(duration, Instant.now());
	}

	public TimerState(Duration duration, Instant startTime) {
		this.startTime = startTime;
		this.duration = duration;
	}

	public Duration getDuration() {
		return duration;
	}

	public Instant getStartTime() {
		return startTime;
	}

	public Instant getEndTime() {
		return startTime.plus(duration);
	}

	private Duration getElapsed(Instant now) {
		Duration elapsed = Duration.between(startTime, now);
		if (elapsed.compareTo(duration) > 0) {
			return duration;
		}
		return elapsed;
	}

	private Duration getRemaining(Instant now) {
		Duration remaining = duration.minus(getElapsed(now));
		if (remaining.compareTo(Duration.ZERO) < 0) {
			return Duration.ZERO;
		}
		return remaining;
	}

	public String getRemainingFormatted(Instant now) {
		Duration remaining = getRemaining(now);
		// TODO fancy formatting
		return remaining.toString();
	}

	public double getElapsedPercent(Instant now) {
		BigDecimal total = convertDurationToNanos(duration);
		BigDecimal elapsed = convertDurationToNanos(getElapsed(now));
		double elapsedPercent = elapsed.divide(total, 3, RoundingMode.HALF_UP).doubleValue();
		return Math.min(elapsedPercent, 1.0);
	}

	private static BigDecimal convertDurationToNanos(Duration duration) {
		return BigDecimal.valueOf(duration.getSeconds())
				.multiply(BigDecimal.valueOf(1_000_000_000L))
				.add(BigDecimal.valueOf(duration.getNano()));
	}

}
