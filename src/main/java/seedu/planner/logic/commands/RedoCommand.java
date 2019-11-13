package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.autocomplete.CommandInformation;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.commands.util.HelpExplanation;
import seedu.planner.logic.events.Event;
import seedu.planner.model.Model;
//@@author OneArmyj
/**
 * An redo command to revert the changes made in the most recent undo command.
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Latest undo command successfully reverted!";
    public static final String MESSAGE_FAILURE = "No commands to redo.";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD,
            "Redo the last undo command.",
            COMMAND_WORD,
            COMMAND_WORD
    );

    public static final CommandInformation COMMAND_INFORMATION = new CommandInformation(COMMAND_WORD);

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (CommandHistory.isEmptyRedoStack()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        Event redoableEvent = CommandHistory.getRedoEvent();
        CommandHistory.addToUndoStack(redoableEvent); //redoable event added to undo stack
        UndoableCommand commandToUndo = redoableEvent.redo();
        return commandToUndo.execute(model);
    }
}
