package seedu.deliverymans.logic.commands.universal;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;

/**
 * Lists the actions that can be undone till.
 */
public class UndoListCommand extends Command {

    public static final String COMMAND_WORD = "-undo_list";

    private static final String MESSAGE_HEADER = "Here are the actions that can be undone or redone, use "
            + UndoTillCommand.COMMAND_WORD + " INDEX to undo or redo till that action.";
    private static final String MESSAGE_NOTHING_TO_UNDO = "There is nothing to undo/redo";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Optional<String>> undoList = model.getUndoList();
        if (undoList.size() == 1) {
            throw new CommandException(MESSAGE_NOTHING_TO_UNDO);
        }

        StringJoiner joiner = new StringJoiner("\n");
        boolean undo = true;
        for (int i = 0; i < undoList.size(); i++) {
            Optional<String> action = undoList.get(i);
            if (action.isEmpty()) {
                undo = false;
            } else if (undo) {
                joiner.add(i + 1 + ": (Undo) " + action.get());
            } else {
                joiner.add(i + 1 + ": (Redo) " + action.get());
            }
        }

        return new CommandResult(MESSAGE_HEADER + "\n" + joiner.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof UndoListCommand; // instanceof handles nulls
    }
}
