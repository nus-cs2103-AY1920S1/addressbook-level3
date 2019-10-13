package com.dukeacademy.testutil;

import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_DIFFICULTY_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_STATUS_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_STATUS_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_TITLE_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_TITLE_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_TOPIC_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_TOPIC_BOB;

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
        .withDifficulty("123, Jurong West Ave 6, #08-111").withStatus("alice@example.com")
        .withTopic("94351253")
        .withTags("friends").build();
    public static final Question BENSON = new QuestionBuilder().withTitle("Benson Meier")
                                                               .withDifficulty("311, Clementi Ave 2, #02-25")
                                                               .withStatus("johnd@example.com").withTopic("98765432")
                                                               .withTags("owesMoney", "friends").build();
    public static final Question
        CARL = new QuestionBuilder().withTitle("Carl Kurz").withTopic("95352563")
                                    .withStatus("heinz@example.com").withDifficulty("wall street").build();
    public static final Question
        DANIEL = new QuestionBuilder().withTitle("Daniel Meier").withTopic("87652533")
                                      .withStatus("cornelia@example.com")
                                      .withDifficulty("10th street").withTags("friends").build();
    public static final Question
        ELLE = new QuestionBuilder().withTitle("Elle Meyer").withTopic("9482224")
                                    .withStatus("werner@example.com").withDifficulty("michegan ave").build();
    public static final Question
        FIONA = new QuestionBuilder().withTitle("Fiona Kunz").withTopic("9482427")
                                     .withStatus("lydia@example.com").withDifficulty("little tokyo").build();
    public static final Question
        GEORGE = new QuestionBuilder().withTitle("George Best").withTopic("9482442")
                                      .withStatus("anna@example.com").withDifficulty("4th street").build();

    // Manually added
    public static final Question
        HOON = new QuestionBuilder().withTitle("Hoon Meier").withTopic("8482424")
                                    .withStatus("stefan@example.com").withDifficulty("little india").build();
    public static final Question
        IDA = new QuestionBuilder().withTitle("Ida Mueller").withTopic("8482131")
                                   .withStatus("hans@example.com").withDifficulty("chicago ave").build();

    // Manually added - Question's details found in {@code CommandTestUtil}
    public static final Question
        AMY = new QuestionBuilder().withTitle(VALID_TITLE_AMY).withTopic(VALID_TOPIC_AMY)
                                   .withStatus(VALID_STATUS_AMY)
                                   .withDifficulty(VALID_DIFFICULTY_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Question
        BOB = new QuestionBuilder().withTitle(VALID_TITLE_BOB).withTopic(VALID_TOPIC_BOB)
                                   .withStatus(VALID_STATUS_BOB)
                                   .withDifficulty(VALID_DIFFICULTY_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
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
