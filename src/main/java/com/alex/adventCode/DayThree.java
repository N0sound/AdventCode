package com.alex.adventCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class DayThree {

	private static final String AREA_DELIMITER = "x";
	private static final String COORDINATE_DELIMITER = ",";
	private static final String LINE_DELIMITER = " ";

	public long getOverLapFabricArea(File input) throws FileNotFoundException {
		Map<Coordinate, Integer> fabricAreaToNumberOfTimesOverlappedMap = getFabricAreaToTimesOverlappedMap(input);
		return getNumberOfOverlapFabricTiles(fabricAreaToNumberOfTimesOverlappedMap);
	}

	public String getFabricIdWithoutOverlap(File input) throws FileNotFoundException {
		Map<Coordinate, Integer> fabricAreaToNumberOfTimesOverlappedMap = getFabricAreaToTimesOverlappedMap(input);
		Set<Coordinate> overlappedFabricArea = fabricAreaToNumberOfTimesOverlappedMap.entrySet().stream()
				.filter(entry -> entry.getValue() > 1).map(entry -> entry.getKey()).collect(Collectors.toSet());

		try (Scanner scanner = new Scanner(input)) {
			while (scanner.hasNextLine()) {
				String[] line = scanner.nextLine().split(LINE_DELIMITER);

				String[] coordinates = line[2].split(COORDINATE_DELIMITER);
				int x_coordinate = Integer.parseInt(coordinates[0]);
				int y_coordinate = Integer.parseInt(coordinates[1]);

				String[] area = line[3].split(AREA_DELIMITER);
				int width = Integer.parseInt(area[0]);
				int height = Integer.parseInt(area[1]);

				boolean isOverlapped = false;
				for (int n = x_coordinate; n < width + x_coordinate; n++) {
					if (isOverlapped) {
						break;
					}
					for (int m = y_coordinate; m < height + y_coordinate; m++) {
						Coordinate tile = new Coordinate(n, m);
						if (overlappedFabricArea.contains(tile)) {
							isOverlapped = true;
							break;
						}

					}
				}
				if (!isOverlapped) {
					return line[0];
				}
			}
		}
		return StringUtils.EMPTY;
	}

	private Map<Coordinate, Integer> getFabricAreaToTimesOverlappedMap(File input) throws FileNotFoundException {
		Map<Coordinate, Integer> tilesToTimesOverlappedMap = new HashMap<>();
		try (Scanner scanner = new Scanner(input)) {
			while (scanner.hasNextLine()) {
				String[] line = scanner.nextLine().split(LINE_DELIMITER);

				String[] coordinates = line[2].split(COORDINATE_DELIMITER);
				int x_coordinate = Integer.parseInt(coordinates[0]);
				int y_coordinate = Integer.parseInt(coordinates[1]);

				String[] area = line[3].split(AREA_DELIMITER);
				int width = Integer.parseInt(area[0]);
				int height = Integer.parseInt(area[1]);

				for (int n = x_coordinate; n < width + x_coordinate; n++) {
					for (int m = y_coordinate; m < height + y_coordinate; m++) {
						Coordinate tile = new Coordinate(n, m);
						if (tilesToTimesOverlappedMap.containsKey(tile)) {
							Integer numberOfOverlap = tilesToTimesOverlappedMap.get(tile);
							tilesToTimesOverlappedMap.put(tile, ++numberOfOverlap);
						} else {
							tilesToTimesOverlappedMap.put(tile, 1);
						}
					}
				}
			}
		}
		return tilesToTimesOverlappedMap;
	}

	private long getNumberOfOverlapFabricTiles(Map<Coordinate, Integer> tiles) {
		return tiles.entrySet().stream().filter(entry -> entry.getValue() > 1).count();
	}

	private static class Coordinate {
		private final int x;
		private final int y;

		public Coordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Coordinate other = (Coordinate) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}
	}
}
