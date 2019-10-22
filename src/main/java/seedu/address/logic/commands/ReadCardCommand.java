package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.card.Card;
import seedu.address.model.card.Description;


/**
 * Reads a password identified using it's displayed index from the password book.
 */
public class ReadCardCommand extends Command {
    public static final String COMMAND_WORD = "read";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " : Opens and accesses the card identified by "
            + "the description used in the display list. \n"
            + "Parameters: DESCRIPTION (must be alphanumeric)"
            + "Example: " + COMMAND_WORD + " POSB Debit";

    public static final String MESSAGE_SUCCESS = "%1$s";

    private final Description description;

    public ReadCardCommand(Description description) {
        this.description = description;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Card> lastShownList = model.getFilteredCardList();

        if (!lastShownList.contains(new Card(description))) {
            throw new CommandException(Messages.MESSAGE_INVALID_CARD_DISPLAYED);
        }

        Card cardToRead = lastShownList.get(lastShownList.indexOf(new Card(description)));
        return new CommandResult(String.format(MESSAGE_SUCCESS, cardToRead.toNonAsterixString()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadCardCommand // instanceof handles nulls
                && description.equals(((ReadCardCommand) other).description)); // state check
    }
}
