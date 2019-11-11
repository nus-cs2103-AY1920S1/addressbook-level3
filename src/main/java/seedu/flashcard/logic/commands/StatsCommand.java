package seedu.flashcard.logic.commands;

import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.flashcard.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;

import java.util.Set;

import seedu.flashcard.logic.CommandHistory;
import seedu.flashcard.logic.commands.exceptions.CommandException;
import seedu.flashcard.model.Model;
import seedu.flashcard.model.tag.Tag;

/**
 * Command to calculate and display stats
 */
public class StatsCommand extends Command {
    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": list all statistics under the given parameters."
            + "if no parameter is given it will list the overall statistics\n"
            + "Parameters:" + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TAG + "software engineering";

    public static final String MESSAGE_SUCCESS = "Generated statistics successfully";

    public static final String MESSAGE_FAIL = "I'm sorry there is nothing to calculate please refer below.\n\n"
            + MESSAGE_USAGE;

    private final Set<Tag> target;

    public StatsCommand (Set<Tag> target) {
        this.target = target;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        if (target == null) {
            model.updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
        } else {
            model.updateFilteredFlashcardList(model.getHasTagPredicate(target));
        }

        if (model.getFilteredFlashcardList().size() == 0) {
            return new CommandResult(MESSAGE_FAIL);
        }
        return new CommandResult(model.generateStatistics(), false, false, true);

    }

    @Override
    public boolean equals(Object other) {
        if (target == null && ((StatsCommand) other).target == null) {
            return true;
        }
        return other == this // short circuit if same object
                || (other instanceof StatsCommand// instanceof handles nulls
                && target.equals(((StatsCommand) other).target));
    }


}
