package seedu.module.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.module.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
//import static seedu.module.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.module.testutil.Assert.assertThrows;
//import static seedu.module.testutil.TypicalPersons.ALICE;
//import static seedu.module.testutil.TypicalPersons.getTypicalAddressBook;

//import java.util.Arrays;
//import java.util.Collection;

import java.util.Collections;
//import java.util.List;

import org.junit.jupiter.api.Test;

//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import seedu.module.model.module.Module;
//import seedu.module.model.module.exceptions.DuplicateModuleException;
//import seedu.module.testutil.PersonBuilder;

public class ModuleBookTest {

    private final ModuleBook moduleBook = new ModuleBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), moduleBook.getModuleList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> moduleBook.resetData(null));
    }

    //     @Test
    //     public void resetData_withValidReadOnlyAddressBook_replacesData() {
    //         ModuleBook newData = getTypicalAddressBook();
    //         addressBook.resetData(newData);
    //         assertEquals(newData, addressBook);
    //     }

    //     @Test
    //     public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
    //         // Two persons with the same identity fields
    //         Module editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
    //                 .build();
    //         List<Module> newModules = Arrays.asList(ALICE, editedAlice);
    //         ModuleBookStub newData = new ModuleBookStub(newModules);

    //         assertThrows(DuplicateModuleException.class, () -> addressBook.resetData(newData));
    //      }

    @Test
    public void hasModule_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> moduleBook.hasModule(null));
    }
}

//     @Test
//     public void hasPerson_personNotInAddressBook_returnsFalse() {
//         assertFalse(addressBook.hasModule(ALICE));
//     }

//     @Test
//     public void hasPerson_personInAddressBook_returnsTrue() {
//         addressBook.addModule(ALICE);
//         assertTrue(addressBook.hasModule(ALICE));
//     }

//     @Test
//     public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
//         addressBook.addModule(ALICE);
//         Module editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
//                 .build();
//         assertTrue(addressBook.hasModule(editedAlice));
//     }

//     @Test
//     public void getPersonList_modifyList_throwsUnsupportedOperationException() {
//         assertThrows(UnsupportedOperationException.class, () -> addressBook.getModuleList().remove(0));
//     }

//     /**
//      * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
//      */
//     private static class ModuleBookStub implements ReadOnlyModuleBook {
//         private final ObservableList<Module> modules = FXCollections.observableArrayList();

//         ModuleBookStub(Collection<Module> modules) {
//             this.modules.setAll(modules);
//         }

//         @Override
//         public ObservableList<Module> getModuleList() {
//             return modules;
//         }
//     }

// }
