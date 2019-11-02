package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_BORROWER;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalBorrowers.BOB;
import static seedu.address.testutil.TypicalBorrowers.FIONA;
import static seedu.address.testutil.TypicalBorrowers.getTypicalBorrowerRecords;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Catalog;
import seedu.address.model.LoanRecords;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.borrower.Borrower;
import seedu.address.model.borrower.BorrowerId;
import seedu.address.testutil.BorrowerBuilder;
import seedu.address.testutil.EditBorrowerDescriptorBuilder;

public class EditBorrowerCommandTest {
    private Model model = new ModelManager(new Catalog(), new
            LoanRecords(), getTypicalBorrowerRecords(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_success() throws CommandException {
        //serves BOB
        new ServeCommand(new BorrowerId(VALID_ID_BOB)).execute(model);
        Borrower editedBorrower = new BorrowerBuilder(BOB).build();
        EditBorrowerCommand.EditBorrowerDescriptor descriptor =
                new EditBorrowerDescriptorBuilder(editedBorrower).build();
        EditBorrowerCommand editBorrowerCommand = new EditBorrowerCommand(descriptor);

        String expectedMessage = String.format(EditBorrowerCommand.MESSAGE_EDIT_BORROWER_SUCCESS,
                editedBorrower);

        Model expectedModel = new ModelManager(
                new Catalog(new Catalog()), new LoanRecords(), model.getBorrowerRecords(), new UserPrefs());

        expectedModel.setBorrower(model.getServingBorrower(), editedBorrower);

        assertCommandSuccess(editBorrowerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() throws CommandException {
        //serves BOB
        new ServeCommand(new BorrowerId(VALID_ID_BOB)).execute(model);

        Borrower borrower = model.getServingBorrower();

        BorrowerBuilder borrowerInList = new BorrowerBuilder(borrower);
        Borrower editedBorrower = borrowerInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();

        EditBorrowerCommand.EditBorrowerDescriptor descriptor =
                new EditBorrowerDescriptorBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();
        EditBorrowerCommand editCommand = new EditBorrowerCommand(descriptor);

        String expectedMessage = String.format(EditBorrowerCommand.MESSAGE_EDIT_BORROWER_SUCCESS,
                editedBorrower);

        Model expectedModel = new ModelManager(
                new Catalog(), new LoanRecords(), model.getBorrowerRecords(), new UserPrefs());
        expectedModel.setBorrower(borrower, editedBorrower);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecified_success() throws CommandException {
        //serves BOB
        new ServeCommand(new BorrowerId(VALID_ID_BOB)).execute(model);

        EditBorrowerCommand editCommand = new EditBorrowerCommand(
                new EditBorrowerCommand.EditBorrowerDescriptor());
        Borrower editedBorrower = model.getServingBorrower();

        String expectedMessage = String.format(EditBorrowerCommand.MESSAGE_EDIT_BORROWER_SUCCESS,
                editedBorrower);

        Model expectedModel = new ModelManager(
                new Catalog(), new LoanRecords(), model.getBorrowerRecords(), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateBorrower_failure() throws CommandException {
        //serves BOB
        new ServeCommand(new BorrowerId(VALID_ID_BOB)).execute(model);

        EditBorrowerCommand.EditBorrowerDescriptor descriptor = new EditBorrowerDescriptorBuilder(FIONA).build();
        EditBorrowerCommand editCommand = new EditBorrowerCommand(descriptor);

        assertCommandFailure(editCommand, model, MESSAGE_DUPLICATE_BORROWER);
    }

    @Test
    public void equals() throws CommandException {
        final EditBorrowerCommand standardCommand = new EditBorrowerCommand(
                DESC_AMY);

        // same values -> returns true
        EditBorrowerCommand.EditBorrowerDescriptor copyDescriptor =
                new EditBorrowerCommand.EditBorrowerDescriptor(DESC_AMY);
        EditBorrowerCommand commandWithSameValues =
                new EditBorrowerCommand(copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different borrowerId -> returns false
        assertFalse(standardCommand.equals(new EditBorrowerCommand(DESC_BOB)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditBorrowerCommand(DESC_BOB)));
    }
}
