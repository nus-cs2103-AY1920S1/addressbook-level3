// package seedu.module.model.module;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static seedu.module.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
// import static seedu.module.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
// import static seedu.module.testutil.Assert.assertThrows;
// import static seedu.module.testutil.TypicalPersons.ALICE;
// import static seedu.module.testutil.TypicalPersons.BOB;

// import java.util.Arrays;
// import java.util.Collections;
// import java.util.List;

// import org.junit.jupiter.api.Test;

// import seedu.module.model.module.exceptions.DuplicateModuleException;
// import seedu.module.model.module.exceptions.ModuleNotFoundException;
// import seedu.module.testutil.PersonBuilder;

// public class UniqueModuleListTest {

//     private final UniqueModuleList uniqueModuleList = new UniqueModuleList();

//     @Test
//     public void contains_nullPerson_throwsNullPointerException() {
//         assertThrows(NullPointerException.class, () -> uniqueModuleList.contains(null));
//     }

//     @Test
//     public void contains_personNotInList_returnsFalse() {
//         assertFalse(uniqueModuleList.contains(ALICE));
//     }

//     @Test
//     public void contains_personInList_returnsTrue() {
//         uniqueModuleList.add(ALICE);
//         assertTrue(uniqueModuleList.contains(ALICE));
//     }

//     @Test
//     public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
//         uniqueModuleList.add(ALICE);
//         Module editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
//                 .build();
//         assertTrue(uniqueModuleList.contains(editedAlice));
//     }

//     @Test
//     public void add_nullPerson_throwsNullPointerException() {
//         assertThrows(NullPointerException.class, () -> uniqueModuleList.add(null));
//     }

//     @Test
//     public void add_duplicatePerson_throwsDuplicatePersonException() {
//         uniqueModuleList.add(ALICE);
//         assertThrows(DuplicateModuleException.class, () -> uniqueModuleList.add(ALICE));
//     }

//     @Test
//     public void setPerson_nullTargetPerson_throwsNullPointerException() {
//         assertThrows(NullPointerException.class, () -> uniqueModuleList.setModule(null, ALICE));
//     }

//     @Test
//     public void setPerson_nullEditedPerson_throwsNullPointerException() {
//         assertThrows(NullPointerException.class, () -> uniqueModuleList.setModule(ALICE, null));
//     }

//     @Test
//     public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
//         assertThrows(ModuleNotFoundException.class, () -> uniqueModuleList.setModule(ALICE, ALICE));
//     }

//     @Test
//     public void setPerson_editedPersonIsSamePerson_success() {
//         uniqueModuleList.add(ALICE);
//         uniqueModuleList.setModule(ALICE, ALICE);
//         UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
//         expectedUniqueModuleList.add(ALICE);
//         assertEquals(expectedUniqueModuleList, uniqueModuleList);
//     }

//     @Test
//     public void setPerson_editedPersonHasSameIdentity_success() {
//         uniqueModuleList.add(ALICE);
//         Module editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
//                 .build();
//         uniqueModuleList.setModule(ALICE, editedAlice);
//         UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
//         expectedUniqueModuleList.add(editedAlice);
//         assertEquals(expectedUniqueModuleList, uniqueModuleList);
//     }

//     @Test
//     public void setPerson_editedPersonHasDifferentIdentity_success() {
//         uniqueModuleList.add(ALICE);
//         uniqueModuleList.setModule(ALICE, BOB);
//         UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
//         expectedUniqueModuleList.add(BOB);
//         assertEquals(expectedUniqueModuleList, uniqueModuleList);
//     }

//     @Test
//     public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
//         uniqueModuleList.add(ALICE);
//         uniqueModuleList.add(BOB);
//         assertThrows(DuplicateModuleException.class, () -> uniqueModuleList.setModule(ALICE, BOB));
//     }

//     @Test
//     public void remove_nullPerson_throwsNullPointerException() {
//         assertThrows(NullPointerException.class, () -> uniqueModuleList.remove(null));
//     }

//     @Test
//     public void remove_personDoesNotExist_throwsPersonNotFoundException() {
//         assertThrows(ModuleNotFoundException.class, () -> uniqueModuleList.remove(ALICE));
//     }

//     @Test
//     public void remove_existingPerson_removesPerson() {
//         uniqueModuleList.add(ALICE);
//         uniqueModuleList.remove(ALICE);
//         UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
//         assertEquals(expectedUniqueModuleList, uniqueModuleList);
//     }

//     @Test
//     public void setPersons_nullUniquePersonList_throwsNullPointerException() {
//         assertThrows(NullPointerException.class, () -> uniqueModuleList.setModules((UniqueModuleList) null));
//     }

//     @Test
//     public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
//         uniqueModuleList.add(ALICE);
//         UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
//         expectedUniqueModuleList.add(BOB);
//         uniqueModuleList.setModules(expectedUniqueModuleList);
//         assertEquals(expectedUniqueModuleList, uniqueModuleList);
//     }

//     @Test
//     public void setPersons_nullList_throwsNullPointerException() {
//         assertThrows(NullPointerException.class, () -> uniqueModuleList.setModules((List<Module>) null));
//     }

//     @Test
//     public void setPersons_list_replacesOwnListWithProvidedList() {
//         uniqueModuleList.add(ALICE);
//         List<Module> moduleList = Collections.singletonList(BOB);
//         uniqueModuleList.setModules(moduleList);
//         UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
//         expectedUniqueModuleList.add(BOB);
//         assertEquals(expectedUniqueModuleList, uniqueModuleList);
//     }

//     @Test
//     public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
//         List<Module> listWithDuplicateModules = Arrays.asList(ALICE, ALICE);
//         assertThrows(DuplicateModuleException.class, () -> uniqueModuleList.setModules(listWithDuplicateModules));
//     }

//     @Test
//     public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
//         assertThrows(UnsupportedOperationException.class, ()
//                 -> uniqueModuleList.asUnmodifiableObservableList().remove(0));
//     }
// }
