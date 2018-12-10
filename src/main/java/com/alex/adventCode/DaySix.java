package com.alex.adventCode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DaySix {

	private static final String EMPTY_CELL = ".";
	private static final String SAFE_CELL = "#";
	private static final String LINE_DELIMITER = ",";

	public int findLargestFiniteArea(File input, int dimensions) throws IOException {
		List<Seed> seeds = getSeeds(input);
		String[][] board = createBoard(dimensions);
		populateInitialBoard(seeds, board);
		populateBoard(seeds, board, dimensions);

		return getMaxFiniteArea(seeds);
	}

	public int findLargestAreaWithinDistance(File input, int dimensions, int distance) throws IOException {
		List<Seed> seeds = getSeeds(input);
		String[][] board = createBoard(dimensions);
		populateInitialBoard(seeds, board);

		return getAreaWithinDistance(seeds, board, dimensions, distance);
	}

	private List<Seed> getSeeds(File input) throws IOException {
		return Files.lines(Paths.get(input.getAbsolutePath())).map(line -> line.split(LINE_DELIMITER))
				.map(values -> new Seed(new Coordinate(Integer.valueOf(values[0]), Integer.valueOf(values[1]))))
				.collect(Collectors.toList());
	}

	private String[][] createBoard(int dimensions) {
		String[][] board = new String[dimensions][dimensions];
		for (int n = 0; n < dimensions; n++) {
			for (int m = 0; m < dimensions; m++) {
				board[n][m] = EMPTY_CELL;
			}
		}
		return board;
	}

	private void populateInitialBoard(List<Seed> seeds, String[][] board) {
		// ASCII code for A == 065
		int num = 65;
		for (Seed seed : seeds) {
			seed.setId(Character.toString((char) num));
			Coordinate coordinate = seed.seedCoordinate;
			board[coordinate.x][coordinate.y] = seed.id;
			num++;
		}
	}

	private void populateBoard(List<Seed> seeds, String[][] board, int dimensions) {
		for (int y = 0; y < dimensions; y++) {
			for (int x = 0; x < dimensions; x++) {
				populateCell(seeds, board, dimensions, x, y);
			}
		}
		printBoard(board);
	}

	private void populateCell(List<Seed> seeds, String[][] board, int dimensions, int x, int y) {
		if (!board[x][y].equals(EMPTY_CELL)) {
			return;
		}
		Seed closestSeed = null;
		int minDistance = Integer.MAX_VALUE;
		boolean closeToMoreThanOne = false;
		for (Seed seed : seeds) {
			Coordinate coordinate = seed.seedCoordinate;
			int distance = getManhattanDistance(x, y, coordinate.x, coordinate.y);
			if (distance < minDistance) {
				minDistance = distance;
				closeToMoreThanOne = false;
				closestSeed = seed;
			} else if (minDistance == distance) {
				closeToMoreThanOne = true;
			}
		}

		if (!closeToMoreThanOne) {
			board[x][y] = closestSeed.id;
			closestSeed.addToCount();
			int maxDimension = dimensions - 1;
			if (x == 0 || y == 0 || x == maxDimension || y == maxDimension) {
				closestSeed.setInfinite();
			}
		}
	}

	private int getManhattanDistance(int x, int y, int other_x, int other_y) {
		int x_absolute = Math.abs(x - other_x);
		int y_absolute = Math.abs(y - other_y);
		return x_absolute + y_absolute;
	}

	private Integer getMaxFiniteArea(List<Seed> seeds) {
		return seeds.stream().filter(seed -> seed.isFinite).map(seed -> seed.count)
				.max(Comparator.comparing(Integer::valueOf)).get();
	}

	private void printBoard(String[][] board) {
		int length = board.length;
		for (int y = 0; y < length; y++) {
			for (int x = 0; x < length; x++) {
				System.out.print("[" + board[x][y] + "]");
			}
			System.out.println();
		}
		System.out.println();
	}

	private int getAreaWithinDistance(List<Seed> seeds, String[][] board, int dimensions, int distance) {
		int area = 0;
		for (int y = 0; y < dimensions; y++) {
			for (int x = 0; x < dimensions; x++) {
				int totalDistance = 0;
				for (Seed seed : seeds) {
					Coordinate coordinate = seed.seedCoordinate;
					totalDistance = totalDistance + getManhattanDistance(x, y, coordinate.x, coordinate.y);
				}
				if (totalDistance < distance) {
					board[x][y] = SAFE_CELL;
					area++;
				}
			}
		}
		printBoard(board);
		return area;
	}

	private static class Seed {

		private final Coordinate seedCoordinate;

		private int count = 1;
		private String id = "";
		private boolean isFinite = true;

		public Seed(Coordinate seedCoordinate) {
			this.seedCoordinate = seedCoordinate;
		}

		public void addToCount() {
			count++;
		}

		public void setInfinite() {
			isFinite = false;
		}

		public void setId(String id) {
			this.id = id;
		}

		@Override
		public String toString() {
			return String.format("Seed: id=%s; isFinite=%s; x=%d; y=%d", id, isFinite, seedCoordinate.x,
					seedCoordinate.y);
		}
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
