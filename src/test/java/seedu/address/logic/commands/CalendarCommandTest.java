package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPerformance.getTypicalPerformance;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Attendance;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.date.AthletickDate;
import seedu.address.testutil.AthletickDateBuilder;

public class CalendarCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalPerformance(), new Attendance(),
            new UserPrefs());

    @Test
    public void constructor_nullDate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CalendarCommand(null));
    }

    @Test
    public void execute_validDate_success() throws Exception {
        AthletickDate validDate = new AthletickDateBuilder().build();

        CommandResult commandResult = new CalendarCommand(validDate).execute(model);

        assertEquals(CalendarCommand.MESSAGE_SUCCESS_1 + "1st January 2019",
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidDateType_throwsCommandException() throws Exception {
        AthletickDate invalidDateType = new AthletickDateBuilder().withType(0).build();

        CalendarCommand calendarCommand = new CalendarCommand(invalidDateType);

        assertThrows(CommandException.class, CalendarCommand.MESSAGE_INVALID_DATE, (
            ) -> calendarCommand.execute(model));
    }

    @Test
    public void equals() throws ParseException {
        AthletickDate ad1 = new AthletickDateBuilder().build();
        AthletickDate ad2 = new AthletickDateBuilder().withDay(2).build();
        CalendarCommand calendarCommand1 = new CalendarCommand(ad1);
        CalendarCommand calendarCommand2 = new CalendarCommand(ad2);

        // same object -> returns true
        assertTrue(calendarCommand1.equals(calendarCommand1));

        // same values -> returns true
        CalendarCommand calendarCommand1Copy = new CalendarCommand(ad1);
        assertTrue(calendarCommand1.equals(calendarCommand1Copy));

        // different types -> returns false
        assertFalse(calendarCommand1.equals(1));

        // null -> returns false
        assertFalse(calendarCommand1.equals(null));

        //different command -> returns false
        assertFalse(calendarCommand1.equals(calendarCommand2));
    }
}
