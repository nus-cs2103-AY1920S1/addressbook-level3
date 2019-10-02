package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.flashcard.FlashCard;

/**
 * Adds a flashCard to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a flashCard to the address book. "
            + "Parameters: "
            + PREFIX_QUESTION + "QUESTION "
            + PREFIX_ANSWER + "ANSWER "
            + PREFIX_RATING + "RATING "
            + "[" + PREFIX_CATEGORY + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_QUESTION + "End-to-end delay "
            + PREFIX_ANSWER + "2L/R (assuming no other delay) "
            + PREFIX_RATING + "good "
            + PREFIX_CATEGORY + "CS2105 "
            + PREFIX_CATEGORY + "computerNetworking";

    public static final String MESSAGE_SUCCESS = "New flashCard added: %1$s";
    public static final String MESSAGE_DUPLICATE_FLASHCARD = "This flashCard already exists in the address book";

    private final FlashCard toAdd;

    /**
     * Creates an AddCommand to add the specified {@code FlashCard}
     */
    public AddCommand(FlashCard flashCard) {
        requireNonNull(flashCard);
        toAdd = flashCard;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasFlashcard(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_FLASHCARD);
        }

        model.addFlashCard(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }

    @Override
    public String toString() {
        return toAdd.toString();
    }
}
