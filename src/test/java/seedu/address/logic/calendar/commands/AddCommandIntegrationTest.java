//package seedu.address.logic.calendar.commands;
//
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import seedu.address.logic.commands.AddCommand;
//import seedu.address.calendarModel.task.Task;
//import seedu.address.testutil.PersonBuilder;
//
//
//
///**
// * Contains integration tests (interaction with the CalendarModel) for {@code AddCommand}.
// */
//public class AddCommandIntegrationTest {
//
//    private CalendarModel calendarModel;
//
//    @BeforeEach
//    public void setUp() {
//        calendarModel = new CalendarCalendarModelManager(getTypicalAddressBook(), new CalendarUserPrefs());
//    }
//
//    @Test
//    public void execute_newPerson_success() {
//        Task validPerson = new PersonBuilder().build();
//
//        CalendarModel expectedModel = new CalendarCalendarModelManager(calendarModel.getCalendarAddressBook(), new CalendarUserPrefs());
//        expectedModel.addPerson(validPerson);
//
//        assertCommandSuccess(new AddCommand(validPerson), calendarModel,
//                String.format(AddCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
//    }
//
//    @Test
//    public void execute_duplicatePerson_throwsCommandException() {
//        Task personInList = calendarModel.getCalendarAddressBook().getPersonList().get(0);
//        assertCommandFailure(new AddCommand(personInList), calendarModel, AddCommand.MESSAGE_DUPLICATE_PERSON);
//    }
//
//}
