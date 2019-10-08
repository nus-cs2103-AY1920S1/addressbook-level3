package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import seedu.address.model.Model;
import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.Question;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;


/**
 * Adds a flashcard to the StudyBuddy application.
 */
public class AddFlashcardCommand extends Command {

    public static final String COMMAND_WORD = "add_flashcard";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new flashcard "
           + "from the question and answer provided. "
            + "Flashcard will be assigned a unique id when created.\n"
            + "Parameters: " + PREFIX_QUESTION + "[QUESTION] "
            +  PREFIX_ANSWER + "[ANSWER]\n"
            + "Example: " + COMMAND_WORD + PREFIX_QUESTION + "What is 6 + 19? "
            +  PREFIX_ANSWER + "25";

    public static final String MESSAGE_SUCCESS = "New flashcard added: %1$s";
    public static final String MESSAGE_DUPLICATE_FLASHCARD = "This flashcard already exists in the application";

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

        model.addFlashcard(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddFlashcardCommand // instanceof handles nulls
                && toAdd.equals(((AddFlashcardCommand) other).toAdd));
    }
}