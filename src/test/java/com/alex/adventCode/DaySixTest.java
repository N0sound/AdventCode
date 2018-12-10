package com.alex.adventCode;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class DaySixTest {

	private static final String INPUT_FILE_PATH_SAMPLE = "src/test/resources/DaySixInputSample.txt";
	private static final String INPUT_FILE_PATH = "src/test/resources/DaySixInput.txt";

	private DaySix fixture;

	@Before
	public void setUp() throws Exception {
		fixture = new DaySix();
	}

	@Test
	public void shouldFindLargestFiniteArea() throws Exception {
		File sampleInput = new File(INPUT_FILE_PATH_SAMPLE);
		assertEquals(17, fixture.findLargestFiniteArea(sampleInput, 10));

		File input = new File(INPUT_FILE_PATH);
		assertEquals(3238, fixture.findLargestFiniteArea(input, 400));
	}

	@Test
	public void shouldFindLargestAreaWithinDistance() throws Exception {
		File sampleInput = new File(INPUT_FILE_PATH_SAMPLE);
		assertEquals(16, fixture.findLargestAreaWithinDistance(sampleInput, 10, 32));

		File input = new File(INPUT_FILE_PATH);
		assertEquals(45046, fixture.findLargestAreaWithinDistance(input, 400, 10000));
	}
}
