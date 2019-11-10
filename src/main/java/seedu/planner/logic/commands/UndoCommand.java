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
 * An undo command to revert the changes made in the most recent command.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Latest command successfully undone!";
    public static final String MESSAGE_FAILURE = "No commands to undo.";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD,
            "Undo the last command used.",
            COMMAND_WORD,
            COMMAND_WORD
    );

    public static final CommandInformation COMMAND_INFORMATION = new CommandInformation(COMMAND_WORD);

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (CommandHistory.isEmptyUndoStack()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        Event undoableEvent = CommandHistory.getUndoEvent();
        CommandHistory.addToRedoStack(undoableEvent); //undoable event added to redo stack
        UndoableCommand commandToUndo = undoableEvent.undo();
        return commandToUndo.execute(model);
    }
}
