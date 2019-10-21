package seedu.address.statistics;

/**
 * Represents user actions that affect the game.
 * New actions should be added to correctly be shown in GameStatistics.
 * @see seedu.address.logic.commands.gamecommands.GuessCommandResult
 * @see seedu.address.logic.commands.gamecommands.SkipCommandResult
 */
public enum GameDataType {
    GUESS,
    SKIP
}
