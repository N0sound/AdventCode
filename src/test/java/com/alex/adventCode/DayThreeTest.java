package com.alex.adventCode;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

public class DayThreeTest {

	private static final String INPUT_FILE_PATH_SAMPLE = "src/test/resources/DayThreeInputSample.txt";
	private static final String INPUT_FILE_PATH = "src/test/resources/DayThreeInput.txt";

	private DayThree fixture;

	@Before
	public void setUp() throws Exception {
		fixture = new DayThree();
	}

	@Test
	public void shouldComputeFabricOverLapArea() throws FileNotFoundException {
		File sampleInput = new File(INPUT_FILE_PATH_SAMPLE);
		assertEquals(4, fixture.getOverLapFabricArea(sampleInput));

		File input = new File(INPUT_FILE_PATH);
		assertEquals(113716, fixture.getOverLapFabricArea(input));
	}

	@Test
	public void shouldGetFabricIdWithoutOverLap() throws FileNotFoundException {
		File sampleInput = new File(INPUT_FILE_PATH_SAMPLE);
		assertEquals("3", fixture.getFabricIdWithoutOverlap(sampleInput));

		File input = new File(INPUT_FILE_PATH);
		assertEquals("742", fixture.getFabricIdWithoutOverlap(input));
	}
}
