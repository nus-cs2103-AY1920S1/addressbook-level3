package seedu.address.financialtracker.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.financialtracker.model.Model;
import seedu.address.financialtracker.model.expense.Amount;

class EditFinCommandTest {

    private Model model = new Model();
    private Index indexFirst = Index.fromZeroBased(1);
    private Index indexSecond = Index.fromZeroBased(2);

    @Test
    void equals() {
        final EditFinCommand.EditExpenseDescriptor editExpenseDescriptor = new EditFinCommand.EditExpenseDescriptor();
        final EditFinCommand standardCommand = new EditFinCommand(indexFirst, editExpenseDescriptor);

        // same values -> returns true
        EditFinCommand.EditExpenseDescriptor copyDescriptor =
                new EditFinCommand.EditExpenseDescriptor(editExpenseDescriptor);
        EditFinCommand commandWithSameValues = new EditFinCommand(indexFirst, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new HelpCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditFinCommand(indexSecond, editExpenseDescriptor)));


        final EditFinCommand.EditExpenseDescriptor editExpenseDescriptor2 = new EditFinCommand.EditExpenseDescriptor();
        editExpenseDescriptor2.setAmount(new Amount("30"));
        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditFinCommand(indexFirst, editExpenseDescriptor2)));
    }

}
