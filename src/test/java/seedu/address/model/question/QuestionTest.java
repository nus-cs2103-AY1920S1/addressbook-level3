package seedu.address.model.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.question.QuestionBuilder;

public class QuestionTest {

    private final Question openEndedQuestion = new QuestionBuilder().withQuestion("What is 1+1?")
        .withAnswer("2").build();
    private final Question mcqQuestion = new QuestionBuilder().withQuestion("What is 1+1?")
        .withAnswer("2").withType("mcq").withOptionA("A").withOptionB("B").withOptionC("C")
        .withOptionD("D").build();

    @Test
    public void initialise_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new OpenEndedQuestion(null, null));
        assertThrows(NullPointerException.class, ()
            -> new McqQuestion(null, null, null, null, null, null));
    }

    @Test
    public void setQuestion_withValidQuestion_success() {
        String question = "What is 2+2?";
        openEndedQuestion.setQuestion(question);

        assertEquals(openEndedQuestion.question, question);
    }

    @Test
    public void setAnswer_withValidQuestion_success() {
        String answer = "3";
        openEndedQuestion.setAnswer(answer);

        assertEquals(openEndedQuestion.answer, answer);
    }

    @Test
    public void getQuestion_validQuestion_returnTrue() {
        assertTrue(mcqQuestion.getQuestion().equals("What is 1+1?"));
        assertTrue(openEndedQuestion.getQuestion().equals("What is 1+1?"));
    }

    @Test
    public void getAnswer_validQuestion_returnTrue() {
        assertTrue(mcqQuestion.getAnswer().equals("2"));
        assertTrue(openEndedQuestion.getAnswer().equals("2"));
    }

    @Test
    public void duplicate_sameQuestionType_returnTrue() {
        assertTrue(openEndedQuestion.equals(openEndedQuestion.duplicate()));
        assertTrue(mcqQuestion.equals(mcqQuestion.duplicate()));
    }

    @Test
    public void equals_differentQuestionTypes_returnFalse() {
        assertFalse(openEndedQuestion.equals(mcqQuestion));
        assertFalse(mcqQuestion.equals(openEndedQuestion));
    }

    @Test
    public void equals_sameQuestion_returnTrue() {
        Question questionToTest = new QuestionBuilder().withQuestion("What is 1+1?")
            .withAnswer("2").build();
        assertTrue(openEndedQuestion.equals(questionToTest));

        questionToTest = new QuestionBuilder().withQuestion("What is 1+1?")
            .withAnswer("2").withType("mcq").withOptionA("A").withOptionB("B").withOptionC("C")
            .withOptionD("D").build();
        assertTrue(mcqQuestion.equals(questionToTest));
    }

    @Test
    public void equals_sameQuestionDifferentAnswer_returnFalse() {
        Question questionToTest = new QuestionBuilder().withQuestion("What is 1+1?")
            .withAnswer("3").build();
        assertFalse(openEndedQuestion.equals(questionToTest));

        questionToTest = new QuestionBuilder().withQuestion("What is 1+1?")
            .withAnswer("3").withType("mcq").withOptionA("A").withOptionB("B").withOptionC("C")
            .withOptionD("D").build();
        assertFalse(mcqQuestion.equals(questionToTest));
    }

    @Test
    public void equals_sameMcqQuestionDifferentOptions_returnFalse() {
        // Different option A
        Question questionToTest = new QuestionBuilder().withQuestion("What is 1+1?")
            .withAnswer("2").withType("mcq").withOptionA("1").withOptionB("B").withOptionC("C")
            .withOptionD("D").build();
        assertFalse(mcqQuestion.equals(questionToTest));

        // Different option B
        questionToTest = new QuestionBuilder().withQuestion("What is 1+1?")
            .withAnswer("2").withType("mcq").withOptionA("A").withOptionB("2").withOptionC("C")
            .withOptionD("D").build();
        assertFalse(mcqQuestion.equals(questionToTest));

        // Different option C
        questionToTest = new QuestionBuilder().withQuestion("What is 1+1?")
            .withAnswer("2").withType("mcq").withOptionA("A").withOptionB("B").withOptionC("3")
            .withOptionD("D").build();
        assertFalse(mcqQuestion.equals(questionToTest));

        // Different option D
        questionToTest = new QuestionBuilder().withQuestion("What is 1+1?")
            .withAnswer("2").withType("mcq").withOptionA("A").withOptionB("B").withOptionC("C")
            .withOptionD("4").build();
        assertFalse(mcqQuestion.equals(questionToTest));
    }
}
