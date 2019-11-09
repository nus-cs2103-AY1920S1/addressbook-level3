package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS1231;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS2100;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EARNINGS_CS1231_T05;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_EARNINGS_CS1231_T05;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showEarningsAtIndex;
import static seedu.address.testutil.TypicalEarnings.getTypicalTutorAid;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateEarningsCommand.EditEarningsDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TutorAid;
import seedu.address.model.UserPrefs;
import seedu.address.model.earnings.Earnings;
import seedu.address.testutil.EarningsBuilder;
import seedu.address.testutil.UpdateEarningsDescriptorBuilder;

public class UpdateEarningsCommandTest {

    private Model model = new ModelManager(getTypicalTutorAid(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Earnings editedEarnings = new EarningsBuilder().build();
        EditEarningsDescriptor descriptor = new UpdateEarningsDescriptorBuilder(editedEarnings).build();
        UpdateEarningsCommand updateEarningsCommand = new UpdateEarningsCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(UpdateEarningsCommand.MESSAGE_UPDATE_SUCCESS, editedEarnings);

        Model expectedModel = new ModelManager(new TutorAid(model.getTutorAid()), new UserPrefs());
        expectedModel.setEarnings(model.getFilteredEarningsList().get(0), editedEarnings);
        expectedModel.commitTutorAid();
        assertCommandSuccess(updateEarningsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastEarnings = Index.fromOneBased(model.getFilteredEarningsList().size());
        Earnings lastEarnings = model.getFilteredEarningsList().get(indexLastEarnings.getZeroBased());

        EarningsBuilder earningsInList = new EarningsBuilder(lastEarnings);
        Earnings editedEarnings = earningsInList.withDate(VALID_DATE_EARNINGS_CS1231_T05)
                .withType(VALID_TYPE_EARNINGS_CS1231_T05).build();

        EditEarningsDescriptor descriptor = new UpdateEarningsDescriptorBuilder()
                .withDate(VALID_DATE_EARNINGS_CS1231_T05)
                .withType(VALID_TYPE_EARNINGS_CS1231_T05).build();
        UpdateEarningsCommand updateEarningsCommand = new UpdateEarningsCommand(indexLastEarnings, descriptor);

        String expectedMessage = String.format(UpdateEarningsCommand.MESSAGE_UPDATE_SUCCESS, editedEarnings);

        Model expectedModel = new ModelManager(new TutorAid(model.getTutorAid()), new UserPrefs());
        expectedModel.setEarnings(lastEarnings, editedEarnings);

        expectedModel.commitTutorAid();
        assertCommandSuccess(updateEarningsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        UpdateEarningsCommand updateEarningsCommand =
                new UpdateEarningsCommand(INDEX_FIRST, new EditEarningsDescriptor());
        Earnings editedEarnings = model.getFilteredEarningsList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(UpdateEarningsCommand.MESSAGE_UPDATE_SUCCESS, editedEarnings);

        Model expectedModel = new ModelManager(new TutorAid(model.getTutorAid()), new UserPrefs());
        expectedModel.commitTutorAid();
        assertCommandSuccess(updateEarningsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showEarningsAtIndex(model, INDEX_FIRST);

        Earnings earningsInFilteredList = model.getFilteredEarningsList().get(INDEX_FIRST.getZeroBased());
        Earnings editedEarnings = new EarningsBuilder(earningsInFilteredList)
                .withDate(VALID_DATE_EARNINGS_CS1231_T05).build();
        UpdateEarningsCommand updateEarningsCommand = new UpdateEarningsCommand(INDEX_FIRST,
                new UpdateEarningsDescriptorBuilder().withDate(VALID_DATE_EARNINGS_CS1231_T05).build());

        String expectedMessage = String.format(UpdateEarningsCommand.MESSAGE_UPDATE_SUCCESS, editedEarnings);

        Model expectedModel = new ModelManager(new TutorAid(model.getTutorAid()), new UserPrefs());
        expectedModel.setEarnings(model.getFilteredEarningsList().get(0), editedEarnings);
        expectedModel.commitTutorAid();
        assertCommandSuccess(updateEarningsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateEarningsUnfilteredList_failure() {
        Earnings firstEarnings = model.getFilteredEarningsList().get(INDEX_FIRST.getZeroBased());
        EditEarningsDescriptor descriptor = new UpdateEarningsDescriptorBuilder(firstEarnings).build();
        UpdateEarningsCommand updateEarningsCommand = new UpdateEarningsCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(updateEarningsCommand, model, UpdateEarningsCommand.MESSAGE_DUPLICATE_EARNINGS);
    }

    @Test
    public void execute_duplicateEarningsFilteredList_failure() {
        showEarningsAtIndex(model, INDEX_FIRST);

        // edit earnings in filtered list into a duplicate in address book
        Earnings earningsInList = model.getTutorAid().getEarningsList().get(INDEX_SECOND.getZeroBased());
        UpdateEarningsCommand updateEarningsCommand = new UpdateEarningsCommand(INDEX_FIRST,
                new UpdateEarningsDescriptorBuilder(earningsInList).build());

        assertCommandFailure(updateEarningsCommand, model, UpdateEarningsCommand.MESSAGE_DUPLICATE_EARNINGS);
    }

    @Test
    public void execute_invalidEarningsIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEarningsList().size() + 1);
        EditEarningsDescriptor descriptor = new UpdateEarningsDescriptorBuilder()
                .withDate(VALID_DATE_EARNINGS_CS1231_T05).build();
        UpdateEarningsCommand updateEarningsCommand = new UpdateEarningsCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(updateEarningsCommand, model, Messages.MESSAGE_INVALID_EARNINGS_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidEarningsIndexFilteredList_failure() {
        showEarningsAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTutorAid().getEarningsList().size());

        UpdateEarningsCommand updateEarningsCommand = new UpdateEarningsCommand(outOfBoundIndex,
                new UpdateEarningsDescriptorBuilder().withDate(VALID_DATE_EARNINGS_CS1231_T05).build());

        assertCommandFailure(updateEarningsCommand, model, Messages.MESSAGE_INVALID_EARNINGS_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final UpdateEarningsCommand standardCommand = new UpdateEarningsCommand(INDEX_FIRST, DESC_CS2100);

        // same values -> returns true
        EditEarningsDescriptor copyDescriptor = new EditEarningsDescriptor(DESC_CS2100);
        UpdateEarningsCommand commandWithSameValues = new UpdateEarningsCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new UpdateEarningsCommand(INDEX_SECOND, DESC_CS2100)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new UpdateEarningsCommand(INDEX_FIRST, DESC_CS1231)));
    }

}
