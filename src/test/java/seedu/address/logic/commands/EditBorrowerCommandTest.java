package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_BORROWER;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.TypicalBorrowers.getTypicalBorrowerRecords;
import static seedu.address.testutil.TypicalBorrowers.ID_FIRST_BORROWER;
import static seedu.address.testutil.TypicalBorrowers.ID_SECOND_BORROWER;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.*;
import seedu.address.model.borrower.Borrower;
import seedu.address.model.borrower.BorrowerId;
import seedu.address.testutil.BorrowerBuilder;
import seedu.address.testutil.EditBorrowerDescriptorBuilder;

public class EditBorrowerCommandTest {
    private Model model = new ModelManager(new Catalog(), new LoanRecords(), getTypicalBorrowerRecords(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_success() throws CommandException {
        Borrower editedBorrower = new BorrowerBuilder().build();
        EditBorrowerCommand.EditBorrowerDescriptor descriptor = new EditBorrowerDescriptorBuilder(editedBorrower).build();
        EditBorrowerCommand editBorrowerCommand = new EditBorrowerCommand(ID_FIRST_BORROWER, descriptor);

        String expectedMessage = String.format(EditBorrowerCommand.MESSAGE_EDIT_BORROWER_SUCCESS, editedBorrower);

        Model expectedModel = new ModelManager(
                new Catalog(new Catalog()), new LoanRecords(), model.getBorrowerRecords(), new UserPrefs());

        expectedModel.setBorrower(model.getBorrowerFromId(ID_FIRST_BORROWER), editedBorrower);

        assertCommandSuccess(editBorrowerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() throws CommandException {
        BorrowerId id = ID_FIRST_BORROWER;
        Borrower borrower = model.getBorrowerFromId(id);

        BorrowerBuilder borrowerInList = new BorrowerBuilder(borrower);
        Borrower editedBorrower = borrowerInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();

        EditBorrowerCommand.EditBorrowerDescriptor descriptor = new EditBorrowerDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).build();
        EditBorrowerCommand editCommand = new EditBorrowerCommand(id, descriptor);

        String expectedMessage = String.format(EditBorrowerCommand.MESSAGE_EDIT_BORROWER_SUCCESS, editedBorrower);

        Model expectedModel = new ModelManager(
                new Catalog(), new LoanRecords(), model.getBorrowerRecords(), new UserPrefs());
        expectedModel.setBorrower(borrower, editedBorrower);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecified_success() throws CommandException {
        EditBorrowerCommand editCommand = new EditBorrowerCommand(ID_FIRST_BORROWER, new EditBorrowerCommand.EditBorrowerDescriptor());
        Borrower editedBorrower = model.getBorrowerFromId(ID_FIRST_BORROWER);

        String expectedMessage = String.format(EditBorrowerCommand.MESSAGE_EDIT_BORROWER_SUCCESS, editedBorrower);

        Model expectedModel = new ModelManager(
                new Catalog(), new LoanRecords(), model.getBorrowerRecords(), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateBorrower_failure() throws CommandException {
        Borrower borrower = model.getBorrowerFromId(ID_FIRST_BORROWER);
        EditBorrowerCommand.EditBorrowerDescriptor descriptor = new EditBorrowerDescriptorBuilder(borrower).build();
        EditBorrowerCommand editCommand = new EditBorrowerCommand(ID_SECOND_BORROWER, descriptor);

        assertCommandFailure(editCommand, model, MESSAGE_DUPLICATE_BORROWER);
    }

    @Test
    public void equals() throws CommandException {
        final EditBorrowerCommand standardCommand = new EditBorrowerCommand(new BorrowerId(VALID_ID_AMY)
                , DESC_AMY);

        // same values -> returns true
        EditBorrowerCommand.EditBorrowerDescriptor copyDescriptor = new EditBorrowerCommand.EditBorrowerDescriptor(DESC_AMY);
        EditBorrowerCommand commandWithSameValues = new EditBorrowerCommand(new BorrowerId(VALID_ID_AMY), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different borrowerId -> returns false
        assertFalse(standardCommand.equals(new EditBorrowerCommand(new BorrowerId(VALID_ID_BOB), DESC_BOB)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditBorrowerCommand(new BorrowerId(VALID_ID_BOB), DESC_BOB)));
    }
}
