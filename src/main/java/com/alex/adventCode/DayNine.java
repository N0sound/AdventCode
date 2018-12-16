package com.alex.adventCode;

import java.util.LinkedHashMap;
import java.util.Map;

public class DayNine {

	public long getWinningScore(int playerCount, long maxMarbleValue) {
		Map<Integer, Long> players = createPlayers(playerCount);
		playMarbles(players, maxMarbleValue);
		return players.entrySet().stream().map(entry -> entry.getValue()).max(Long::compareTo).orElse(0L);
	}

	private void playMarbles(Map<Integer, Long> players, long maxMarbleValue) {
		int marbleValue = 0;
		Marble currentMarble = setUpInitialMarble();
		while (marbleValue < maxMarbleValue) {
			for (Map.Entry<Integer, Long> player : players.entrySet()) {
				marbleValue++;

				if (marbleValue % 23 == 0) {
					Marble marbleToRemove = currentMarble.counterClockwise;
					for (int i = 0; i < 6; i++) {
						marbleToRemove = marbleToRemove.counterClockwise;
					}

					Marble counterClockwise = marbleToRemove.counterClockwise;
					Marble clockwise = marbleToRemove.clockwise;

					counterClockwise.setClockwise(clockwise);
					clockwise.setCounterClockwise(counterClockwise);

					players.put(player.getKey(), player.getValue() + marbleValue + marbleToRemove.value);
					currentMarble = clockwise;
				} else {
					Marble newMarble = new Marble(marbleValue);

					Marble firstClockwise = currentMarble.clockwise;
					Marble secondClockwise = firstClockwise.clockwise;

					newMarble.setClockwise(secondClockwise);
					newMarble.setCounterClockwise(firstClockwise);

					firstClockwise.setClockwise(newMarble);
					secondClockwise.setCounterClockwise(newMarble);

					currentMarble = newMarble;
				}
				if (maxMarbleValue == marbleValue) {
					return;
				}
			}
		}
	}

	private Marble setUpInitialMarble() {
		Marble currentMarble = new Marble(0);
		currentMarble.clockwise = currentMarble;
		currentMarble.counterClockwise = currentMarble;
		return currentMarble;
	}

	private Map<Integer, Long> createPlayers(int playerCount) {
		Map<Integer, Long> playerToScore = new LinkedHashMap<>();
		for (int n = 1; n < playerCount + 1; n++) {
			playerToScore.put(n, 0L);
		}
		return playerToScore;
	}

	private static class Marble {

		private final int value;
		private Marble clockwise;
		private Marble counterClockwise;

		public Marble(int value) {
			this.value = value;
		}

		public void setClockwise(Marble marble) {
			clockwise = marble;
		}

		public void setCounterClockwise(Marble marbe) {
			counterClockwise = marbe;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + value;
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
			Marble other = (Marble) obj;
			if (value != other.value)
				return false;
			return true;
		}
	}
}
