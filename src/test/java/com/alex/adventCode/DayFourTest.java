package com.alex.adventCode;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class DayFourTest {

	private static final String INPUT_FILE_PATH_SAMPLE = "src/test/resources/DayFourInputSample.txt";
	private static final String INPUT_FILE_PATH = "src/test/resources/DayFourInput.txt";
	private static final SimpleDateFormat DATE_PARSER = new SimpleDateFormat("yyyy-MM-dd kk:mm");

	private DayFour fixture;

	@Before
	public void setUp() throws Exception {
		fixture = new DayFour();
	}

	@SuppressWarnings("deprecation")
	@Test
	public void shouldParseTime() throws ParseException {
		String startTime = "1518-11-01 00:00";
		String endTime = "1518-11-01 00:12";

		Date startDate = DATE_PARSER.parse(startTime);
		Date endDate = DATE_PARSER.parse(endTime);
		assertEquals(0, startDate.getMinutes());
		assertEquals(12, endDate.getMinutes());
	}

	@Test
	public void shouldGenerateHashForGuardAsleepTheMost() throws Exception {
		File sampleInput = new File(INPUT_FILE_PATH_SAMPLE);
		assertEquals(240, fixture.generateHashForGuardAsleepTheMost(sampleInput));

		File input = new File(INPUT_FILE_PATH);
		assertEquals(140932, fixture.generateHashForGuardAsleepTheMost(input));
	}

	@Test
	public void shouldGenerateHashForGuardAsleepMostFrequentForGivenMinute() throws Exception {
		File sampleInput = new File(INPUT_FILE_PATH_SAMPLE);
		assertEquals(4455, fixture.generateHashForGuardAsleepAtMostForGivenMinute(sampleInput));

		File input = new File(INPUT_FILE_PATH);
		assertEquals(51232, fixture.generateHashForGuardAsleepAtMostForGivenMinute(input));
	}
}
