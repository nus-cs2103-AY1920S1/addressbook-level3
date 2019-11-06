package seedu.address.logic.commands.questioncommands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.question.QuestionContainsKeywordsPredicate;

/**
 * Finds and lists all questions in address book whose bodies contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindQuestionCommand extends Command {
    public static final String COMMAND_WORD = "findq";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all questions whose bodies contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " 1 + 1 =";

    private final QuestionContainsKeywordsPredicate predicate;

    public FindQuestionCommand(QuestionContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredQuestionList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_QUESTIONS_LISTED_OVERVIEW, model.getFilteredQuestionList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindQuestionCommand // instanceof handles nulls
                && predicate.equals(((FindQuestionCommand) other).predicate)); // state check
    }
}
