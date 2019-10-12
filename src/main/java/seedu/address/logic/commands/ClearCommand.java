package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.Context;
import seedu.address.model.AddressBook;
import seedu.deliverymans.commons.core.Messages;
import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * (to be added)
     */
    public static class SwitchCommand extends Command {

        public static final String COMMAND_WORD = "switch";

        public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switches the context for input"
                + "such that the input will only target commands of a certain context.\n"
                + "Parameters: CONTEXT (must be a valid context)\n"
                + "Example: " + COMMAND_WORD + " RESTAURANT";

        public static final String MESSAGE_ALREADY_IN_CURRENT_CONTEXT = "Already in current context: $s";

        public static final String MESSAGE_SUCCESS = "Switched to : $s context";

        public SwitchCommand(String args) {

        }

        @Override
        public CommandResult execute(Model model) throws CommandException {
            requireNonNull(model);
            Context context;

            try {
                context = Context.valueOf("");
            } catch (IllegalArgumentException e) {
                throw new CommandException(Messages.MESSAGE_INVALID_SWITCH_CONTEXT);

            }
            if (context.equals("model.getCurrentContext()")) { // get context of current model
                throw new CommandException(String.format(MESSAGE_ALREADY_IN_CURRENT_CONTEXT, context));
            }

            return new CommandResult(String.format(MESSAGE_SUCCESS, context));
        }

        @Override
        public boolean equals(Object other) {
            return other == this // short circuit if same object
                    || (other instanceof SwitchCommand // instanceof handles nulls
                );
        }
    }
}
