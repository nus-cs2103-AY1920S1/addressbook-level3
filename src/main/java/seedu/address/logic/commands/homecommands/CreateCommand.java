package seedu.address.logic.commands.homecommands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.HomeCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Creates a word bank.
 */
public class CreateCommand extends HomeCommand {

    public static final String COMMAND_WORD = "create";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Create a word bank."
            + "Parameters: "
            + "word bank name.\n"
            + "Example: " + COMMAND_WORD + " "
            + "sample wordbank";

    public static final String MESSAGE_SUCCESS = "New word bank added: %1$s";
    public static final String MESSAGE_DUPLICATE_WORD_BANK = "This Word Bank name already exists in Dukemon";

    private final String name;
    /**
     * Creates a CreateCommand to add the specified {@code Card}
     */
    public CreateCommand(String name) {
        requireNonNull(name);
        this.name = name;
    }

    @Override
    public HomeCommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasWordBank(name)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_WORD_BANK_NAME);
        }
        return new HomeCommandResult(String.format(MESSAGE_SUCCESS, name));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CreateCommand // instanceof handles nulls
                && name.equals(((CreateCommand) other).name));
    }
}
