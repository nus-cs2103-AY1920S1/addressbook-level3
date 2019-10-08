package seedu.algobase.testutil;

import static seedu.algobase.logic.commands.CommandTestUtil.VALID_AUTHOR_FACTORIAL;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_AUTHOR_QUICK_SORT;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_AUTHOR_TWO_SUM;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_DESCRIPTION_FACTORIAL;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_DESCRIPTION_QUICK_SORT;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TWO_SUM;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_DIFFICULTY_FACTORIAL;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_DIFFICULTY_QUICK_SORT;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_DIFFICULTY_TWO_SUM;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_NAME_FACTORIAL;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_NAME_QUICK_SORT;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_NAME_TWO_SUM;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_REMARK_FACTORIAL;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_REMARK_QUICK_SORT;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_REMARK_TWO_SUM;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_SOURCE_FACTORIAL;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_SOURCE_QUICK_SORT;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_SOURCE_TWO_SUM;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_WEBLINK_FACTORIAL;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_WEBLINK_QUICK_SORT;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_WEBLINK_TWO_SUM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.problem.Problem;

/**
 * A utility class containing a list of {@code Problem} objects to be used in tests.
 */
public class TypicalProblems {
    public static final Problem QUICK_SORT = new ProblemBuilder()
        .withName(VALID_NAME_QUICK_SORT)
        .withDescription(VALID_DESCRIPTION_QUICK_SORT)
        .withAuthor(VALID_AUTHOR_QUICK_SORT)
        .withWeblink(VALID_WEBLINK_QUICK_SORT)
        .withRemark(VALID_REMARK_QUICK_SORT)
        .withSource(VALID_SOURCE_QUICK_SORT)
        .withDifficulty(VALID_DIFFICULTY_QUICK_SORT)
        .build();
    public static final Problem TWO_SUM = new ProblemBuilder()
        .withName(VALID_NAME_TWO_SUM)
        .withDescription(VALID_DESCRIPTION_TWO_SUM)
        .withAuthor(VALID_AUTHOR_TWO_SUM)
        .withWeblink(VALID_WEBLINK_TWO_SUM)
        .withRemark(VALID_REMARK_TWO_SUM)
        .withSource(VALID_SOURCE_TWO_SUM)
        .withDifficulty(VALID_DIFFICULTY_TWO_SUM)
        .build();

    public static final Problem FACTORIAL = new ProblemBuilder()
        .withName(VALID_NAME_FACTORIAL)
        .withDescription(VALID_DESCRIPTION_FACTORIAL)
        .withAuthor(VALID_AUTHOR_FACTORIAL)
        .withWeblink(VALID_WEBLINK_FACTORIAL)
        .withRemark(VALID_REMARK_FACTORIAL)
        .withSource(VALID_SOURCE_FACTORIAL)
        .withDifficulty(VALID_DIFFICULTY_FACTORIAL)
        .build();

    /**
     * Returns an{@code AlgoBase} with all typical problems.
     */
    public static AlgoBase getTypicalAlgoBase() {
        AlgoBase algoBase = new AlgoBase();
        for (Problem problem : getTypicalProblems()) {
            algoBase.addProblem(problem);
        }
        return algoBase;
    }

    public static List<Problem> getTypicalProblems() {
        return new ArrayList<>(Arrays.asList(QUICK_SORT, TWO_SUM, FACTORIAL));
    }
}
