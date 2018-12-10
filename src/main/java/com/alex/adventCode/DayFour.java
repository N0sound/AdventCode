package com.alex.adventCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.Validate;

public class DayFour {

	private static final Pattern LINE_PATTERN = Pattern.compile("^\\[(.*)\\] (.*)$");
	private static final Pattern GUARD_ID_PATTERN = Pattern.compile("^Guard #(\\d{1,}+).*$");
	private static final SimpleDateFormat DATE_PARSER = new SimpleDateFormat("yyyy-MM-dd kk:mm");
	private static final String FALLS_ASLEEP = "falls asleep";
	private static final String WAKES_UP = "wakes up";

	public int generateHashForGuardAsleepTheMost(File input) throws FileNotFoundException, ParseException {
		Map<Integer, Integer> idToTotalMinutesAsleep = new HashMap<>();
		Map<Integer, SleepReport> idToSleepReport = new HashMap<>();

		parseInput(input, idToTotalMinutesAsleep, idToSleepReport);
		return generateHash(idToTotalMinutesAsleep, idToSleepReport);
	}

	public int generateHashForGuardAsleepAtMostForGivenMinute(File input) throws FileNotFoundException, ParseException {
		Map<Integer, Integer> idToTotalMinutesAsleep = new HashMap<>();
		Map<Integer, SleepReport> idToSleepReport = new HashMap<>();

		parseInput(input, idToTotalMinutesAsleep, idToSleepReport);
		return generateHash(idToSleepReport);
	}

	private void parseInput(File input, Map<Integer, Integer> idToTotalMinutesAsleep,
			Map<Integer, SleepReport> idToSleepReport) throws ParseException, FileNotFoundException {
		try (Scanner scanner = new Scanner(input)) {

			Integer currentId = null;

			while (scanner.hasNextLine()) {
				Matcher matcher = getNextLineMatcher(scanner);
				String info = matcher.group(2);

				Matcher guardMatcher = GUARD_ID_PATTERN.matcher(info);
				if (guardMatcher.matches()) {
					currentId = Integer.parseInt(guardMatcher.group(1));
					continue;
				}

				if (FALLS_ASLEEP.equals(info)) {
					if (scanner.hasNextLine()) {
						Matcher sleepMatcher = getNextLineMatcher(scanner);
						Validate.isTrue(WAKES_UP.equals(sleepMatcher.group(2)));

						int startMin = getMinutes(matcher);
						int endMin = getMinutes(sleepMatcher);
						int totalMinutesAsleep = endMin - startMin;

						updateTotalMinutesAsleep(currentId, totalMinutesAsleep, idToTotalMinutesAsleep);
						updateSleepReport(currentId, startMin, endMin, idToSleepReport);
					}
				}
			}
		}
	}

	private Matcher getNextLineMatcher(Scanner scanner) {
		String line = scanner.nextLine();
		Matcher matcher = LINE_PATTERN.matcher(line);
		Validate.isTrue(matcher.matches());
		return matcher;
	}

	private int generateHash(Map<Integer, Integer> idToTotalMinutesAsleep, Map<Integer, SleepReport> idToSleepReport) {
		Integer id = Collections.max(idToTotalMinutesAsleep.entrySet(), Map.Entry.comparingByValue()).getKey();
		int mostSleptMinute = idToSleepReport.get(id).getMostSleptMinute().minute;
		return id * mostSleptMinute;
	}

	@SuppressWarnings("deprecation")
	private int getMinutes(Matcher matcher) throws ParseException {
		String time = matcher.group(1);
		Date date = DATE_PARSER.parse(time);
		return date.getMinutes();
	}

	private void updateTotalMinutesAsleep(Integer id, int totalMinutesAsleep,
			Map<Integer, Integer> idToTotalMinutesAsleep) {
		Integer totalTimeAsleep = idToTotalMinutesAsleep.getOrDefault(id, 0);
		idToTotalMinutesAsleep.put(id, totalTimeAsleep + totalMinutesAsleep);
	}

	private void updateSleepReport(Integer id, int start, int end, Map<Integer, SleepReport> idToSleepReport) {
		SleepReport sleepReport = idToSleepReport.getOrDefault(id, new SleepReport());
		sleepReport.addSleepMinutes(start, end);
		idToSleepReport.put(id, sleepReport);
	}

	private int generateHash(Map<Integer, SleepReport> idToSleepReport) {
		int currentId = -1;
		int frequency = -1;
		int minute = -1;
		for (Entry<Integer, SleepReport> entry : idToSleepReport.entrySet()) {
			Integer id = entry.getKey();
			SleepReportMinute sleepReportMinute = entry.getValue().getMostSleptMinute();
			if (sleepReportMinute.frequency > frequency) {
				currentId = id;
				minute = sleepReportMinute.minute;
				frequency = sleepReportMinute.frequency;
			}
		}
		return currentId * minute;
	}

	private static class SleepReport {
		private final int[] sleepMinutes = new int[60];

		public void addSleepMinutes(int start, int end) {
			for (int n = start; n < end; n++) {
				Integer value = sleepMinutes[n];
				sleepMinutes[n] = ++value;
			}
		}

		public SleepReportMinute getMostSleptMinute() {
			int minute = -1;
			int frequency = -1;
			for (int n = 0; n < sleepMinutes.length; n++) {
				Integer timesAsleep = sleepMinutes[n];
				if (frequency < timesAsleep) {
					minute = n;
					frequency = timesAsleep;
				}
			}
			return new SleepReportMinute(minute, frequency);
		}
	}

	private static class SleepReportMinute {
		private final int minute;
		private final int frequency;

		public SleepReportMinute(int minute, int frequency) {
			this.minute = minute;
			this.frequency = frequency;
		}
	}
}
