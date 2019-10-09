// package seedu.module.logic.commands;

// import static seedu.module.logic.commands.CommandTestUtil.assertCommandFailure;
// import static seedu.module.logic.commands.CommandTestUtil.assertCommandSuccess;
// import static seedu.module.testutil.TypicalPersons.getTypicalAddressBook;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import seedu.module.model.Model;
// import seedu.module.model.ModelManager;
// import seedu.module.model.UserPrefs;
// import seedu.module.model.module.Module;
// import seedu.module.testutil.PersonBuilder;

// /**
//  * Contains integration tests (interaction with the Model) for {@code AddCommand}.
//  */
// public class AddCommandIntegrationTest {

//     private Model model;

//     @BeforeEach
//     public void setUp() {
//         model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
//     }

//     @Test
//     public void execute_newPerson_success() {
//         Module validModule = new PersonBuilder().build();

//         Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
//         expectedModel.addModule(validModule);

//         assertCommandSuccess(new AddCommand(validModule), model,
//                 String.format(AddCommand.MESSAGE_SUCCESS, validModule), expectedModel);
//     }

//     @Test
//     public void execute_duplicatePerson_throwsCommandException() {
//         Module moduleInList = model.getAddressBook().getModuleList().get(0);
//         assertCommandFailure(new AddCommand(moduleInList), model, AddCommand.MESSAGE_DUPLICATE_MODULE);
//     }

// }
