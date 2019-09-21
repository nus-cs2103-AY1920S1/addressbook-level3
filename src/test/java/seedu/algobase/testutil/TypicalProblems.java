package seedu.algobase.testutil;

import static seedu.algobase.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.problem.Problem;

/**
 * A utility class containing a list of {@code Problem} objects to be used in tests.
 */
public class TypicalProblems {

    public static final Problem ALICE = new ProblemBuilder().withName("Alice Pauline")
            .withDescription("123, Jurong West Ave 6, #08-111").withWeblink("alice@example.com")
            .withAuthor("94351253")
            .withTags("friends").build();
    public static final Problem BENSON = new ProblemBuilder().withName("Benson Meier")
            .withDescription("311, Clementi Ave 2, #02-25")
            .withWeblink("johnd@example.com").withAuthor("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Problem CARL = new ProblemBuilder().withName("Carl Kurz").withAuthor("95352563")
            .withWeblink("heinz@example.com").withDescription("wall street").build();
    public static final Problem DANIEL = new ProblemBuilder().withName("Daniel Meier").withAuthor("87652533")
            .withWeblink("cornelia@example.com").withDescription("10th street").withTags("friends").build();
    public static final Problem ELLE = new ProblemBuilder().withName("Elle Meyer").withAuthor("9482224")
            .withWeblink("werner@example.com").withDescription("michegan ave").build();
    public static final Problem FIONA = new ProblemBuilder().withName("Fiona Kunz").withAuthor("9482427")
            .withWeblink("lydia@example.com").withDescription("little tokyo").build();
    public static final Problem GEORGE = new ProblemBuilder().withName("George Best").withAuthor("9482442")
            .withWeblink("anna@example.com").withDescription("4th street").build();

    // Manually added
    public static final Problem HOON = new ProblemBuilder().withName("Hoon Meier").withAuthor("8482424")
            .withWeblink("stefan@example.com").withDescription("little india").build();
    public static final Problem IDA = new ProblemBuilder().withName("Ida Mueller").withAuthor("8482131")
            .withWeblink("hans@example.com").withDescription("chicago ave").build();

    // Manually added - Problem's details found in {@code CommandTestUtil}
    public static final Problem AMY = new ProblemBuilder().withName(VALID_NAME_AMY).withAuthor(VALID_PHONE_AMY)
            .withWeblink(VALID_EMAIL_AMY).withDescription(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Problem BOB = new ProblemBuilder().withName(VALID_NAME_BOB).withAuthor(VALID_PHONE_BOB)
            .withWeblink(VALID_EMAIL_BOB).withDescription(VALID_ADDRESS_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalProblems() {} // prevents instantiation

    /**
     * Returns an {@code AlgoBase} with all the typical problems.
     */
    public static AlgoBase getTypicalAlgoBase() {
        AlgoBase ab = new AlgoBase();
        for (Problem problem : getTypicalProblems()) {
            ab.addProblem(problem);
        }
        return ab;
    }

    public static List<Problem> getTypicalProblems() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
