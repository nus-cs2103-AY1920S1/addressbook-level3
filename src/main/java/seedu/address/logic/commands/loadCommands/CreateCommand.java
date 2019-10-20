package seedu.address.logic.commands.loadCommands;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.LoadCommand;
import seedu.address.logic.commands.appCommands.AddCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.card.Card;
import seedu.address.model.wordbank.WordBank;

import static java.util.Objects.requireNonNull;

public class CreateCommand extends LoadCommand {

    public static final String COMMAND_WORD = "create";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Create a word bank."
            + "Parameters: "
            + "word bank name.\n"
            + "Example: " + COMMAND_WORD + " "
            + "sample wordbank";

    public static final String MESSAGE_SUCCESS = "New word bank added: %1$s";
    public static final String MESSAGE_DUPLICATE_WORD_BANK = "This Word Bank name already exists in Dukemon";

    private final WordBank toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Card}
     */
    public CreateCommand(WordBank wordBank) {
        requireNonNull(wordBank);
        toAdd = wordBank;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Check for duplicate word bank
        if (model.getWordBankList().hasWordBank(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_WORD_BANK);
        }

        model.setWordBank(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CreateCommand // instanceof handles nulls
                && toAdd.equals(((CreateCommand) other).toAdd));
    }
}
