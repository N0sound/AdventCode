package com.alex.adventCode;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class DayNineTest {

	private DayNine fixture;

	@Before
	public void setUp() throws Exception {
		fixture = new DayNine();
	}

	@Test
	public void shouldGetWinningScore() {
		assertEquals(32, fixture.getWinningScore(9, 25));
		assertEquals(8317, fixture.getWinningScore(10, 1618));
		assertEquals(146373, fixture.getWinningScore(13, 7999));
		assertEquals(2764, fixture.getWinningScore(17, 1104));
		assertEquals(54718, fixture.getWinningScore(21, 6111));
		assertEquals(37305, fixture.getWinningScore(30, 5807));

		assertEquals(388024, fixture.getWinningScore(470, 72170));
		assertEquals(3180929875L, fixture.getWinningScore(470, 7217000));

	}
}
