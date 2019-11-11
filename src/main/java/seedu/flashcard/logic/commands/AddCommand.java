package seedu.flashcard.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_CHOICE;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_DEFINITION;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.flashcard.logic.CommandHistory;
import seedu.flashcard.logic.commands.exceptions.CommandException;
import seedu.flashcard.model.Model;
import seedu.flashcard.model.flashcard.Flashcard;

/**
 * Command to add a new model.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a flashcard to a flashcard list. "
            + "Parameters: "
            + PREFIX_QUESTION + "QUESTION "
            + "[" + PREFIX_CHOICE + "CHOICE]... "
            + PREFIX_DEFINITION + "DEFINITION "
            + "[" + PREFIX_TAG + "TAG]... "
            + PREFIX_ANSWER + "ANSWER\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_QUESTION + "Refactor "
            + PREFIX_DEFINITION + "An important concept for professional programmers "
            + PREFIX_CHOICE + "Make codes neater "
            + PREFIX_CHOICE + "Test for for bugs in the system "
            + PREFIX_CHOICE + "Change a mathematical formula "
            + PREFIX_ANSWER + "Make codes neater "
            + PREFIX_TAG + "Software Engineering ";

    public static final String MESSAGE_SUCCESS = "New flashcard added.";
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
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasFlashcard(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_FLASHCARD);
        }

        model.addFlashcard(toAdd);
        model.commitFlashcardList();
        return new CommandResult(MESSAGE_SUCCESS, false, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddCommand
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
