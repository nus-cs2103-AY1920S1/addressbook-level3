//package seedu.address.logic.calendar.commands;
//
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.logic.commands.ClearCommand;
//
//
//public class ClearCommandTest {
//
//    @Test
//    public void execute_emptyAddressBook_success() {
//        CalendarModel calendarModel = new CalendarModelManager();
//        CalendarModel expectedModel = new CalendarModelManager();
//
//        assertCommandSuccess(new ClearCommand(), calendarModel, ClearCommand.MESSAGE_SUCCESS, expectedModel);
//    }
//
//    @Test
//    public void execute_nonEmptyAddressBook_success() {
//        CalendarModel calendarModel = new CalendarModelManager(getTypicalAddressBook(), new CalendarUserPrefs());
//        CalendarModel expectedModel = new CalendarModelManager(getTypicalAddressBook(), new CalendarUserPrefs());
//        expectedModel.setCalendarAddressBook(new CalendarAddressBook());
//
//        assertCommandSuccess(new ClearCommand(), calendarModel, ClearCommand.MESSAGE_SUCCESS, expectedModel);
//    }
//
//}
