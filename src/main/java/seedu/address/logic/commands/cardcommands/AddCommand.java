// @@author chrischenhui
package seedu.address.logic.commands.cardcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_CARD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEANING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WORD;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.card.Card;

/**
 * Adds a card to the word bank.
 */
public class AddCommand extends CardCommand {

    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_SUCCESS = "New card added: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + PREFIX_WORD + "WORD "
            + PREFIX_MEANING + "MEANING "
            + "[" + PREFIX_TAG + "TAG] \n"
            + "Eg: " + COMMAND_WORD + " "
            + PREFIX_WORD + "CS2103 "
            + PREFIX_MEANING + "Software engineering NUS module code "
            + PREFIX_TAG + "CS";

    private final Card toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Card}.
     */
    public AddCommand(Card card) {
        requireNonNull(card);
        toAdd = card;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasCard(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CARD);
        }

        model.addCard(toAdd);
        return new CardCommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
