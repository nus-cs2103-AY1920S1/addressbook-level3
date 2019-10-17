//package seedu.address.logic.calendar.commands;
//
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.address.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.logic.commands.CommandResult;
//import seedu.address.logic.commands.ExitCommand;
//
//
//public class ExitCommandTest {
//    private CalendarModel calendarModel = new CalendarModelManager();
//    private CalendarModel expectedModel = new CalendarModelManager();
//
//    @Test
//    public void execute_exit_success() {
//        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
//        assertCommandSuccess(new ExitCommand(), calendarModel, expectedCommandResult, expectedModel);
//    }
//}
