package com.alex.adventCode;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class DayOneTest {

	private static final String INPUT_FILE_PATH_SAMPLE = "src/test/resources/DayOneInputSample.txt";
	private static final String INPUT_FILE_PATH = "src/test/resources/DayOneInput.txt";

	private DayOne fixture;

	@Before
	public void setUp() throws Exception {
		fixture = new DayOne();
	}

	@Test
	public void shouldCalculateFrequency() throws Exception {
		File sampleInput = new File(INPUT_FILE_PATH_SAMPLE);
		assertEquals(4, fixture.getFrequency(sampleInput));

		File input = new File(INPUT_FILE_PATH);
		assertEquals(472, fixture.getFrequency(input));
	}

	@Test
	public void shouldFindDuplicateFreqency() throws Exception {
		File sampleInput = new File(INPUT_FILE_PATH_SAMPLE);
		assertEquals(10, fixture.findFirstDuplicateFrequency(sampleInput));

		File input = new File(INPUT_FILE_PATH);
		assertEquals(66932, fixture.findFirstDuplicateFrequency(input));
	}
}
