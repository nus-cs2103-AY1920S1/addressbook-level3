package seedu.revision.testutil;

import static seedu.revision.logic.commands.CommandTestUtil.VALID_CATEGORY_GREENFIELD;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_CATEGORY_UML;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_DIFFICULTY_ALPHA;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BETA;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_QUESTION_ALPHA;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_QUESTION_BETA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.revision.model.AddressBook;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.Answer;

/**
 * A utility class containing a list of {@code Answerable} objects to be used in tests.
 */
public class TypicalAnswerables {
    private static final Answer correctAnswer = new Answer("Brownfield");
    private static final ArrayList<Answer> defaultCorrectAnswerList = new ArrayList<>(Arrays.asList(correctAnswer));
    private static final Answer wrongAnswerA = new Answer("Greenfield");
    private static final Answer wrongAnswerB = new Answer("Blackfield");
    private static final Answer wrongAnswerC = new Answer("Whitefield");
    private static final ArrayList<Answer> defaultWrongAnswerList = new ArrayList<>(
            Arrays.asList(wrongAnswerA, wrongAnswerB, wrongAnswerC));

    public static final Answerable A_ANSWERABLE = new McqBuilder().withQuestion("If a subclass imposes more "
            + "restrictive conditions than its parent class, it violates Liskov Substitution Principle.")
            .withCorrectAnswerList(defaultCorrectAnswerList).withWrongAnswerList(defaultWrongAnswerList)
            .withDifficulty("1").withCategories("LSP", "SOLID", "Week 9").build();
    public static final Answerable B_ANSWERABLE = new McqBuilder().withQuestion("Brownfield or Greenfield?")
            .withCorrectAnswerList(defaultCorrectAnswerList).withWrongAnswerList(defaultWrongAnswerList)
            .withDifficulty("1").withCategories("field", "introduction").build();
    public static final Answerable C_ANSWERABLE = new McqBuilder().withQuestion("Carl Kurz").withDifficulty("1")
            .withCorrectAnswerList(defaultCorrectAnswerList).withWrongAnswerList(defaultWrongAnswerList).build();
    public static final Answerable E_ANSWERABLE = new McqBuilder().withQuestion("Elle Meyer").withDifficulty("1")
            .withCorrectAnswerList(defaultCorrectAnswerList).withWrongAnswerList(defaultWrongAnswerList).build();
    public static final Answerable F_ANSWERABLE = new McqBuilder().withQuestion("Fiona Kunz").withDifficulty("1")
            .withCorrectAnswerList(defaultCorrectAnswerList).withWrongAnswerList(defaultWrongAnswerList).build();

    // Manually added
    public static final Answerable H_ANSWERABLE = new McqBuilder().withQuestion("Hoon Meier")
            .withDifficulty("1").withCorrectAnswerList(defaultCorrectAnswerList)
            .withWrongAnswerList(defaultWrongAnswerList).build();
    public static final Answerable I_ANSWERABLE = new McqBuilder().withQuestion("Ida Mueller")
            .withDifficulty("1").withCorrectAnswerList(defaultCorrectAnswerList)
            .withWrongAnswerList(defaultWrongAnswerList).build();

    // Manually added - Answerable's details found in {@code CommandTestUtil}
    public static final Answerable ALPHA = new McqBuilder().withQuestion(VALID_QUESTION_ALPHA)
            .withDifficulty(VALID_DIFFICULTY_ALPHA).withCorrectAnswerList(defaultCorrectAnswerList)
            .withWrongAnswerList(defaultWrongAnswerList).withCategories(VALID_CATEGORY_UML).build();
    public static final Answerable BETA = new McqBuilder().withQuestion(VALID_QUESTION_BETA)
            .withDifficulty(VALID_DIFFICULTY_BETA).withCorrectAnswerList(defaultCorrectAnswerList)
            .withWrongAnswerList(defaultWrongAnswerList)
            .withCategories(VALID_CATEGORY_GREENFIELD, VALID_CATEGORY_UML).build();

    private TypicalAnswerables() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical answerables.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Answerable answerable : getTypicalAnswerables()) {
            ab.addAnswerable(answerable);
        }
        return ab;
    }

    public static List<Answerable> getTypicalAnswerables() {
        return new ArrayList<>(Arrays.asList(A_ANSWERABLE, B_ANSWERABLE, C_ANSWERABLE,
                E_ANSWERABLE, F_ANSWERABLE));
    }
}
