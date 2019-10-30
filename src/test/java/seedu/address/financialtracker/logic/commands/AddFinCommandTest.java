package seedu.address.financialtracker.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import seedu.address.financialtracker.model.Model;
import seedu.address.financialtracker.model.expense.Amount;
import seedu.address.financialtracker.model.expense.Country;
import seedu.address.financialtracker.model.expense.Date;
import seedu.address.financialtracker.model.expense.Description;
import seedu.address.financialtracker.model.expense.Expense;
import seedu.address.financialtracker.model.expense.Time;
import seedu.address.financialtracker.model.expense.Type;
import seedu.address.logic.commands.CommandResult;

class AddFinCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddFinCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {

        Expense ep1 = new Expense(new Date("271016"), new Time("1720"), new Amount("4"), new Description("breakfast"),
                new Type("Food"), new Country("Singapore"));
        Model model = new Model();

        CommandResult commandResult = new AddFinCommand(ep1).execute(model);
        assertEquals(AddFinCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        Expense ep1 = new Expense(new Date("271016"), new Time("1720"), new Amount("4"), new Description("breakfast"),
                new Type("Food"), new Country("Singapore"));
        Expense ep2 = new Expense(new Date("271019"), new Time("1520"), new Amount("5"), new Description("breakfast"),
                new Type("Food"), new Country("Singapore"));
        AddFinCommand addEp1Command = new AddFinCommand(ep1);
        AddFinCommand addEp2Command = new AddFinCommand(ep2);

        // same object -> returns true
        assertTrue(addEp1Command.equals(addEp1Command));

        // same values -> returns true
        AddFinCommand addEp1CommandCopy = new AddFinCommand(ep1);
        assertTrue(addEp1Command.equals(addEp1CommandCopy));

        // different types -> returns false
        assertFalse(addEp1Command.equals(1));

        // null -> returns false
        assertFalse(addEp1Command.equals(null));

        // different person -> returns false
        assertFalse(addEp1Command.equals(addEp2Command));
    }
}