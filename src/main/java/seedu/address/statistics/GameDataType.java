package seedu.address.statistics;

/**
 * Represents user actions that affect the game.
 * New actions should be added to correctly be shown in GameStatistics.
 * @see seedu.address.logic.commands.game.GuessCommandResult
 * @see seedu.address.logic.commands.game.SkipCommandResult
 */
public enum GameDataType {
    GUESS,
    SKIP
}
