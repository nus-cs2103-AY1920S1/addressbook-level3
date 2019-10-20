package seedu.address.statistics;

/**
 * Represents user actions that affect the game.
 * New actions should be added to correctly be shown in GameStatistics.
 * @see seedu.address.logic.commands.gameCommands.GuessCommandResult
 * @see seedu.address.logic.commands.gameCommands.SkipCommandResult
 */
public enum GameDataType {
    GUESS,
    SKIP
}
