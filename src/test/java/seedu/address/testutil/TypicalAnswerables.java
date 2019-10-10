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
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.answerable.Answerable;

/**
 * A utility class containing a list of {@code Answerable} objects to be used in tests.
 */
public class TypicalAnswerables {

    public static final Answerable ALICE = new AnswerableBuilder().withQuestion("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withDifficulty("94351253")
            .withTags("friends").build();
    public static final Answerable BENSON = new AnswerableBuilder().withQuestion("Benson Meier")
            .withAnswer("placeholder answer").withAddress("311, Clementi Ave 2, #02-25").withDifficulty("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Answerable CARL = new AnswerableBuilder().withQuestion("Carl Kurz").withDifficulty("95352563")
            .withAnswer("placeholder answer").withAddress("wall street").build();
    public static final Answerable DANIEL = new AnswerableBuilder().withQuestion("Daniel Meier").withDifficulty("87652533")
            .withAnswer("placeholder answer").withAddress("10th street").withTags("friends").build();
    public static final Answerable ELLE = new AnswerableBuilder().withQuestion("Elle Meyer").withDifficulty("9482224")
            .withAnswer("placeholder answer").withAddress("michegan ave").build();
    public static final Answerable FIONA = new AnswerableBuilder().withQuestion("Fiona Kunz").withDifficulty("9482427")
            .withAnswer("placeholder answer").withAddress("little tokyo").build();
    public static final Answerable GEORGE = new AnswerableBuilder().withQuestion("George Best").withDifficulty("9482442")
            .withAnswer("placeholder answer").withAddress("4th street").build();

    // Manually added
    public static final Answerable HOON = new AnswerableBuilder().withQuestion("Hoon Meier").withDifficulty("8482424")
            .withAnswer("placeholder answer").withAddress("little india").build();
    public static final Answerable IDA = new AnswerableBuilder().withQuestion("Ida Mueller").withDifficulty("8482131")
            .withAnswer("placeholder answer").withAddress("chicago ave").build();

    // Manually added - Answerable's details found in {@code CommandTestUtil}
    public static final Answerable AMY = new AnswerableBuilder().withQuestion(VALID_QUESTION_AMY).withDifficulty(VALID_DIFFICULTY_AMY)
            .withAnswer("placeholder answer").withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Answerable BOB = new AnswerableBuilder().withQuestion(VALID_QUESTION_BOB).withDifficulty(VALID_DIFFICULTY_BOB)
            .withAnswer("placeholder answer").withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
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
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
