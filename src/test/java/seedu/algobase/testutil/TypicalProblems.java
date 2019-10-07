package seedu.algobase.testutil;

import static seedu.algobase.logic.commands.CommandTestUtil.VALID_AUTHOR_QUICK_SORT;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_DESCRIPTION_QUICK_SORT;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_NAME_QUICK_SORT;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_REMARK_QUICK_SORT;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_SOURCE_QUICK_SORT;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_WEBLINK_QUICK_SORT;

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
            .build();
}
