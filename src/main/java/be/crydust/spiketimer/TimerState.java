package be.crydust.spiketimer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;

public class TimerState {

	private static final int HOURS_PER_DAY = 24;
	private static final int MINUTES_PER_HOUR = 60;
	private static final int SECONDS_PER_MINUTE = 60;
	private static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
	private static final long NANOS_PER_SECOND = 1_000_000_000L;
	/**
	 * Nanos per minute.
	 */
	private static final long NANOS_PER_MINUTE = NANOS_PER_SECOND * SECONDS_PER_MINUTE;
	/**
	 * Nanos per hour.
	 */
	private static final long NANOS_PER_HOUR = NANOS_PER_MINUTE * MINUTES_PER_HOUR;
	/**
	 * Nanos per day.
	 */
	private static final long NANOS_PER_DAY = NANOS_PER_HOUR * HOURS_PER_DAY;

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
		return formatDuration(getRemaining(now));
	}

	/* package-private for testing */
	static String formatDuration(Duration duration) {
		if (duration.compareTo(Duration.ZERO) <= 0) {
			return "0s";
		}
		final long seconds = duration.getSeconds();
		final long hours = seconds / SECONDS_PER_HOUR;
		final int minutes = (int) ((seconds % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
		final int secs = (int) (seconds % SECONDS_PER_MINUTE);
		if (hours != 0) {
			return "%dh %dm %ds".formatted(hours, minutes, secs);
		}
		if (minutes != 0) {
			return "%dm %ds".formatted(minutes, secs);
		}
		return "%ds".formatted(secs);
	}

	public double getElapsedPercent(Instant now) {
		BigDecimal total = convertDurationToNanos(duration);
		BigDecimal elapsed = convertDurationToNanos(getElapsed(now));
		double elapsedPercent = elapsed.divide(total, 3, RoundingMode.HALF_UP).doubleValue();
		return Math.min(elapsedPercent, 1.0);
	}

	private static BigDecimal convertDurationToNanos(Duration duration) {
		return BigDecimal.valueOf(duration.getSeconds())
				.multiply(BigDecimal.valueOf(NANOS_PER_SECOND))
				.add(BigDecimal.valueOf(duration.getNano()));
	}

}
