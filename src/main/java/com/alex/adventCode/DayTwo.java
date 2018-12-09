package com.alex.adventCode;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DayTwo {

	public long getChecksum(File input) throws FileNotFoundException {
		int hasTwo = 0;
		int hasThree = 0;
		try (Scanner scanner = new Scanner(input)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				ChecksumResult result = getChecksumResult(line);
				if (result.hasTwo) {
					hasTwo++;
				}
				if (result.hasThree) {
					hasThree++;
				}
			}
		}
		return hasThree * hasTwo;
	}

	public String getLargestCommongId(File input) throws IOException {
		List<String> inputList = Files.lines(input.toPath()).collect(toList());
		List<Character> largestCommonId = Collections.emptyList();

		for (int n = 0; n < inputList.size(); n++) {
			String currentLine = inputList.get(n);
			for (int m = n + 1; m < inputList.size(); m++) {
				String otherLine = inputList.get(m);
				List<Character> commonCharacters = getCommonChararcters(currentLine, otherLine);
				if (commonCharacters.size() > largestCommonId.size()) {
					largestCommonId = commonCharacters;
				}
			}
		}
		return largestCommonId.stream().map(character -> character.toString()).collect(joining());
	}

	private ChecksumResult getChecksumResult(String line) {
		Map<Character, Integer> characterToCountMap = new HashMap<>();
		for (int i = 0; i < line.length(); i++) {
			char character = line.charAt(i);
			if (characterToCountMap.containsKey(character)) {
				Integer count = characterToCountMap.get(character);
				characterToCountMap.put(character, ++count);
			} else {
				characterToCountMap.put(character, 1);
			}
		}
		Collection<Integer> values = characterToCountMap.values();
		return new ChecksumResult(values.contains(2), values.contains(3));
	}

	private List<Character> getCommonChararcters(String currentLine, String otherLine) {
		List<Character> commonCharacters = new ArrayList<>();
		for (int n = 0; n < currentLine.length(); n++) {
			char currentCharacter = currentLine.charAt(n);
			char otherCharacter = otherLine.charAt(n);
			if (currentCharacter == otherCharacter) {
				commonCharacters.add(currentCharacter);
			}
		}
		return commonCharacters;
	}

	private static class ChecksumResult {
		private final boolean hasTwo;
		private final boolean hasThree;

		public ChecksumResult(boolean hasTwo, boolean hasThree) {
			this.hasThree = hasThree;
			this.hasTwo = hasTwo;
		}
	}
}
