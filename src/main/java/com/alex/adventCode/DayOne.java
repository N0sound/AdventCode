package com.alex.adventCode;

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.Validate;

public class DayOne {

	private static final int OPERATOR_INDEX = 1;
	private static final int VALUE_INDEX = 2;
	private static final String ADDITION_OPERATOR = "+";
	private static final String SUBTRACTION_OPERATOR = "-";
	private static final Pattern VALUE_PATTERN = Pattern.compile("^(\\+|-){1}(\\d+){1}$");

	public long getFrequency(File input) throws FileNotFoundException {
		Integer currentFrequency = 0;
		try (Scanner scanner = new Scanner(input)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				currentFrequency = updateFrequency(currentFrequency, line);
			}
		}
		return currentFrequency;
	}

	private Integer updateFrequency(Integer frequency, String line) {
		Matcher matcher = VALUE_PATTERN.matcher(line);
		Validate.isTrue(matcher.matches());
		if (matcher.matches()) {
			Integer value = Integer.parseInt(matcher.group(VALUE_INDEX));
			String operator = matcher.group(OPERATOR_INDEX);
			return addValue(frequency, value, operator);
		}
		return frequency;
	}

	private Integer addValue(Integer frequency, Integer value, String operator) {
		if (operator.equals(ADDITION_OPERATOR)) {
			frequency = frequency + value;
		} else if (operator.equals(SUBTRACTION_OPERATOR)) {
			frequency = frequency - value;
		}
		return frequency;
	}

	public long findFirstDuplicateFrequency(File input) throws Exception {
		Integer currentFrequency = 0;
		Set<Integer> frequencies = new HashSet<>();
		boolean hasFoundDuplicate = false;
		List<String> driftFrequencies = Files.lines(input.toPath()).collect(toList());

		while (!hasFoundDuplicate) {
			FrequencyResult result = parseFrequency(driftFrequencies, frequencies, currentFrequency);
			currentFrequency = result.frequency;
			hasFoundDuplicate = result.foundDuplicate;
		}
		return currentFrequency;
	}

	private FrequencyResult parseFrequency(List<String> driftFrequencies, Set<Integer> frequencies,
			Integer currentFrequency) throws Exception {
		for (String line : driftFrequencies) {
			currentFrequency = updateFrequency(currentFrequency, line);

			if (frequencies.contains(currentFrequency)) {
				return new FrequencyResult(true, currentFrequency);

			} else {
				frequencies.add(currentFrequency);
			}
		}
		return new FrequencyResult(false, currentFrequency);
	}

	private static class FrequencyResult {
		private final boolean foundDuplicate;
		private final Integer frequency;

		public FrequencyResult(boolean foundDuplicate, int frequency) {
			this.foundDuplicate = foundDuplicate;
			this.frequency = frequency;
		}
	}
}
