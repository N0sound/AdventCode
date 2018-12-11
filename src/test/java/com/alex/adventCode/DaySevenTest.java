package com.alex.adventCode;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class DaySevenTest {
	private static final String INPUT_FILE_PATH_SAMPLE = "src/test/resources/DaySevenInputSample.txt";
	private static final String INPUT_FILE_PATH = "src/test/resources/DaySevenInput.txt";

	private DaySeven fixture;

	@Before
	public void setUp() throws Exception {
		fixture = new DaySeven();
	}

	@Test
	public void shouldGetInstructionSteps() throws IOException {
		File sampleInput = new File(INPUT_FILE_PATH_SAMPLE);
		assertEquals("CABDFE", fixture.getInstructionSteps(sampleInput));

		File input = new File(INPUT_FILE_PATH);
		assertEquals("LFMNJRTQVZCHIABKPXYEUGWDSO", fixture.getInstructionSteps(input));
	}

	@Test
	public void shouldGetInstructionDuration() throws IOException {
		File sampleInput = new File(INPUT_FILE_PATH_SAMPLE);
		assertEquals(15, fixture.getTimeToCompleteInstructions(sampleInput, 2, 64));

		File input = new File(INPUT_FILE_PATH);
		assertEquals(1180, fixture.getTimeToCompleteInstructions(input, 5, 4));
	}
}
