//package seedu.guilttrip.logic.commands;
//
//import static seedu.guilttrip.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.guilttrip.testutil.TypicalEntries.getTypicalAddressBook;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.guilttrip.model.GuiltTrip;
//import seedu.guilttrip.model.Model;
//import seedu.guilttrip.model.ModelManager;
//import seedu.guilttrip.model.UserPrefs;
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
//        expectedModel.setAddressBook(new GuiltTrip());
//
//        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
//    }
//
//}
