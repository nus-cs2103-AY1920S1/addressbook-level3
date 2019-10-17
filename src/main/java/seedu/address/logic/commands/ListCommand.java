package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ANSWERABLE;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.answerable.Answerable;
import seedu.address.model.answerable.predicates.CategoryPredicate;
import seedu.address.model.answerable.predicates.DifficultyPredicate;

/**
 * Lists all answerables in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all questions which belong to the "
            + "specified category and difficulty (case-insensitive, optional) "
            + "and displays them as a list with index numbers.\n"
            +"Parameters: "
            + PREFIX_CATEGORY + "CATEGORY "
            + PREFIX_DIFFICULTY + "DIFFICULTY "
            + "Example: " + COMMAND_WORD
            + PREFIX_CATEGORY + "UML "
            + PREFIX_DIFFICULTY + "2 ";

    public static final String MESSAGE_SUCCESS = "Listed all answerables";

    private CategoryPredicate categoryPredicate;
    private DifficultyPredicate difficultyPredicate;

    public ListCommand(CategoryPredicate categoryPredicate, DifficultyPredicate difficultyPredicate) {
        this.categoryPredicate = categoryPredicate;
        this.difficultyPredicate = difficultyPredicate;
    }

    public ListCommand(CategoryPredicate categoryPredicate) {
        this.categoryPredicate = categoryPredicate;
    }

    public ListCommand(DifficultyPredicate difficultyPredicate) {
        this.difficultyPredicate = difficultyPredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Predicate<Answerable>> predicates = new ArrayList<>();
        if (categoryPredicate != null) {
            predicates.add(categoryPredicate);
        }
        if (difficultyPredicate != null) {
            predicates.add(difficultyPredicate);
        }

        // if predicate list is empty, it will just return a predicate which shows all answerable
        Predicate<Answerable> combinedPredicate = predicates.stream()
                                                            .reduce(PREDICATE_SHOW_ALL_ANSWERABLE, Predicate::and);

        model.updateFilteredAnswerableList(combinedPredicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_ANSWERABLES_LISTED_OVERVIEW, model.getFilteredAnswerableList().size()));
    }
}
