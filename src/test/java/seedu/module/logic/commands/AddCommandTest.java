// package seedu.module.logic.commands;

// import static java.util.Objects.requireNonNull;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static seedu.module.testutil.Assert.assertThrows;

// import java.nio.file.Path;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.function.Predicate;

// import org.junit.jupiter.api.Test;

// import javafx.collections.ObservableList;
// import seedu.module.commons.core.GuiSettings;
// import seedu.module.logic.commands.exceptions.CommandException;
// import seedu.module.model.Model;
// import seedu.module.model.ModuleBook;
// import seedu.module.model.ReadOnlyModuleBook;
// import seedu.module.model.ReadOnlyUserPrefs;
// import seedu.module.model.module.Module;
// import seedu.module.testutil.PersonBuilder;

// public class AddCommandTest {

//     @Test
//     public void constructor_nullPerson_throwsNullPointerException() {
//         assertThrows(NullPointerException.class, () -> new AddCommand(null));
//     }

//     @Test
//     public void execute_personAcceptedByModel_addSuccessful() throws Exception {
//         ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
//         Module validModule = new PersonBuilder().build();

//         CommandResult commandResult = new AddCommand(validModule).execute(modelStub);

//         assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validModule),
//              commandResult.getFeedbackToUser());
//         assertEquals(Arrays.asList(validModule), modelStub.personsAdded);
//     }

//     @Test
//     public void execute_duplicatePerson_throwsCommandException() {
//         Module validModule = new PersonBuilder().build();
//         AddCommand addCommand = new AddCommand(validModule);
//         ModelStub modelStub = new ModelStubWithPerson(validModule);

//         assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_MODULE,
//            () -> addCommand.execute(modelStub));
//     }

//     @Test
//     public void equals() {
//         Module alice = new PersonBuilder().withName("Alice").build();
//         Module bob = new PersonBuilder().withName("Bob").build();
//         AddCommand addAliceCommand = new AddCommand(alice);
//         AddCommand addBobCommand = new AddCommand(bob);

//         // same object -> returns true
//         assertTrue(addAliceCommand.equals(addAliceCommand));

//         // same values -> returns true
//         AddCommand addAliceCommandCopy = new AddCommand(alice);
//         assertTrue(addAliceCommand.equals(addAliceCommandCopy));

//         // different types -> returns false
//         assertFalse(addAliceCommand.equals(1));

//         // null -> returns false
//         assertFalse(addAliceCommand.equals(null));

//         // different person -> returns false
//         assertFalse(addAliceCommand.equals(addBobCommand));
//     }

//     /**
//      * A default model stub that have all of the methods failing.
//      */
//     private class ModelStub implements Model {
//         @Override
//         public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
//             throw new AssertionError("This method should not be called.");
//         }

//         @Override
//         public ReadOnlyUserPrefs getUserPrefs() {
//             throw new AssertionError("This method should not be called.");
//         }

//         @Override
//         public GuiSettings getGuiSettings() {
//             throw new AssertionError("This method should not be called.");
//         }

//         @Override
//         public void setGuiSettings(GuiSettings guiSettings) {
//             throw new AssertionError("This method should not be called.");
//         }

//         @Override
//         public Path getAddressBookFilePath() {
//             throw new AssertionError("This method should not be called.");
//         }

//         @Override
//         public void setAddressBookFilePath(Path addressBookFilePath) {
//             throw new AssertionError("This method should not be called.");
//         }

//         @Override
//         public void addModule(Module module) {
//             throw new AssertionError("This method should not be called.");
//         }

//         @Override
//         public void setAddressBook(ReadOnlyModuleBook newData) {
//             throw new AssertionError("This method should not be called.");
//         }

//         @Override
//         public ReadOnlyModuleBook getAddressBook() {
//             throw new AssertionError("This method should not be called.");
//         }

//         @Override
//         public boolean hasModule(Module module) {
//             throw new AssertionError("This method should not be called.");
//         }

//         @Override
//         public void deleteModule(Module target) {
//             throw new AssertionError("This method should not be called.");
//         }

//         @Override
//         public void setModule(Module target, Module editedModule) {
//             throw new AssertionError("This method should not be called.");
//         }

//         @Override
//         public ObservableList<Module> getFilteredModuleList() {
//             throw new AssertionError("This method should not be called.");
//         }

//         @Override
//         public void updateFilteredModuleList(Predicate<Module> predicate) {
//             throw new AssertionError("This method should not be called.");
//         }
//     }

//     /**
//      * A Model stub that contains a single person.
//      */
//     private class ModelStubWithPerson extends ModelStub {
//         private final Module module;

//         ModelStubWithPerson(Module module) {
//             requireNonNull(module);
//             this.module = module;
//         }

//         @Override
//         public boolean hasModule(Module module) {
//             requireNonNull(module);
//             return this.module.isSameModule(module);
//         }
//     }

//     /**
//      * A Model stub that always accept the person being added.
//      */
//     private class ModelStubAcceptingPersonAdded extends ModelStub {
//         final ArrayList<Module> personsAdded = new ArrayList<>();

//         @Override
//         public boolean hasModule(Module module) {
//             requireNonNull(module);
//             return personsAdded.stream().anyMatch(module::isSameModule);
//         }

//         @Override
//         public void addModule(Module module) {
//             requireNonNull(module);
//             personsAdded.add(module);
//         }

//         @Override
//         public ReadOnlyModuleBook getAddressBook() {
//             return new ModuleBook();
//         }
//     }

// }
