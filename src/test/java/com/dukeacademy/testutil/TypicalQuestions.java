package com.dukeacademy.testutil;

import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dukeacademy.model.QuestionBank;
import com.dukeacademy.model.question.Question;

/**
 * A utility class containing a list of {@code Question} objects to be used in tests.
 */
public class TypicalQuestions {

    public static final Question ALICE = new QuestionBuilder()
        .withTitle("Alice Pauline")
        .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
        .withPhone("94351253")
        .withTags("friends").build();
    public static final Question BENSON = new QuestionBuilder().withTitle("Benson Meier")
                                                               .withAddress("311, Clementi Ave 2, #02-25")
                                                               .withEmail("johnd@example.com").withPhone("98765432")
                                                               .withTags("owesMoney", "friends").build();
    public static final Question
        CARL = new QuestionBuilder().withTitle("Carl Kurz").withPhone("95352563")
                                    .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Question
        DANIEL = new QuestionBuilder().withTitle("Daniel Meier").withPhone("87652533")
                                      .withEmail("cornelia@example.com")
                                      .withAddress("10th street").withTags("friends").build();
    public static final Question
        ELLE = new QuestionBuilder().withTitle("Elle Meyer").withPhone("9482224")
                                    .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Question
        FIONA = new QuestionBuilder().withTitle("Fiona Kunz").withPhone("9482427")
                                     .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Question
        GEORGE = new QuestionBuilder().withTitle("George Best").withPhone("9482442")
                                      .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Question
        HOON = new QuestionBuilder().withTitle("Hoon Meier").withPhone("8482424")
                                    .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Question
        IDA = new QuestionBuilder().withTitle("Ida Mueller").withPhone("8482131")
                                   .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Question's details found in {@code CommandTestUtil}
    public static final Question
        AMY = new QuestionBuilder().withTitle(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                                   .withEmail(VALID_EMAIL_AMY)
                                   .withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Question
        BOB = new QuestionBuilder().withTitle(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                                   .withEmail(VALID_EMAIL_BOB)
                                   .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
                                   .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalQuestions() {} // prevents instantiation

    /**
     * Returns an {@code QuestionBank} with all the typical persons.
     */
    public static QuestionBank getTypicalQuestionBank() {
        QuestionBank ab = new QuestionBank();
        for (Question question : getTypicalPersons()) {
            ab.addQuestion(question);
        }
        return ab;
    }

    public static List<Question> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
