package com.dukeacademy.logic.commands;

import static com.dukeacademy.logic.commands.CommandTestUtil.DESC_AMY;
import static com.dukeacademy.logic.commands.CommandTestUtil.DESC_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static com.dukeacademy.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static com.dukeacademy.logic.commands.CommandTestUtil.assertCommandFailure;
import static com.dukeacademy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static com.dukeacademy.logic.commands.CommandTestUtil.showQuestionAtIndex;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.dukeacademy.commons.core.Messages;
import com.dukeacademy.commons.core.index.Index;
import com.dukeacademy.model.Model;
import com.dukeacademy.model.ModelManager;
import com.dukeacademy.model.QuestionBank;
import com.dukeacademy.model.UserPrefs;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.testutil.EditQuestionDescriptorBuilder;
import com.dukeacademy.testutil.QuestionBuilder;
import com.dukeacademy.testutil.TypicalIndexes;
import com.dukeacademy.testutil.TypicalQuestions;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(TypicalQuestions.getTypicalQuestionBank(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Question editedQuestion = new QuestionBuilder().build();
        EditCommand.EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder(
            editedQuestion).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_QUESTION, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_QUESTION_SUCCESS,
            editedQuestion);

        Model expectedModel = new ModelManager(new QuestionBank(model.getQuestionBank()), new UserPrefs());
        expectedModel.setQuestion(model.getFilteredQuestionList().get(0),
            editedQuestion);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastQuestion = Index.fromOneBased(model.getFilteredQuestionList().size());
        Question
            lastQuestion = model.getFilteredQuestionList().get(indexLastQuestion.getZeroBased());

        QuestionBuilder questionInList = new QuestionBuilder(lastQuestion);
        Question editedQuestion = questionInList.withTitle(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                                              .withTags(VALID_TAG_HUSBAND).build();

        EditCommand.EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder()
            .withTitle(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastQuestion, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_QUESTION_SUCCESS,
            editedQuestion);

        Model expectedModel = new ModelManager(new QuestionBank(model.getQuestionBank()), new UserPrefs());
        expectedModel.setQuestion(lastQuestion, editedQuestion);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_QUESTION,
                new EditCommand.EditQuestionDescriptor());
        Question editedQuestion = model
            .getFilteredQuestionList()
            .get(TypicalIndexes.INDEX_FIRST_QUESTION.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_QUESTION_SUCCESS,
            editedQuestion);

        Model expectedModel = new ModelManager(new QuestionBank(model.getQuestionBank()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showQuestionAtIndex(model, TypicalIndexes.INDEX_FIRST_QUESTION);

        Question questionInFilteredList = model.getFilteredQuestionList()
                                               .get(TypicalIndexes.INDEX_FIRST_QUESTION.getZeroBased());
        Question editedQuestion = new QuestionBuilder(questionInFilteredList).withTitle(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_QUESTION,
                new EditQuestionDescriptorBuilder().withTitle(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_QUESTION_SUCCESS,
            editedQuestion);

        Model expectedModel = new ModelManager(new QuestionBank(model.getQuestionBank()), new UserPrefs());
        expectedModel.setQuestion(model.getFilteredQuestionList().get(0),
            editedQuestion);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateQuestionUnfilteredList_failure() {
        Question firstQuestion = model
            .getFilteredQuestionList()
            .get(TypicalIndexes.INDEX_FIRST_QUESTION.getZeroBased());
        EditCommand.EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder(
            firstQuestion).build();
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_SECOND_QUESTION, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_QUESTION);
    }

    @Test
    public void execute_duplicateQuestionFilteredList_failure() {
        showQuestionAtIndex(model, TypicalIndexes.INDEX_FIRST_QUESTION);

        // edit question in filtered list into a duplicate in address book
        Question questionInList = model.getQuestionBank().getQuestionList()
                                       .get(TypicalIndexes.INDEX_SECOND_QUESTION.getZeroBased());
        EditCommand editCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_QUESTION,
                new EditQuestionDescriptorBuilder(questionInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_QUESTION);
    }

    @Test
    public void execute_invalidQuestionIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredQuestionList().size() + 1);
        EditCommand.EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder().withTitle(VALID_NAME_BOB)
                                                                                         .build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidQuestionIndexFilteredList_failure() {
        showQuestionAtIndex(model, TypicalIndexes.INDEX_FIRST_QUESTION);
        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_QUESTION;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getQuestionBank().getQuestionList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditQuestionDescriptorBuilder().withTitle(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(TypicalIndexes.INDEX_FIRST_QUESTION, DESC_AMY);

        // same values -> returns true
        EditCommand.EditQuestionDescriptor copyDescriptor = new EditCommand.EditQuestionDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(TypicalIndexes.INDEX_FIRST_QUESTION, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(TypicalIndexes.INDEX_SECOND_QUESTION, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(TypicalIndexes.INDEX_FIRST_QUESTION, DESC_BOB)));
    }

}
