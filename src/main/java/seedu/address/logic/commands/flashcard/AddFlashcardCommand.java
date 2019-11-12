package seedu.address.logic.commands.flashcard;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.ADD;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandresults.FlashcardCommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;
import seedu.address.model.flashcard.Flashcard;

/**
 * Adds a flashcard to the StudyBuddy application.
 */
public class AddFlashcardCommand extends Command {

    public static final String COMMAND_WORD = ADD;
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new flashcard "
            + "from the question, answer and title provided. "
            + "Flashcard will be assigned a unique id when created.\n"
            + "Parameters: " + PREFIX_QUESTION + "[QUESTION] "
            + PREFIX_ANSWER + "[ANSWER] "
            + PREFIX_TITLE + "[TITLE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_QUESTION + "What is 6 + 19? "
            + PREFIX_ANSWER + "25 "
            + PREFIX_TITLE + "Basic addition question 1 "
            + PREFIX_TAG + "math";

    public static final String MESSAGE_SUCCESS = "New flashcard added: %1$s";
    public static final String MESSAGE_DUPLICATE_FLASHCARD = "A flashcard with the same title or question already "
            + "exists in Flashcards.";

    private static final Logger logger = LogsCenter.getLogger(AddFlashcardCommand.class);
    private final Flashcard toAdd;


    /**
     * Creates an AddFlashcardCommand to add the specified {@code Flashcard}
     */
    public AddFlashcardCommand(Flashcard flashcard) {
        requireAllNonNull(flashcard);

        this.toAdd = flashcard;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasFlashcard(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_FLASHCARD);
        }

        logger.info("Executing AddFlashcardCommand for the flashcard: " + toAdd);

        model.addFlashcard(toAdd);
        return new FlashcardCommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddFlashcardCommand // instanceof handles nulls
                && toAdd.equals(((AddFlashcardCommand) other).toAdd));
    }
}
