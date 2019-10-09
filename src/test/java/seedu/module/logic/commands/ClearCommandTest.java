// package seedu.module.logic.commands;

// import static seedu.module.testutil.TypicalPersons.getTypicalAddressBook;

// import org.junit.jupiter.api.Disabled;
// import org.junit.jupiter.api.Test;

// import seedu.module.model.Model;
// import seedu.module.model.ModelManager;
// import seedu.module.model.ModuleBook;
// import seedu.module.model.UserPrefs;

// @Disabled("Migrated AddressBook to ModuleBook")
// public class ClearCommandTest {

//     @Test
//     public void execute_emptyAddressBook_success() {
//         Model model = new ModelManager();
//         Model expectedModel = new ModelManager();

//         assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
//     }

//     @Test
//     public void execute_nonEmptyAddressBook_success() {
//         Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
//         Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
//         expectedModel.setAddressBook(new ModuleBook());

//         assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
//     }

// }
