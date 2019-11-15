package seedu.revision.logic.commands.main;

import static java.util.Objects.requireNonNull;

import seedu.revision.commons.core.Messages;
import seedu.revision.logic.commands.Command;
import seedu.revision.model.Model;
import seedu.revision.model.answerable.predicates.QuestionContainsKeywordsPredicate;

/**
 * Finds and lists all answerables in test bank whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all answerables whose questions contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " greenfield brownfield";

    private final QuestionContainsKeywordsPredicate predicate;

    public FindCommand(QuestionContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredAnswerableList(predicate);
        return new CommandResultBuilder().withFeedBack(
                String.format(Messages.MESSAGE_ANSWERABLES_LISTED_OVERVIEW, model.getFilteredAnswerableList().size()))
                .build();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
