package seedu.address.logic.commands.question;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.question.Question;
import seedu.address.model.question.ReadOnlyQuestions;
import seedu.address.model.question.SavedQuestions;
import seedu.address.testutil.model.ModelStub;
import seedu.address.testutil.question.QuestionBuilder;

public class QuestionAddCommandTest {

    @Test
    public void constructor_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new QuestionAddCommand(null, null, null));
    }

    @Test
    public void execute_questionAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingQuestionAdded modelStub = new ModelStubAcceptingQuestionAdded();
        Question validQuestion = new QuestionBuilder().build();

        CommandResult commandResult = new QuestionAddCommand(validQuestion.getQuestion(),
            validQuestion.getAnswer(), "open").execute(modelStub);

        assertEquals(String.format(QuestionAddCommand.MESSAGE_SUCCESS, validQuestion),
            commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validQuestion), modelStub.questionsAdded);
    }

    @Test
    public void equals() {
        Question question = new QuestionBuilder().withQuestion("What is 1+1?").build();
        Question otherQuestion = new QuestionBuilder().withQuestion("How awesome is Njoy?").build();
        QuestionAddCommand addQuestionCommand = new QuestionAddCommand(question.getQuestion(),
            question.getAnswer(), "open");
        QuestionAddCommand addOtherQuestionCommand = new QuestionAddCommand(
            otherQuestion.getQuestion(),
            otherQuestion.getAnswer(), "open");

        // Same question
        assertTrue(addQuestionCommand.equals(addQuestionCommand));

        // Copy of question
        QuestionAddCommand addQuestionCommandCopy = new QuestionAddCommand(question.getQuestion(),
            question.getAnswer(), "open");
        assertTrue(addQuestionCommand.equals(addQuestionCommandCopy));

        // Different data types
        assertFalse(addQuestionCommand.equals(1));

        // Null
        assertFalse(addQuestionCommand.equals(null));

        // Different question
        assertFalse(addQuestionCommand.equals(addOtherQuestionCommand));
    }

    /**
     * A Model stub that always accept the question being added.
     */
    private class ModelStubAcceptingQuestionAdded extends ModelStub {

        final ArrayList<Question> questionsAdded = new ArrayList<>();

        @Override
        public void addQuestion(Question question) {
            requireNonNull(question);
            questionsAdded.add(question);
        }

        @Override
        public boolean hasQuestion(Question toCheck) {
            requireNonNull(toCheck);
            return questionsAdded.stream().anyMatch((question) -> question.equals(toCheck));
        }

        @Override
        public ReadOnlyQuestions getSavedQuestions() {
            return new SavedQuestions();
        }
    }
}
