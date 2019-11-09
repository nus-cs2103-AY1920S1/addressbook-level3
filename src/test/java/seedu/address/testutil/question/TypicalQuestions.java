package seedu.address.testutil.question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.question.Question;
import seedu.address.model.question.SavedQuestions;

/**
 * A utility class containing a list of {@code Question} objects to be used in tests.
 */
public class TypicalQuestions {

    public static final Question QUESTION_ONE = new QuestionBuilder()
            .withQuestion("What year was Singapore independent?")
            .withAnswer("1965")
            .withType("open")
            .build();
    public static final Question QUESTION_TWO = new QuestionBuilder()
            .withQuestion("What is A in terms of CAP?")
            .withAnswer("5")
            .withType("open")
            .build();
    public static final Question QUESTION_THREE = new QuestionBuilder()
            .withQuestion("How many years is a CS degree?")
            .withAnswer("4")
            .withType("mcq")
            .withOptionA("1").withOptionB("2").withOptionC("3").withOptionD("4")
            .build();
    public static final Question QUESTION_FOUR = new QuestionBuilder()
            .withQuestion("What is the emergency number?")
            .withAnswer("999")
            .withType("mcq")
            .withOptionA("990").withOptionB("999").withOptionC("902").withOptionD("111")
            .build();
    public static final Question OPEN_ENDED = new QuestionBuilder().withQuestion("What is 1+1?")
        .withAnswer("2").withType("open").build();
    public static final Question MCQ = new QuestionBuilder().withQuestion("What is 1+1?")
        .withAnswer("B").withType("mcq").withOptionA("1").withOptionB("2").withOptionC("3")
        .withOptionD("4").build();

    public static final Question NOT_IN_TYPICAL = new QuestionBuilder().withQuestion("What is 9+9?")
        .withAnswer("18").build();

    /**
     * Returns an {@code SavedQuestions} with all the typical questions.
     */
    public static SavedQuestions getTypicalSavedQuestions() {
        SavedQuestions sq = new SavedQuestions();
        for (Question question : getTypicalQuestions()) {
            sq.addQuestion(question);
        }
        return sq;
    }

    /**
     * Returns an {@code SavedQuestions} with all the typical questions.
     */
    public static SavedQuestions getTypicalSavedQuestionsForQuiz() {
        SavedQuestions sq = new SavedQuestions();
        for (Question question : getTypicalQuestionsForQuiz()) {
            sq.addQuestion(question);
        }
        return sq;
    }

    public static List<Question> getTypicalQuestions() {
        return new ArrayList<>(Arrays.asList(OPEN_ENDED, MCQ));
    }

    public static List<Question> getTypicalQuestionsForQuiz() {
        return new ArrayList<>(Arrays.asList(QUESTION_ONE, QUESTION_TWO, QUESTION_THREE, QUESTION_FOUR));
    }
}
