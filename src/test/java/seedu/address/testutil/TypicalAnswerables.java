package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.answerable.Answer;
import seedu.address.model.answerable.AnswerSet;
import seedu.address.model.answerable.Answerable;

/**
 * A utility class containing a list of {@code Answerable} objects to be used in tests.
 */
public class TypicalAnswerables {
    private static final Answer correctAnswer = new Answer("CORRECT");
    private static final Set<Answer> correctAnswerSet = new HashSet<>(Arrays.asList(correctAnswer));
    private static final Answer wrongAnswer = new Answer("WRONG");
    private static final Set<Answer> wrongAnswerSet = new HashSet<>(Arrays.asList(wrongAnswer));
    private static final AnswerSet defaultAnswerSet = new AnswerSet(correctAnswerSet, wrongAnswerSet);

    public static final Answerable A_ANSWERABLE = new AnswerableBuilder().withQuestion("A Question?")
            .withAnswerSet(defaultAnswerSet).withCategory("Category A").withDifficulty("1")
            .withTags("easy").build();
    public static final Answerable B_ANSWERABLE = new AnswerableBuilder().withQuestion("Brownfield or Greenfield?")
            .withAnswerSet(defaultAnswerSet).withCategory("Requirements").withDifficulty("2")
            .withTags("field", "introduction").build();
    public static final Answerable C_ANSWERABLE = new AnswerableBuilder().withQuestion("Carl Kurz").withDifficulty("1")
            .withAnswerSet(defaultAnswerSet).withCategory("wall street").build();
    public static final Answerable E_ANSWERABLE = new AnswerableBuilder().withQuestion("Elle Meyer").withDifficulty("1")
            .withAnswerSet(defaultAnswerSet).withCategory("michegan ave").build();
    public static final Answerable F_ANSWERABLE = new AnswerableBuilder().withQuestion("Fiona Kunz").withDifficulty("1")
            .withAnswerSet(defaultAnswerSet).withCategory("little tokyo").build();

    // Manually added
    public static final Answerable H_ANSWERABLE = new AnswerableBuilder().withQuestion("Hoon Meier").withDifficulty("1")
            .withAnswerSet(defaultAnswerSet).withCategory("little india").build();
    public static final Answerable I_ANSWERABLE = new AnswerableBuilder().withQuestion("Ida Mueller").withDifficulty("1")
            .withAnswerSet(defaultAnswerSet).withCategory("chicago ave").build();

    // Manually added - Answerable's details found in {@code CommandTestUtil}
    public static final Answerable ALPHA = new AnswerableBuilder().withQuestion(VALID_QUESTION_AMY).withDifficulty(VALID_DIFFICULTY_AMY)
            .withAnswerSet(defaultAnswerSet).withCategory(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Answerable BETA = new AnswerableBuilder().withQuestion(VALID_QUESTION_BOB).withDifficulty(VALID_DIFFICULTY_BOB)
            .withAnswerSet(defaultAnswerSet).withCategory(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

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
