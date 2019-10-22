package seedu.revision.logic.commands.main;

import seedu.revision.logic.commands.Command;
import seedu.revision.logic.parser.main.AddressBookParser;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.Model;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.predicates.CategoryPredicate;
import seedu.revision.model.answerable.predicates.DifficultyPredicate;
import seedu.revision.model.quiz.Mode;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.revision.model.Model.PREDICATE_SHOW_ALL_ANSWERABLE;

public class StartQuizCommand extends Command {

    public static final String COMMAND_WORD = "start";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Starts quiz based on Category, "
            + "Difficulty or Mode.\n"
            + "Parameters: "
            + PREFIX_CATEGORY + "CATEGORY "
            + PREFIX_DIFFICULTY + "DIFFICULTY "
            + PREFIX_MODE + "MODE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODE + "normal";

    private static final String MESSAGE_SUCCESS = "Starting Quiz!";

    private static Object currentIteratedAnswerable;

    private CategoryPredicate categoryPredicate;
    private DifficultyPredicate difficultyPredicate;
    private Mode mode;

    /**
     * Creates an AddCommand to add the specified {@code Answerable}
     */
    public StartQuizCommand(CategoryPredicate categoryPredicate, DifficultyPredicate difficultyPredicate, Mode mode) {
        this.categoryPredicate = categoryPredicate;
        this.difficultyPredicate = difficultyPredicate;
        this.mode = mode;
    }

    @Override
    public CommandResult execute(Model model) throws ParseException {

        ListCommand quizList = new ListCommand(categoryPredicate, difficultyPredicate);
        quizList.execute(model);

        return new CommandResult(MESSAGE_SUCCESS, false, false, true);

    }
}
