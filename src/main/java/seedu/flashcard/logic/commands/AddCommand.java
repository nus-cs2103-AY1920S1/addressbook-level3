package seedu.flashcard.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_DEFINITION;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_WORD;

import seedu.flashcard.model.Model;
import seedu.flashcard.logic.commands.exceptions.CommandException;
import seedu.flashcard.model.flashcard.Flashcard;

/**
 * Command to add a new model.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a flashcard to a flashcard list. "
            + "Parameters: "
            + PREFIX_WORD + "WORD "
            + PREFIX_DEFINITION + "DEFINITION "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_WORD + "Refactor "
            + PREFIX_DEFINITION + "Make the codes be in a neater style without changing its functions. "
            + "This is different from debugging. "
            + PREFIX_TAG + "Software Engineering ";

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
     * @param model list of flashcards
     * @return the execution result
     * @throws CommandException error encountered during execution of command
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasFlashcard(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_FLASHCARD);
        }

        model.addFlashcard(toAdd);
        return new CommandResult(MESSAGE_SUCCESS, false, false);
    }
}
