package seedu.revision.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.revision.logic.commands.CommandTestUtil.DESC_ALPHA;
import static seedu.revision.logic.commands.CommandTestUtil.DESC_BETA;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_CATEGORY_GREENFIELD;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BETA;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_MCQ_QUESTION_2;
import static seedu.revision.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.revision.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.revision.logic.commands.CommandTestUtil.showAnswerableAtIndex;
import static seedu.revision.testutil.TypicalIndexes.INDEX_FIRST_ANSWERABLE;
import static seedu.revision.testutil.TypicalIndexes.INDEX_SECOND_ANSWERABLE;
import static seedu.revision.testutil.TypicalMcqs.MCQ_INVALID_CORRECT_ANSWER_LIST;
import static seedu.revision.testutil.TypicalMcqs.getTypicalMcqs;

import org.junit.jupiter.api.Test;

import seedu.revision.commons.core.Messages;
import seedu.revision.commons.core.index.Index;
import seedu.revision.logic.commands.main.ClearCommand;
import seedu.revision.logic.commands.main.EditCommand;
import seedu.revision.logic.commands.main.EditCommand.EditAnswerableDescriptor;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.History;
import seedu.revision.model.Model;
import seedu.revision.model.ModelManager;
import seedu.revision.model.RevisionTool;
import seedu.revision.model.UserPrefs;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.Mcq;
import seedu.revision.testutil.McqBuilder;
import seedu.revision.testutil.builder.AnswerableBuilder;
import seedu.revision.testutil.builder.EditAnswerableDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalMcqs(), new UserPrefs(), new History());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws ParseException {
        Answerable editedAnswerable = new McqBuilder().build();
        EditAnswerableDescriptor descriptor = new EditAnswerableDescriptorBuilder(editedAnswerable).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ANSWERABLE, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ANSWERABLE_SUCCESS, editedAnswerable);

        Model expectedModel = new ModelManager(new RevisionTool(model.getRevisionTool()),
                new UserPrefs(), new History());
        expectedModel.setAnswerable(model.getFilteredAnswerableList().get(0), editedAnswerable);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws ParseException {
        Index indexLastAnswerable = Index.fromOneBased(model.getFilteredAnswerableList().size());
        Answerable lastAnswerable = model.getFilteredAnswerableList().get(indexLastAnswerable.getZeroBased());

        AnswerableBuilder answerableInList = new McqBuilder(lastAnswerable);
        Answerable editedAnswerable = answerableInList.withQuestion(VALID_MCQ_QUESTION_2)
                .withDifficulty(VALID_DIFFICULTY_BETA)
                .withCategories(VALID_CATEGORY_GREENFIELD).build();

        EditAnswerableDescriptor descriptor = new EditAnswerableDescriptorBuilder().withQuestion(VALID_MCQ_QUESTION_2)
                .withDifficulty(VALID_DIFFICULTY_BETA).withCategories(VALID_CATEGORY_GREENFIELD).build();
        EditCommand editCommand = new EditCommand(indexLastAnswerable, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ANSWERABLE_SUCCESS, editedAnswerable);

        Model expectedModel = new ModelManager(new RevisionTool(model.getRevisionTool()),
                new UserPrefs(), new History());
        expectedModel.setAnswerable(lastAnswerable, editedAnswerable);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() throws ParseException {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ANSWERABLE, new EditAnswerableDescriptor());
        Answerable editedAnswerable = model.getFilteredAnswerableList().get(INDEX_FIRST_ANSWERABLE.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ANSWERABLE_SUCCESS, editedAnswerable);

        Model expectedModel = new ModelManager(new RevisionTool(model.getRevisionTool()),
                new UserPrefs(), new History());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws ParseException {
        showAnswerableAtIndex(model, INDEX_FIRST_ANSWERABLE);

        Answerable answerableInFilteredList = model.getFilteredAnswerableList()
                .get(INDEX_FIRST_ANSWERABLE.getZeroBased());
        Answerable editedAnswerable = new McqBuilder(answerableInFilteredList)
                .withQuestion(VALID_MCQ_QUESTION_2).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ANSWERABLE,
                new EditAnswerableDescriptorBuilder().withQuestion(VALID_MCQ_QUESTION_2).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ANSWERABLE_SUCCESS, editedAnswerable);

        Model expectedModel = new ModelManager(new RevisionTool(model.getRevisionTool()),
                new UserPrefs(), new History());
        expectedModel.setAnswerable(model.getFilteredAnswerableList().get(0), editedAnswerable);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void parse_multipleCorrectAnswer_failure() {
        EditAnswerableDescriptor descriptor = new EditAnswerableDescriptorBuilder()
                .withCorrectAnswerList(MCQ_INVALID_CORRECT_ANSWER_LIST).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_ANSWERABLE, descriptor);
        assertCommandFailure(editCommand, model, Mcq.MESSAGE_CONSTRAINTS);
    }


    @Test
    public void execute_duplicateAnswerableUnfilteredList_failure() {
        Answerable firstAnswerable = model.getFilteredAnswerableList().get(INDEX_FIRST_ANSWERABLE.getZeroBased());
        EditAnswerableDescriptor descriptor = new EditAnswerableDescriptorBuilder(firstAnswerable).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_ANSWERABLE, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_ANSWERABLE);
    }

    @Test
    public void execute_duplicateAnswerableFilteredList_failure() {
        showAnswerableAtIndex(model, INDEX_FIRST_ANSWERABLE);

        // edit answerable in filtered list into a duplicate in revision tool
        Answerable answerableInList = model.getRevisionTool().getAnswerableList()
                .get(INDEX_SECOND_ANSWERABLE.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ANSWERABLE,
                new EditAnswerableDescriptorBuilder(answerableInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_ANSWERABLE);
    }

    @Test
    public void execute_invalidAnswerableIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAnswerableList().size() + 1);
        EditAnswerableDescriptor descriptor = new EditAnswerableDescriptorBuilder()
                .withQuestion(VALID_MCQ_QUESTION_2).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ANSWERABLE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of revision tool
     */
    @Test
    public void execute_invalidAnswerableIndexFilteredList_failure() {
        showAnswerableAtIndex(model, INDEX_FIRST_ANSWERABLE);
        Index outOfBoundIndex = INDEX_SECOND_ANSWERABLE;
        // ensures that outOfBoundIndex is still in bounds of revision tool list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRevisionTool().getAnswerableList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditAnswerableDescriptorBuilder().withQuestion(VALID_MCQ_QUESTION_2).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ANSWERABLE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_ANSWERABLE, DESC_ALPHA);

        // same values -> returns true
        EditAnswerableDescriptor copyDescriptor = new EditAnswerableDescriptor(DESC_ALPHA);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_ANSWERABLE, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_ANSWERABLE, DESC_ALPHA)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_ANSWERABLE, DESC_BETA)));
    }

}
