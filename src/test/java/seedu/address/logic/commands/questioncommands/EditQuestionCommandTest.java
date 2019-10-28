package seedu.address.logic.commands.questioncommands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ALGEBRA;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CONCEPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_BODY_CONCEPT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showQuestionAtIndex;
import static seedu.address.testutil.TypicalAppData.getTypicalAppData;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.questioncommands.EditQuestionCommand.EditQuestionDescriptor;
import seedu.address.model.AppData;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.question.Question;
import seedu.address.testutil.EditQuestionDescriptorBuilder;
import seedu.address.testutil.QuestionBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditQuestionCommand.
 */
class EditQuestionCommandTest {
    private Model model = new ModelManager(getTypicalAppData(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Question editedQuestion = new QuestionBuilder().build();
        EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder(editedQuestion).build();
        EditQuestionCommand editQuestionCommand = new EditQuestionCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditQuestionCommand.MESSAGE_EDIT_QUESTION_SUCCESS, editedQuestion);

        Model expectedModel = new ModelManager(new AppData(model.getAppData()), new UserPrefs());
        expectedModel.setQuestion(model.getFilteredQuestionList().get(0), editedQuestion);

        assertCommandSuccess(editQuestionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastQuestion = Index.fromOneBased(model.getFilteredQuestionList().size());
        Question lastQuestion = model.getFilteredQuestionList().get(indexLastQuestion.getZeroBased());

        QuestionBuilder questionInList = new QuestionBuilder(lastQuestion);
        Question editedQuestion = questionInList.withQuestionBody(VALID_QUESTION_BODY_CONCEPT).build();

        EditQuestionCommand.EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder()
                .withQuestionBody(VALID_QUESTION_BODY_CONCEPT).build();
        EditQuestionCommand editQuestionCommand = new EditQuestionCommand(indexLastQuestion, descriptor);

        String expectedMessage = String.format(EditQuestionCommand.MESSAGE_EDIT_QUESTION_SUCCESS, editedQuestion);

        Model expectedModel = new ModelManager(new AppData(model.getAppData()), new UserPrefs());
        expectedModel.setQuestion(lastQuestion, editedQuestion);

        assertCommandSuccess(editQuestionCommand, model, expectedMessage, expectedModel);
    }

    // NO FIELD?
    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditQuestionCommand editQuestionCommand = new EditQuestionCommand(INDEX_FIRST,
                new EditQuestionCommand.EditQuestionDescriptor());
        Question editedQuestion = model.getFilteredQuestionList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditQuestionCommand.MESSAGE_EDIT_QUESTION_SUCCESS, editedQuestion);

        Model expectedModel = new ModelManager(new AppData(model.getAppData()), new UserPrefs());

        assertCommandSuccess(editQuestionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showQuestionAtIndex(model, INDEX_FIRST);

        Question questionInFilteredList = model.getFilteredQuestionList().get(INDEX_FIRST.getZeroBased());
        Question editedQuestion = new QuestionBuilder(questionInFilteredList)
                .withQuestionBody(VALID_QUESTION_BODY_CONCEPT).build();
        EditQuestionCommand editQuestionCommand = new EditQuestionCommand(INDEX_FIRST,
                new EditQuestionDescriptorBuilder()
                        .withQuestionBody(VALID_QUESTION_BODY_CONCEPT).build());

        String expectedMessage = String.format(EditQuestionCommand.MESSAGE_EDIT_QUESTION_SUCCESS, editedQuestion);

        Model expectedModel = new ModelManager(new AppData(model.getAppData()), new UserPrefs());
        expectedModel.setQuestion(model.getFilteredQuestionList().get(0), editedQuestion);

        assertCommandSuccess(editQuestionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateQuestionUnfilteredList_failure() {
        Question firstQuestion = model.getFilteredQuestionList().get(INDEX_FIRST.getZeroBased());
        EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder(firstQuestion).build();
        EditQuestionCommand editQuestionCommand = new EditQuestionCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editQuestionCommand, model, EditQuestionCommand.MESSAGE_DUPLICATE_QUESTION);
    }

    @Test
    public void execute_duplicateQuestionFilteredList_failure() {
        showQuestionAtIndex(model, INDEX_FIRST);

        // edit question in filtered list into a duplicate in questions
        Question questionInList = model.getAppData().getQuestionList().get(INDEX_SECOND.getZeroBased());
        EditQuestionCommand editQuestionCommand = new EditQuestionCommand(INDEX_FIRST,
                new EditQuestionDescriptorBuilder(questionInList).build());

        assertCommandFailure(editQuestionCommand, model, EditQuestionCommand.MESSAGE_DUPLICATE_QUESTION);
    }

    @Test
    public void execute_invalidQuestionIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredQuestionList().size() + 1);
        EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder()
                .withQuestionBody(VALID_QUESTION_BODY_CONCEPT).build();
        EditQuestionCommand editQuestionCommand = new EditQuestionCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editQuestionCommand, model, Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than number of questions
     */
    @Test
    public void execute_invalidQuestionIndexFilteredList_failure() {
        showQuestionAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of lecture question list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAppData().getQuestionList().size());

        EditQuestionCommand editQuestionCommand = new EditQuestionCommand(outOfBoundIndex,
                new EditQuestionDescriptorBuilder().withQuestionBody(VALID_QUESTION_BODY_CONCEPT).build());

        assertCommandFailure(editQuestionCommand, model, Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditQuestionCommand standardCommand = new EditQuestionCommand(INDEX_FIRST, DESC_ALGEBRA);

        // same values -> returns true
        EditQuestionDescriptor copyDescriptor = new EditQuestionCommand.EditQuestionDescriptor(DESC_ALGEBRA);
        EditQuestionCommand commandWithSameValues = new EditQuestionCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ListQuestionCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditQuestionCommand(INDEX_SECOND, DESC_ALGEBRA)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditQuestionCommand(INDEX_FIRST, DESC_CONCEPT)));
    }
}
