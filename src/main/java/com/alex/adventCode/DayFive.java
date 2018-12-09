package com.alex.adventCode;

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DayFive {

	public int getSize(File input) throws IOException {
		String polymer = Files.lines(Paths.get(input.getAbsolutePath())).collect(toList()).get(0);

		return 0;
	}

	private String reactPolymer(String polymer) {
		char[] chars = polymer.toCharArray();
		for (int n = 0; n < chars.length; n++) {
			int neighbor = n + 1;
			if (neighbor < chars.length) {
				char letter = chars[n];

			}
		}
		return polymer;
	}
}
