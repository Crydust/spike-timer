package be.crydust.spiketimer;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimerStateTest {

	@ParameterizedTest
	@CsvSource(textBlock = """
			PT1h2m3s, 1h 2m 3s
			PT1h2m,   1h 2m 0s
			PT1h3s,   1h 0m 3s
			PT1h,     1h 0m 0s
			PT2m3s,      2m 3s
			PT2m,        2m 0s
			PT3s,           3s
			PT0.001s,       0s
			PT0s,           0s
			PT-1s,          0s
			""")
	void should_formatDuration(String input, String expected) {
		assertEquals(expected, TimerState.formatDuration(Duration.parse(input)));

	}
}
