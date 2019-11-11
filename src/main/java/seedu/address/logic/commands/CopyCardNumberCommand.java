package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CVC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ClipboardUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.card.Card;
import seedu.address.model.card.Cvc;
import seedu.address.model.card.Description;

/**
 * Copies card number from card identified.
 */
public class CopyCardNumberCommand extends Command {

    public static final String COMMAND_WORD = "copy";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " : Opens and copies the card number of the card identified by "
            + "the description or index used in the display list. \nChecks against "
            + "provided CVC as second level of security\n"
            + "Parameters: d/DESCRIPTION v/CVC\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_DESCRIPTION + "POSB Debit "
            + PREFIX_CVC + "023\n"
            + "Parameters: INDEX v/CVC\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_CVC + "023";

    public static final String MESSAGE_SUCCESS = "Copied card number of %s to clipboard";

    private final Cvc cvc;
    private final Description description;
    private final Index targetIndex;

    public CopyCardNumberCommand(Cvc cvc, Description description) {
        this.cvc = cvc;
        this.description = description;
        this.targetIndex = null;
    }

    public CopyCardNumberCommand(Cvc cvc, Index index) {
        this.cvc = cvc;
        this.description = null;
        this.targetIndex = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Card> lastShownList = model.getFilteredCardList();

        assert((description != null) || (targetIndex != null));

        Card cardToRead;
        if (targetIndex != null) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
            }
            cardToRead = lastShownList.get(targetIndex.getZeroBased());
        } else {
            if (!lastShownList.contains(new Card(description))) {
                throw new CommandException(Messages.MESSAGE_INVALID_CARD_DISPLAYED);
            }
            cardToRead = lastShownList.get(lastShownList.indexOf(new Card(description)));
        }

        if (!cardToRead.getNonEncryptedCvc().equals(cvc.getNonEncryptedCvc())) {
            throw new CommandException(Messages.MESSAGE_INVALID_CVC_DISPLAYED);
        }

        ClipboardUtil.copyToClipboard(cardToRead.getCardNumberWithoutDashes(), null);
        return CommandResult.builder(String.format(MESSAGE_SUCCESS, cardToRead.getDescription()))
                .read()
                .setObject(cardToRead)
                .setIndex(targetIndex)
                .build();
    }
}
