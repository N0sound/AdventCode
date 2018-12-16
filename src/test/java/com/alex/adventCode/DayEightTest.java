package com.alex.adventCode;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class DayEightTest {

	private static final String INPUT_FILE_PATH_SAMPLE = "src/test/resources/DayEightInputSample.txt";
	private static final String INPUT_FILE_PATH = "src/test/resources/DayEightInput.txt";

	private DayEight fixture;

	@Before
	public void setUp() throws Exception {
		fixture = new DayEight();
	}

	@Test
	public void shouldFindMetaDataSum() throws IOException {
		File sampleInput = new File(INPUT_FILE_PATH_SAMPLE);
		assertEquals(138, fixture.findSumOfMetaData(sampleInput));

		File input = new File(INPUT_FILE_PATH);
		assertEquals(48496, fixture.findSumOfMetaData(input));
	}

	@Test
	public void shouldGetValueOfRoot() throws IOException {
		File sampleInput = new File(INPUT_FILE_PATH_SAMPLE);
		assertEquals(66, fixture.findValueOfRoot(sampleInput));

		File input = new File(INPUT_FILE_PATH);
		assertEquals(32850, fixture.findValueOfRoot(input));
	}
}
