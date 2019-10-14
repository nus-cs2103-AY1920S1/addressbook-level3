package seedu.flashcard.logic.commands;

import static java.util.Objects.requireNonNull;

<<<<<<< HEAD
import seedu.flashcard.model.Model;
=======
import seedu.flashcard.logic.commands.exceptions.CommandException;
import seedu.flashcard.model.FlashcardList;
import seedu.flashcard.model.flashcard.Flashcard;

/**
 * Command to add a new model.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a flashcard to a flashcard list. "
            + "Parameters: "
            + "/sq SHORT-RESPONSE QUESTION "
            + "/ans ANSWER\n"
            + "Example: " + COMMAND_WORD + " "
            + "/sq What is the highest mountain in the world? "
            + "/ans Mount Everest\n"
            + "OR\n"
            + "Parameters: "
            + "/mq MCQ QUESTION /a.XXXXXX /b.XXXXXX /c.XXXXXX /d.XXXXXX"
            + "/ans a"
            + "Example: " + COMMAND_WORD + " "
            + "/mq What is 1 + 1? /a.1 /b.2 /c.3 /d.4"
            + "/ans b\n";


    public static final String MESSAGE_SUCCESS = "New flashcard added: %1$s";
    public static final String MESSAGE_DUPLICATE_FLASHCARD = "This flashcard already exists in the flashcard list!";

    private final Flashcard toAdd;

    /**
     * Construct a new add flashcard command.
     * @param flashcard the flashcard to be added
     */
    public AddCommand (Flashcard flashcard) {
        requireNonNull(flashcard);
        this.toAdd = flashcard;
    }

    /**
     * Return the result from executing the add command.
     * @param flashcardList list of flashcards
     * @return the execution result
     * @throws CommandException error encountered during execution of command
     */
    @Override
    public CommandResult execute(FlashcardList flashcardList) throws CommandException {
        requireNonNull(flashcardList);

        if (flashcardList.(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_FLASHCARD);
        }

        flashcardList.addFlashcard(toAdd);
        return new CommandResult(MESSAGE_SUCCESS, false, false);
    }
}
