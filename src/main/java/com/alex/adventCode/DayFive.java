package com.alex.adventCode;

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;

public class DayFive {

	private static final String[] ALPHABET = { "a|A", "b|B", "c|C", "d|D", "e|E", "f|F", "g|G", "h|H", "i|I", "j|J",
			"k|K", "l|L", "m|M", "n|N", "o|O", "p|P", "q|Q", "r|R", "s|S", "t|T", "u|U", "v|V", "w|W", "x|X", "y|Y",
			"z|Z" };

	public int getFullyReactedPolymerSize(File input) throws IOException {
		String polymer = Files.lines(Paths.get(input.getAbsolutePath())).collect(toList()).get(0);

		return fullyReactPolymer(polymer);
	}

	public int getSmallestPolymer(File input) throws IOException {
		String polymer = Files.lines(Paths.get(input.getAbsolutePath())).collect(toList()).get(0);

		return findSmallestPolymer(polymer);
	}

	private int findSmallestPolymer(String polymer) {
		int smallestLength = Integer.MAX_VALUE;
		for (String letter : ALPHABET) {
			String modifiedPolymer = polymer.replaceAll(letter, StringUtils.EMPTY);
			int length = fullyReactPolymer(modifiedPolymer);
			if (length < smallestLength) {
				smallestLength = length;
			}
		}
		return smallestLength;
	}

	private int fullyReactPolymer(String polymer) {
		boolean isFullyReacted = false;

		while (!isFullyReacted) {
			String reactedPolymer = reactPolymer(polymer);
			if (polymer.length() == reactedPolymer.length()) {
				isFullyReacted = true;
			}
			polymer = reactedPolymer;
		}
		return polymer.length();
	}

	private String reactPolymer(String polymer) {
		StringBuilder reactedPolymer = new StringBuilder();
		int length = polymer.length();
		for (int position = 0; position < length; position++) {
			char currentCharacter = polymer.charAt(position);
			int nextPosition = position + 1;
			if (shouldRemoveMonomer(polymer, length, currentCharacter, nextPosition)) {
				position = nextPosition;
			} else {
				reactedPolymer.append(Character.toString(currentCharacter));
			}
		}
		return reactedPolymer.toString();
	}

	private boolean shouldRemoveMonomer(String polymer, int length, char currentCharacter, int nextPosition) {
		return nextPosition >= length ? false : shouldReact(currentCharacter, polymer.charAt(nextPosition));
	}

	private boolean shouldReact(char currentCharacter, char adjacentCharacter) {
		if (!Character.toString(currentCharacter).equalsIgnoreCase(Character.toString(adjacentCharacter))) {
			return false;
		}
		return ((Character.isUpperCase(currentCharacter) && Character.isLowerCase(adjacentCharacter))
				|| (Character.isUpperCase(adjacentCharacter) && Character.isLowerCase(currentCharacter)));
	}
}
