package seedu.address.logic.commands.questioncommands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ModelStub;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AppData;
import seedu.address.model.ReadOnlyAppData;

import seedu.address.model.question.Question;
import seedu.address.testutil.QuestionBuilder;

class AddQuestionCommandTest {

    @Test
    public void constructor_nullQuestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddQuestionCommand(null));
    }

    @Test
    public void execute_questionAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingQuestionAdded modelStub = new ModelStubAcceptingQuestionAdded();
        Question validQuestion = new QuestionBuilder().build();

        CommandResult commandResult = new AddQuestionCommand(validQuestion).execute(modelStub);

        assertEquals(String.format(AddQuestionCommand.MESSAGE_SUCCESS, validQuestion),
                commandResult.getFeedbackToUser());
        assertEquals(Collections.singletonList(validQuestion), modelStub.questionsAdded);
    }

    @Test
    public void execute_duplicateQuestion_throwsCommandException() {
        Question validQuestion = new QuestionBuilder().build();
        AddQuestionCommand addQuestionCommand = new AddQuestionCommand(validQuestion);
        ModelStub modelStub = new AddQuestionCommandTest.ModelStubWithQuestion(validQuestion);

        assertThrows(CommandException.class,
                AddQuestionCommand.MESSAGE_DUPLICATE_QUESTION, () -> addQuestionCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Question algebra = new QuestionBuilder().withQuestionBody("Algebra").build();
        Question concept = new QuestionBuilder().withQuestionBody("Concept").build();
        AddQuestionCommand addAlgebraCommand = new AddQuestionCommand(algebra);
        AddQuestionCommand addConceptCommand = new AddQuestionCommand(concept);

        // same object -> returns true
        assertEquals(addAlgebraCommand, addAlgebraCommand);

        // same values -> returns true
        AddQuestionCommand addAlgebraCommandCopy = new AddQuestionCommand(algebra);
        assertEquals(addAlgebraCommand, addAlgebraCommandCopy);

        // different types -> returns false
        assertNotEquals(1, addAlgebraCommand);

        // null -> returns false
        assertNotNull(addAlgebraCommand);

        // different question -> returns false
        assertNotEquals(addAlgebraCommand, addConceptCommand);
    }

    /**
     * A Model stub that contains a single question.
     */
    private class ModelStubWithQuestion extends ModelStub {
        private final Question question;

        ModelStubWithQuestion(Question question) {
            requireNonNull(question);
            this.question = question;
        }

        @Override
        public boolean hasQuestion(Question question) {
            requireNonNull(question);
            return this.question.isSameQuestion(question);
        }
    }

    /**
     * A Model stub that always accept the question being added.
     */
    private class ModelStubAcceptingQuestionAdded extends ModelStub {
        final ArrayList<Question> questionsAdded = new ArrayList<>();

        @Override
        public boolean hasQuestion(Question question) {
            requireNonNull(question);
            return questionsAdded.stream().anyMatch(question::isSameQuestion);
        }

        @Override
        public void addQuestion(Question question) {
            requireNonNull(question);
            questionsAdded.add(question);
        }

        @Override
        public ReadOnlyAppData getAppData() {
            return new AppData();
        }
    }
}
