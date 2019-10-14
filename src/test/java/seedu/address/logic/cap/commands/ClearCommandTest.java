package seedu.address.logic.cap.commands;//package seedu.address.logic.calendar.commands;
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
//        Model model = new ModelManager();
//        Model expectedModel = new ModelManager();
//
//        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
//    }
//
//    @Test
//    public void execute_nonEmptyAddressBook_success() {
//        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
//        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
//        expectedModel.setAddressBook(new AddressBook());
//
//        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
//    }
//
//}
