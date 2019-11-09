package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.lesson.LessonContainsKeywordsPredicate;

/**
 * Finds and lists all lessons in notebook which contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindLessonCommand extends Command {
    public static final String COMMAND_WORD = "findlesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all lessons which contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " math";

    private final LessonContainsKeywordsPredicate predicate;

    public FindLessonCommand(LessonContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredLessonList(predicate);
        String displayList = model.displayLessons();
        String result = String.format(Messages.MESSAGE_LESSONS_LISTED_OVERVIEW, model.getFilteredLessonList().size())
                + "\n" + displayList;
        return new CommandResult(
                String.format(result));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindLessonCommand // instanceof handles nulls
                && predicate.equals(((FindLessonCommand) other).predicate)); // state check
    }
}
