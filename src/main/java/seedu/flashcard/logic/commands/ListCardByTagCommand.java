package seedu.flashcard.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.flashcard.logic.commands.exceptions.CommandException;
import seedu.flashcard.model.Model;
import seedu.flashcard.model.tag.Tag;

/**
 * The command to list out all flashcards under a given tag
 */
public class ListCardByTagCommand extends Command {

    public static final String COMMAND_WORD = "listbytag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": list all flashcards under the given tags."
            + "Parameters:" + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TAG + "software engineering";

    public static final String MESSAGE_SUCCESS = "Listed all flashcards with this tag. "
            + "If no flashcard is listed, it means no flashcard is under this tag.";

    private final Set<Tag> target;

    public ListCardByTagCommand(Set<Tag> target) {
        this.target = target;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredFlashcardList(model.getHasTagPredicate(target));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
