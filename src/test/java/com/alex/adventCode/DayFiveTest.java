package com.alex.adventCode;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	public void sdfds() {
		String[] test = new String[5];
		test[1] = "a";
		test[4] = "e";
		System.out.println(test.length);
		List<String> list = new ArrayList<String>(Arrays.asList(test));
		System.out.println(list);
		list.removeAll(Arrays.asList("", null));
		System.out.println(list);
		String[] array = list.toArray(new String[0]);
		System.out.println(array.length);
	}

	@Test
	public void shouldGetPolymerSize() throws Exception {
		File inputSample = new File(INPUT_FILE_PATH_SAMPLE);
		assertEquals(10, fixture.getSize(inputSample));

		File input = new File(INPUT_FILE_PATH);
		System.out.println(fixture.getSize(input));
		// assertEquals(10, fixture.getSize(input));
	}

}
