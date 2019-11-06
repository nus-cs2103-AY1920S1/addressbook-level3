package seedu.address.testutil;

import seedu.address.model.quiz.AddressQuizBook;
import seedu.address.model.quiz.person.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static seedu.address.logic.quiz.commands.CommandTestUtil.*;

/**
 * A utility class containing a list of {@code Question} objects to be used in tests.
 */
public class TypicalQuestion {

    public static final Question ALICE = new QuestionBuilder().withName("What is alice favourite fruit?")
            .withComment(null).withAnswer("Watermelon")
            .withCategory("Sec4").withType("normal")
            .withTags("friends").build();
    public static final Question BENSON = new QuestionBuilder().withName("What is benson favourite fruit?")
            .withComment(null).withAnswer("Banana")
            .withCategory("PrimarySch").withType("high")
            .withTags("owesMoney", "friends").build();
    public static final Question CARL = new QuestionBuilder().withName("What is carl favourite fruit?")
            .withType("low").withComment(null).withCategory("CS2002")
            .withAnswer("strawberry").build();
    public static final Question DANIEL = new QuestionBuilder().withName("What is daniel favourite fruit?")
            .withType("high").withComment("I really love this fruit")
            .withCategory("LSM1000").withAnswer("Mango").withTags("friends").build();
    public static final Question ELLE = new QuestionBuilder().withName("What is elle favourite fruit?").withType("low")
            .withComment("It tastes good").withCategory("IS1002").withAnswer("grape").build();
    public static final Question FIONA = new QuestionBuilder().withName("What is fiona favourite fruit?")
            .withType("low").withComment("Mom told me this one")
            .withCategory("PT1000").withAnswer("Lime").build();
    public static final Question GEORGE = new QuestionBuilder().withName("What is george favourite fruit?")
            .withType("high").withComment(null).withCategory("Random").withAnswer("None").build();

    // Manually added - Question's details found in {@code CommandTestUtil}
    public static final Question AMY = new QuestionBuilder().withName(VALID_NAME_AMY).withType(VALID_ANSWER_AMY)
            .withCategory(VALID_CATEGORY_AMY).withAnswer(VALID_TYPE_AMY).withTags(VALID_TAG_LECTURE).build();
    public static final Question BOB = new QuestionBuilder().withName(VALID_NAME_BOB).withType(VALID_ANSWER_BOB)
            .withCategory(VALID_CATEGORY_BOB).withAnswer(VALID_TYPE_BOB).withTags(VALID_TAG_LECTURE, VALID_TAG_TUTORIAL)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    public TypicalQuestion() {} // prevents instantiation

    /**
     * Returns an {@code AddressQuizBook} with all the typical persons.
     */
    public static AddressQuizBook getTypicalAddressQuizBook() {
        AddressQuizBook ab = new AddressQuizBook();
        for (Question question : getTypicalQuestions()) {
            ab.addQuestion(question);
        }
        return ab;
    }

    public static List<Question> getTypicalQuestions() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
