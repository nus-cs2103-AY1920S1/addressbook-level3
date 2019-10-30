package seedu.address.logic.commands.questioncommands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.question.DifficultyContainsKeywordsPredicate;

/**
 * Finds and lists all questions in address book whose difficulty contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindDifficultyCommand extends Command {
    public static final String COMMAND_WORD = "difficulty";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all questions whose bodies contain any of "
            + "the specified difficulty (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " 1 + 1 =";

    private final DifficultyContainsKeywordsPredicate predicate;

    public FindDifficultyCommand(DifficultyContainsKeywordsPredicate predicate) {
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
                || (other instanceof FindDifficultyCommand // instanceof handles nulls
                && predicate.equals(((FindDifficultyCommand) other).predicate)); // state check
    }
}
