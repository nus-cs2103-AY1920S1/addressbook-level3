package seedu.revision.logic.commands.main;

import static seedu.revision.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.revision.logic.parser.CliSyntax.PREFIX_MODE;

import seedu.revision.logic.commands.Command;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.Model;
import seedu.revision.model.answerable.predicates.CategoryPredicate;
import seedu.revision.model.answerable.predicates.DifficultyPredicate;
import seedu.revision.model.quiz.Mode;

import java.util.function.Predicate;

/**
 * Command to start the quiz session.
 */

public class StartQuizCommand extends Command {

    public static final String COMMAND_WORD = "start";

//    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Starts quiz based on Category, "
//            + "Difficulty or Mode.\n"
//            + "Parameters: "
//            + PREFIX_CATEGORY + "CATEGORY "
//            + PREFIX_DIFFICULTY + "DIFFICULTY "
//            + PREFIX_MODE + "MODE\n"
//            + "Example: " + COMMAND_WORD + " "
//            + PREFIX_MODE + "normal";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Starts quiz based on Mode, if no Mode provided, "
        + "entire question bank will be initialised.\n"
            + "Parameters: "
            + PREFIX_MODE + "MODE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODE + "normal";

    private static final String MESSAGE_SUCCESS = "Starting Quiz!";

//    private CategoryPredicate categoryPredicate;
//    private DifficultyPredicate difficultyPredicate;
    private Mode mode;

//    /**
//     * Creates an AddCommand to add the specified {@code Answerable}
//     */
//    public StartQuizCommand(CategoryPredicate categoryPredicate, DifficultyPredicate difficultyPredicate, Mode mode) {
//        this.categoryPredicate = categoryPredicate;
//        this.difficultyPredicate = difficultyPredicate;
//        this.mode = mode;
//    }
    /**
     * Creates an AddCommand to add the specified {@code Answerable}
     */
    public StartQuizCommand(Mode mode) {
        this.mode = mode;
    }

    @Override
    public CommandResult execute(Model model) throws ParseException {

//        ListCommand quizList = new ListCommand(categoryPredicate, difficultyPredicate);
//        quizList.execute(model);

//        Predicate<>

        return new CommandResult(MESSAGE_SUCCESS, true, mode);

    }
}
