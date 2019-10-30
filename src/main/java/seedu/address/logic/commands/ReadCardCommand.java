package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CVC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.card.Card;
import seedu.address.model.card.Cvc;
import seedu.address.model.card.Description;


/**
 * Reads a card identified using it's displayed description from the card book.
 */
public class ReadCardCommand extends Command {
    public static final String COMMAND_WORD = "read";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " : Opens and accesses the card identified by "
            + "the description used in the display list. Checks against"
            + "provided CVC as second level of security\n"
            + "Parameters: d/DESCRIPTION v/CVC"
            + "Example: " + COMMAND_WORD + " " + PREFIX_DESCRIPTION + "POSB Debit "
            + PREFIX_CVC + "256";

    public static final String MESSAGE_SUCCESS = "%1$s";

    private final Cvc cvc;
    private final Description description;

    public ReadCardCommand(Cvc cvc, Description description) {
        this.cvc = cvc;
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

        if (!cardToRead.getNonEncryptedCvc().equals(cvc.getNonEncryptedCvc())) {
            throw new CommandException(Messages.MESSAGE_INVALID_CVC_DISPLAYED);
        }

        return CommandResult.builder(String.format(MESSAGE_SUCCESS, cardToRead.toNonAsterixString()))
                .read()
                .setObject(cardToRead)
                .build();

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadCardCommand // instanceof handles nulls
                && description.equals(((ReadCardCommand) other).description)); // state check
    }
}
