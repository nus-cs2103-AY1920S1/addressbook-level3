package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
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
            + ": Deletes the card identified by the description or index used "
            + "in the displayed card list.\n"
            + "Parameters: DESCRIPTION\n"
            + "Example: " + COMMAND_WORD + " POSB Debit\n"
            + "Parameters: INDEX (must be positive integer\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_CARD_SUCCESS = "Deleted Card: %1$s";

    private final Description description;
    private final Index targetIndex;

    public DeleteCardCommand(Description description) {
        this.description = description;
        this.targetIndex = null;
    }

    public DeleteCardCommand(Index index) {
        this.description = null;
        this.targetIndex = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Card> lastShownList = model.getFilteredCardList();

        Card cardToDelete;

        assert((targetIndex != null) || (description != null));

        if (targetIndex != null) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
            }
            cardToDelete = lastShownList.get(targetIndex.getZeroBased());
        } else {
            if (!lastShownList.contains(new Card(description))) {
                throw new CommandException(Messages.MESSAGE_INVALID_CARD_DISPLAYED);
            }
            cardToDelete = lastShownList.get(lastShownList.indexOf(new Card(description)));
        }

        model.deleteCard(cardToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_CARD_SUCCESS, cardToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCardCommand // instanceof handles nulls
                && description.equals(((DeleteCardCommand) other).description)
                && targetIndex.equals(((DeleteCardCommand) other).targetIndex)); // state check
    }
}
