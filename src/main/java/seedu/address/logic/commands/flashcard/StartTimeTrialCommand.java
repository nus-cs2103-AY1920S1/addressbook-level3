package seedu.address.logic.commands.flashcard;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.TIMETRIAL;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;
import seedu.address.model.flashcard.FlashcardContainsTagPredicate;

/**
 * Loads the next flashcard into the window.
 */
public class StartTimeTrialCommand extends Command {

    public static final String COMMAND_WORD = TIMETRIAL;
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Starts a time trial of all all the flashcards"
            + " with all the specified tag."
            + "Parameters: " + PREFIX_TAG + "[TAG] "
            + "Example: " + COMMAND_WORD + " " + PREFIX_TAG + "CS2100";

    public static final String MESSAGE_SUCCESS = "Time trial started";

    private String[] tagKeywords;

    private final FlashcardContainsTagPredicate tagPredicate;
    /**
     * Creates an AddFlashcardCommand to add the specified {@code Flashcard}
     */
    public StartTimeTrialCommand(FlashcardContainsTagPredicate tagPredicate, String[] tagKeywords) {
        requireAllNonNull(tagPredicate, tagKeywords);

        this.tagKeywords = tagKeywords;
        this.tagPredicate = tagPredicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.collectTaggedFlashcards(tagPredicate);

        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartTimeTrialCommand // instanceof handles nulls
                && tagKeywords.equals(((StartTimeTrialCommand) other).tagKeywords)
                && tagPredicate.equals(((StartTimeTrialCommand) other).tagPredicate));
    }
}
