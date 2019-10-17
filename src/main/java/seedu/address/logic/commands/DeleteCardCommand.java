package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.card.Card;
import seedu.address.model.card.Description;

/**
 * Deletes a card identified using it's description from the card book.
 */
public class DeleteCardCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the card identified by the description used in the displayed card list.\n"
            + "Parameters: DESCRIPTION\n"
            + "Example: " + COMMAND_WORD + " POSB Debit";

    public static final String MESSAGE_DELETE_CARD_SUCCESS = "Deleted Card: %1$s";

    private final Description description;

    public DeleteCardCommand(Description description) {
        this.description = description;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Card> lastShownList = model.getFilteredCardList();

        if (!lastShownList.contains(new Card(description))) {
            throw new CommandException(Messages.MESSAGE_INVALID_CARD_DISPLAYED);
        }

        Card cardToDelete = lastShownList.get(lastShownList.indexOf(new Card(description)));
        model.deleteCard(cardToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_CARD_SUCCESS, cardToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCardCommand // instanceof handles nulls
                && description.equals(((DeleteCardCommand) other).description)); // state check
    }
}
