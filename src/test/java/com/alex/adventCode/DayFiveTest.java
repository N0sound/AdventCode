package com.alex.adventCode;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class DayFiveTest {

	private static final String INPUT_FILE_PATH = "src/test/resources/DayFiveInput.txt";
	private static final String INPUT_FILE_PATH_SAMPLE = "src/test/resources/DayFiveInputSample.txt";

	private DayFive fixture;

	@Before
	public void setUp() throws Exception {
		fixture = new DayFive();
	}

	@Test
	public void shouldGetFullyReactedPolymerSize() throws Exception {
		File inputSample = new File(INPUT_FILE_PATH_SAMPLE);
		assertEquals(10, fixture.getFullyReactedPolymerSize(inputSample));

		File input = new File(INPUT_FILE_PATH);
		assertEquals(9202, fixture.getFullyReactedPolymerSize(input));
	}

	@Test
	public void shouldGetSmallestPolymer() throws Exception {
		File inputSample = new File(INPUT_FILE_PATH_SAMPLE);
		assertEquals(4, fixture.getSmallestPolymer(inputSample));

		File input = new File(INPUT_FILE_PATH);
		assertEquals(6394, fixture.getSmallestPolymer(input));
	}
}
