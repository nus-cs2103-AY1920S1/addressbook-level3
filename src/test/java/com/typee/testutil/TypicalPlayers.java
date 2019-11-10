package com.typee.testutil;

import com.typee.game.Player;

/**
 * A utility class containing a list of {@code Player} objects to be used in tests.
 */
public class TypicalPlayers {

    public static final int START_OF_GAME_SCORE = 0;
    public static final int START_OF_GAME_HEALTH = 100;
    public static final Player START_OF_GAME = new PlayerBuilder().withScore(START_OF_GAME_SCORE)
            .withHealth(START_OF_GAME_HEALTH).build();
    public static final int MID_GAME_SCORE = 5000;
    public static final int MID_GAME_HEALTH = 50;
    public static final Player MID_GAME = new PlayerBuilder().withScore(MID_GAME_SCORE).withHealth(MID_GAME_HEALTH)
            .build();
    public static final int END_GAME_SCORE = 10000;
    public static final int END_GAME_HEALTH = 0;
    public static final Player END_GAME = new PlayerBuilder().withScore(END_GAME_SCORE).withHealth(END_GAME_HEALTH)
            .build();

    private TypicalPlayers() {
    } // prevents instantiation

}
