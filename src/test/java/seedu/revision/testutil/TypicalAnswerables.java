package seedu.revision.testutil;

import static seedu.revision.logic.commands.CommandTestUtil.VALID_QUESTION_ALPHA;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_QUESTION_BETA;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_DIFFICULTY_ALPHA;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BETA;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_CATEGORY_UML;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_CATEGORY_GREENFIELD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.revision.model.AddressBook;
import seedu.revision.model.answerable.Answer;
import seedu.revision.model.answerable.Answerable;

/**
 * A utility class containing a list of {@code Answerable} objects to be used in tests.
 */
public class TypicalAnswerables {
    private static final Answer correctAnswer = new Answer("CORRECT");
    private static final Set<Answer> defaultCorrectAnswerSet = new HashSet<>(Arrays.asList(correctAnswer));
    private static final Answer wrongAnswer = new Answer("WRONG");
    private static final Set<Answer> defaultWrongAnswerSet = new HashSet<>(Arrays.asList(wrongAnswer));

    public static final Answerable A_ANSWERABLE = new AnswerableBuilder().withQuestion("If a subclass imposes more "
            + "restrictive conditions than its parent class, it violates Liskov Substitution Principle.")
            .withCorrectAnswerSet(defaultCorrectAnswerSet).withWrongAnswerSet(defaultWrongAnswerSet)
            .withDifficulty("1").withCategories("LSP", "SOLID", "Week 9").build();
    public static final Answerable B_ANSWERABLE = new AnswerableBuilder().withQuestion("Brownfield or Greenfield?")
            .withCorrectAnswerSet(defaultCorrectAnswerSet).withWrongAnswerSet(defaultWrongAnswerSet)
            .withDifficulty("1").withCategories("field", "introduction").build();
    public static final Answerable C_ANSWERABLE = new AnswerableBuilder().withQuestion("Carl Kurz").withDifficulty("1")
            .withCorrectAnswerSet(defaultCorrectAnswerSet).withWrongAnswerSet(defaultWrongAnswerSet).build();
    public static final Answerable E_ANSWERABLE = new AnswerableBuilder().withQuestion("Elle Meyer").withDifficulty("1")
            .withCorrectAnswerSet(defaultCorrectAnswerSet).withWrongAnswerSet(defaultWrongAnswerSet).build();
    public static final Answerable F_ANSWERABLE = new AnswerableBuilder().withQuestion("Fiona Kunz").withDifficulty("1")
            .withCorrectAnswerSet(defaultCorrectAnswerSet).withWrongAnswerSet(defaultWrongAnswerSet).build();

    // Manually added
    public static final Answerable H_ANSWERABLE = new AnswerableBuilder().withQuestion("Hoon Meier").withDifficulty("1")
            .withCorrectAnswerSet(defaultCorrectAnswerSet).withWrongAnswerSet(defaultWrongAnswerSet).build();
    public static final Answerable I_ANSWERABLE = new AnswerableBuilder().withQuestion("Ida Mueller").withDifficulty("1")
            .withCorrectAnswerSet(defaultCorrectAnswerSet).withWrongAnswerSet(defaultWrongAnswerSet).build();

    // Manually added - Answerable's details found in {@code CommandTestUtil}
    public static final Answerable ALPHA = new AnswerableBuilder().withQuestion(VALID_QUESTION_ALPHA)
            .withDifficulty(VALID_DIFFICULTY_ALPHA) .withCorrectAnswerSet(defaultCorrectAnswerSet)
            .withWrongAnswerSet(defaultWrongAnswerSet).withCategories(VALID_CATEGORY_UML).build();
    public static final Answerable BETA = new AnswerableBuilder().withQuestion(VALID_QUESTION_BETA)
            .withDifficulty(VALID_DIFFICULTY_BETA).withCorrectAnswerSet(defaultCorrectAnswerSet)
            .withWrongAnswerSet(defaultWrongAnswerSet)
            .withCategories(VALID_CATEGORY_GREENFIELD, VALID_CATEGORY_UML).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

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
