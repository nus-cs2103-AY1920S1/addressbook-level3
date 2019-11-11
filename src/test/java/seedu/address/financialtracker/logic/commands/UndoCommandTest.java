package seedu.address.financialtracker.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import seedu.address.financialtracker.model.Model;
import seedu.address.financialtracker.model.expense.Amount;
import seedu.address.financialtracker.model.expense.Country;
import seedu.address.financialtracker.model.expense.Date;
import seedu.address.financialtracker.model.expense.Description;
import seedu.address.financialtracker.model.expense.Expense;
import seedu.address.financialtracker.model.expense.Time;
import seedu.address.financialtracker.model.expense.Type;
import seedu.address.logic.commands.exceptions.CommandException;

class UndoCommandTest {

    @Test
    void execute_withoutModel() {
        assertThrows(NullPointerException.class,
                () -> new UndoCommand().execute(null));
    }

    @Test
    void execute_withModel() throws CommandException {
        Model model = new Model();
        Expense ep = new Expense(new Date("27102016"), new Time("1720"), new Amount("4"), new Description("breakfast"),
                new Type("Food"), new Country("Singapore"));

        // nothing to undo
        assertThrows(CommandException.class, () -> new UndoCommand().execute(model));

        // add an action
        new AddFinCommand(ep).execute(model);
        //undo once
        new UndoCommand().execute(model);
        //second undo throws exception
        assertThrows(CommandException.class, () -> new UndoCommand().execute(model));
    }

}
