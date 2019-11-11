package seedu.address.logic.quiz.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.quiz.parser.CliSyntax.PREFIX_KEYWORD;

import seedu.address.commons.core.Messages;
import seedu.address.model.quiz.Model;
import seedu.address.model.quiz.person.NameContainsKeywordsPredicate;


/**
 * Finds and lists all questions in modulo whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all questions whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [INSTRUCTION_KEYWORD] " + PREFIX_KEYWORD
            + " [SEARCH_KEYWORD]\n"
            + "Example: " + COMMAND_WORD
            + " question, answer, tag " + PREFIX_KEYWORD
            + "lecture, tomorrow";

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
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
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
