// @@author chrischenhui
package seedu.address.logic.commands.cardcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Empties the word bank.
 */
public class ClearCommand extends CardCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Word bank has been cleared!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " \n";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.clearWordBank();
        model.clearWordBankStatistics();
        return new CardCommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClearCommand); // instanceof handles nulls
    }
}
