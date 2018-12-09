package com.alex.adventCode;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class DayTwoTest {

	private static final String INPUT_FILE_PATH_SAMPLE_1 = "src/test/resources/DayTwoInputSample.txt";
	private static final String INPUT_FILE_PATH_SAMPLE_2 = "src/test/resources/DayTwoInputSample2.txt";
	private static final String INPUT_FILE_PATH = "src/test/resources/DayTwoInput.txt";

	private DayTwo fixture;

	@Before
	public void setUp() throws Exception {
		fixture = new DayTwo();
	}

	@Test
	public void shouldComputeChecksum() throws Exception {
		File sampleInput = new File(INPUT_FILE_PATH_SAMPLE_1);
		assertEquals(12L, fixture.getChecksum(sampleInput));

		File input = new File(INPUT_FILE_PATH);
		assertEquals(6723L, fixture.getChecksum(input));
	}

	@Test
	public void shouldGetLargestCommonId() throws Exception {
		File sampleInput = new File(INPUT_FILE_PATH_SAMPLE_2);
		assertEquals("fgij", fixture.getLargestCommongId(sampleInput));

		File input = new File(INPUT_FILE_PATH);
		assertEquals("prtkqyluiusocwvaezjmhmfgx", fixture.getLargestCommongId(input));
	}
}
