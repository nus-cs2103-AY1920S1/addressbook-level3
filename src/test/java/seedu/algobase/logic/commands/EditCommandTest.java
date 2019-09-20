package seedu.algobase.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.algobase.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.algobase.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.algobase.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.algobase.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.algobase.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.algobase.logic.commands.CommandTestUtil.showProblemAtIndex;
import static seedu.algobase.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.algobase.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.algobase.testutil.TypicalProblems.getTypicalAlgoBase;

import org.junit.jupiter.api.Test;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.EditCommand.EditProblemDescriptor;
import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.Model;
import seedu.algobase.model.ModelManager;
import seedu.algobase.model.UserPrefs;
import seedu.algobase.model.Problem.Problem;
import seedu.algobase.testutil.EditProblemDescriptorBuilder;
import seedu.algobase.testutil.ProblemBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAlgoBase(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Problem editedProblem = new ProblemBuilder().build();
        EditProblemDescriptor descriptor = new EditProblemDescriptorBuilder(editedProblem).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedProblem);

        Model expectedModel = new ModelManager(new AlgoBase(model.getAlgoBase()), new UserPrefs());
        expectedModel.setProblem(model.getFilteredProblemList().get(0), editedProblem);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastProblem = Index.fromOneBased(model.getFilteredProblemList().size());
        Problem lastProblem = model.getFilteredProblemList().get(indexLastProblem.getZeroBased());

        ProblemBuilder problemInList = new ProblemBuilder(lastProblem);
        Problem editedProblem = problemInList.withName(VALID_NAME_BOB).withAuthor(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditProblemDescriptor descriptor = new EditProblemDescriptorBuilder().withName(VALID_NAME_BOB)
                .withAuthor(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastProblem, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedProblem);

        Model expectedModel = new ModelManager(new AlgoBase(model.getAlgoBase()), new UserPrefs());
        expectedModel.setProblem(lastProblem, editedProblem);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditProblemDescriptor());
        Problem editedProblem = model.getFilteredProblemList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedProblem);

        Model expectedModel = new ModelManager(new AlgoBase(model.getAlgoBase()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showProblemAtIndex(model, INDEX_FIRST_PERSON);

        Problem problemInFilteredList = model.getFilteredProblemList().get(INDEX_FIRST_PERSON.getZeroBased());
        Problem editedProblem = new ProblemBuilder(problemInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditProblemDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedProblem);

        Model expectedModel = new ModelManager(new AlgoBase(model.getAlgoBase()), new UserPrefs());
        expectedModel.setProblem(model.getFilteredProblemList().get(0), editedProblem);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateProblemUnfilteredList_failure() {
        Problem firstProblem = model.getFilteredProblemList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditProblemDescriptor descriptor = new EditProblemDescriptorBuilder(firstProblem).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicateProblemFilteredList_failure() {
        showProblemAtIndex(model, INDEX_FIRST_PERSON);

        // edit Problem in filtered list into a duplicate in algobase
        Problem problemInList = model.getAlgoBase().getProblemList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditProblemDescriptorBuilder(problemInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidProblemIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredProblemList().size() + 1);
        EditProblemDescriptor descriptor = new EditProblemDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of algobase
     */
    @Test
    public void execute_invalidProblemIndexFilteredList_failure() {
        showProblemAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of algobase list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAlgoBase().getProblemList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditProblemDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditProblemDescriptor copyDescriptor = new EditProblemDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

}
