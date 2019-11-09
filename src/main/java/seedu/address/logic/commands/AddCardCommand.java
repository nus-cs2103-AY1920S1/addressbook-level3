package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CARDNUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CVC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRYDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.card.Card;

/**
 * Adds a card to the card book.
 */
public class AddCardCommand extends Command {
    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a card to the app. \n"
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_CARDNUMBER + "CARD NUMBER "
            + PREFIX_CVC + "CVC "
            + PREFIX_EXPIRYDATE + "EXPIRY DATE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "POSB Debit "
            + PREFIX_CARDNUMBER + "5283201212034958 "
            + PREFIX_CVC + "876 "
            + PREFIX_EXPIRYDATE + "12/25 "
            + PREFIX_TAG + "groceries";

    public static final String MESSAGE_SUCCESS = "New card added: %1$s";
    public static final String MESSAGE_DUPLICATE_CARD = "This card already exists in the app!";
    public static final String MESSAGE_DUPLICATE_CARD_DESCRIPTION = "Another card has the same description!";

    private final Card toAddCard;

    /**
     * Creates an AddCardCommand to add the specified {@code Card}
     */
    public AddCardCommand(Card card) {
        requireNonNull(card);
        toAddCard = card;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasCardDescription(toAddCard)) {
            throw new CommandException(MESSAGE_DUPLICATE_CARD_DESCRIPTION);
        }

        if (model.hasCard(toAddCard)) {
            throw new CommandException(MESSAGE_DUPLICATE_CARD);
        }

        model.addCard(toAddCard);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAddCard));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAddCard.equals(((AddCardCommand) other).toAddCard));
    }
}
