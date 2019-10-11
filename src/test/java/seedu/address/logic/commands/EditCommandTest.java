package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudyPlanAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDYPLAN;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_STUDYPLAN;
import static seedu.address.testutil.TypicalStudyPlans.getTypicalModulePlanner;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.ModulePlanner;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.testutil.StudyPlanBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalModulePlanner(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        StudyPlan editedStudyPlan = new StudyPlanBuilder().build();
        EditStudyPlanDescriptor descriptor = new EditStudyPlanDescriptorBuilder(editedStudyPlan).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_STUDYPLAN, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_STUDYPLAN_SUCCESS, editedStudyPlan);

        Model expectedModel = new ModelManager(new ModulePlanner(model.getModulePlanner()), new UserPrefs());
        expectedModel.setStudyPlan(model.getFilteredStudyPlanList().get(0), editedStudyPlan);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastStudyPlan = Index.fromOneBased(model.getFilteredStudyPlanList().size());
        StudyPlan lastStudyPlan = model.getFilteredStudyPlanList().get(indexLastStudyPlan.getZeroBased());

        StudyPlanBuilder studyPlanInList = new StudyPlanBuilder(lastStudyPlan);
        StudyPlan editedStudyPlan = studyPlanInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditStudyPlanDescriptor descriptor = new EditStudyPlanDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastStudyPlan, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_STUDYPLAN_SUCCESS, editedStudyPlan);

        Model expectedModel = new ModelManager(new ModulePlanner(model.getModulePlanner()), new UserPrefs());
        expectedModel.setStudyPlan(lastStudyPlan, editedStudyPlan);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_STUDYPLAN, new EditStudyPlanDescriptor());
        StudyPlan editedStudyPlan = model.getFilteredStudyPlanList().get(INDEX_FIRST_STUDYPLAN.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_STUDYPLAN_SUCCESS, editedStudyPlan);

        Model expectedModel = new ModelManager(new ModulePlanner(model.getModulePlanner()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showStudyPlanAtIndex(model, INDEX_FIRST_STUDYPLAN);

        StudyPlan studyPlanInFilteredList = model.getFilteredStudyPlanList().get(INDEX_FIRST_STUDYPLAN.getZeroBased());
        StudyPlan editedStudyPlan = new StudyPlanBuilder(studyPlanInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_STUDYPLAN,
                new EditStudyPlanDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_STUDYPLAN_SUCCESS, editedStudyPlan);

        Model expectedModel = new ModelManager(new ModulePlanner(model.getModulePlanner()), new UserPrefs());
        expectedModel.setStudyPlan(model.getFilteredStudyPlanList().get(0), editedStudyPlan);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateStudyPlanUnfilteredList_failure() {
        StudyPlan firstStudyPlan = model.getFilteredStudyPlanList().get(INDEX_FIRST_STUDYPLAN.getZeroBased());
        EditStudyPlanDescriptor descriptor = new EditStudyPlanDescriptorBuilder(firstStudyPlan).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_STUDYPLAN, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_STUDYPLAN);
    }

    @Test
    public void execute_duplicateStudyPlanFilteredList_failure() {
        showStudyPlanAtIndex(model, INDEX_FIRST_STUDYPLAN);

        // edit studyPlan in filtered list into a duplicate in module planner
        StudyPlan studyPlanInList = model.getModulePlanner().getStudyPlanList().get(INDEX_SECOND_STUDYPLAN.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_STUDYPLAN,
                new EditStudyPlanDescriptorBuilder(studyPlanInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_STUDYPLAN);
    }

    @Test
    public void execute_invalidStudyPlanIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudyPlanList().size() + 1);
        EditStudyPlanDescriptor descriptor = new EditStudyPlanDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_STUDYPLAN_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of module planner
     */
    @Test
    public void execute_invalidStudyPlanIndexFilteredList_failure() {
        showStudyPlanAtIndex(model, INDEX_FIRST_STUDYPLAN);
        Index outOfBoundIndex = INDEX_SECOND_STUDYPLAN;
        // ensures that outOfBoundIndex is still in bounds of module planner list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getModulePlanner().getStudyPlanList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditStudyPlanDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_STUDYPLAN_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_STUDYPLAN, DESC_AMY);

        // same values -> returns true
        EditStudyPlanDescriptor copyDescriptor = new EditStudyPlanDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_STUDYPLAN, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_STUDYPLAN, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_STUDYPLAN, DESC_BOB)));
    }

}
