package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CARDNUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CVC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.card.Card;

/**
 * Adds a card to the card book.
 */
public class AddCardCommand extends Command {
    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a card to the app. "
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_CARDNUMBER + "CARD NUMBER "
            + PREFIX_CVC + "CVC "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "POSB Debit "
            + PREFIX_CARDNUMBER + "2723201212034958 "
            + PREFIX_CVC + "876 "
            + PREFIX_TAG + "use for groceries";

    public static final String MESSAGE_SUCCESS = "New card added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This card already exists in the app";

    private final Card toAddCard;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCardCommand(Card card) {
        requireNonNull(card);
        toAddCard = card;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // if (model.hasPerson(toAdd)) {
        //    throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        // }

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
