// @@author chrischenhui
package seedu.address.logic.commands.wordbankcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Creates a word bank.
 */
public class CreateCommand extends WordBankCommand {

    public static final String COMMAND_WORD = "create";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " WORDBANK.\n"
            + "Eg: " + COMMAND_WORD + " "
            + "vocabulary";

    public static final String MESSAGE_SUCCESS = "Created word bank: %1$s\n"
            + "Select a bank\n"
            + "Eg. select mybank";

    private final String wordBankName;

    /**
     * Creates a CreateCommand to add the specified {@code Card}
     */
    public CreateCommand(String name) {
        requireNonNull(name);
        this.wordBankName = name;
    }

    @Override
    public WordBankCommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasWordBank(wordBankName)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_WORD_BANK_NAME);
        }
        return new CreateCommandResult(String.format(MESSAGE_SUCCESS, wordBankName), wordBankName);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CreateCommand // instanceof handles nulls
                && wordBankName.equals(((CreateCommand) other).wordBankName));
    }
}
