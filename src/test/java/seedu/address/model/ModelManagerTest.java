package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP0;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUPNAME0;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUPNAME1;
import static seedu.address.testutil.mappingutil.TypicalMappings.MAP00;
import static seedu.address.testutil.mappingutil.TypicalMappings.MAP20;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.BENSON;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ZACK;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupId;
import seedu.address.model.group.GroupList;
import seedu.address.model.mapping.PersonToGroupMappingList;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.PersonList;
import seedu.address.testutil.grouputil.TypicalGroups;
import seedu.address.testutil.mappingutil.TypicalMappings;
import seedu.address.testutil.personutil.PersonBuilder;
import seedu.address.testutil.personutil.TypicalPersonDescriptor;
import seedu.address.testutil.scheduleutil.TypicalEvents;

public class ModelManagerTest {

    private ModelManager modelManager;

    @BeforeEach
    void init() {
        Person.counterReset();
        Group.counterReset();

        PersonList personList = TypicalPersonDescriptor.generateTypicalPersonList();
        GroupList groupList = TypicalGroups.generateTypicalGroupList();
        PersonToGroupMappingList personToGroupMappingList = TypicalMappings.generateTypicalMappingList();

        modelManager = new ModelManager(personList, groupList, personToGroupMappingList);
    }

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(modelManager.getAddressBook()), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    /*@Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }*/


    @Test
    void getPersonList() {
        PersonList personList2 = modelManager.getPersonList();

        assertNotNull(personList2);
        assertNotNull(personList2.findPerson(ALICE.getName()));
        assertNull(personList2.findPerson(ZACK.getName()));
    }

    @Test
    void addPerson() {
        Person person = modelManager.addPerson(ZACK);

        assertNotNull(person);
        assertTrue(person.isSamePerson(new PersonBuilder(ZACK).build()));
        assertTrue(person.isSamePerson(person));

        assertFalse(person.isSamePerson(new PersonBuilder(ALICE).build()));
        assertFalse(person.equals(new PersonBuilder(ZACK).build()));
    }

    @Test
    void findPerson() {
        Person person = modelManager.findPerson(ALICE.getName());

        assertTrue(person.isSamePerson(new PersonBuilder(ALICE).build()));
        assertTrue(person.isSamePerson(person));
    }

    @Test
    void testFindPerson() {
        Person person = modelManager.findPerson(BENSON.getName());
        assertTrue(person.isSamePerson(person));
        assertTrue(person.equals(person));
    }

    @Test
    void addEvent() {
        assertTrue(modelManager.addEvent(BENSON.getName(), TypicalEvents.generateTypicalEvent2()));
        assertTrue(modelManager.addEvent(ALICE.getName(), TypicalEvents.generateTypicalEvent1()));

        assertFalse(modelManager.addEvent(ZACK.getName(), TypicalEvents.generateTypicalEvent2()));
    }

    @Test
    void editPerson() {
        assertNotNull(modelManager.editPerson(ALICE.getName(), ZACK));
        assertNull(modelManager.editPerson(ALICE.getName(), BENSON));
    }

    @Test
    void deletePerson() {
        Person person = modelManager.findPerson(ALICE.getName());
        assertTrue(modelManager.deletePerson(person.getPersonId()));
    }

    @Test
    void findGroupsOfPerson() {
        Person person = modelManager.findPerson(ALICE.getName());
        ArrayList<GroupId> groups = modelManager.findGroupsOfPerson(person.getPersonId());
        assertTrue(groups.size() == 3);
    }

    @Test
    void getGroupList() {
        assertNotNull(modelManager.getGroupList());
        assertNotNull(modelManager.findGroup(GROUPNAME1));
        assertNull(modelManager.findGroup(GROUPNAME0));
    }

    @Test
    void addGroup() {
        assertNull(modelManager.addGroup(GROUP1));
        assertNotNull(modelManager.addGroup(GROUP0));
        assertNull(modelManager.addGroup(GROUP0));
    }

    @Test
    void editGroup() {
        assertNull(modelManager.editGroup(GROUPNAME1, GROUP1));
        assertNotNull(modelManager.editGroup(GROUPNAME1, GROUP0));
    }

    @Test
    void findGroup() {
        assertNull(modelManager.findGroup(GROUPNAME0));
        assertNotNull(modelManager.findGroup(GROUPNAME1));
    }

    @Test
    void testFindGroup() {
        Group group = modelManager.findGroup(GROUPNAME1);
        assertNotNull(group);

        assertNotNull(modelManager.findGroup(group.getGroupId()));
    }

    @Test
    void deleteGroup() {
        Group group = modelManager.findGroup(GROUPNAME1);
        assertTrue(modelManager.deleteGroup(group.getGroupId()));

        assertFalse(modelManager.deleteGroup(group.getGroupId()));
    }

    @Test
    void findPersonsOfGroup() {
        Group group = modelManager.findGroup(GROUPNAME1);
        assertNotNull(group);
        ArrayList<PersonId> arr = modelManager.findPersonsOfGroup(group.getGroupId());
        assertNotNull(arr);
        assertTrue(arr.size() == 2);
    }

    @Test
    void getPersonToGroupMappingList() {
        assertNotNull(modelManager.getPersonToGroupMappingList());
    }

    @Test
    void addPersonToGroupMapping() {
        assertTrue(modelManager.addPersonToGroupMapping(MAP20));
        assertFalse(modelManager.addPersonToGroupMapping(MAP00));
    }

    @Test
    void findPersonToGroupMapping() {
        assertNotNull(modelManager.findPersonToGroupMapping(MAP00.getPersonId(), MAP00.getGroupId()));
        assertNull(modelManager.findPersonToGroupMapping(MAP20.getPersonId(), MAP20.getGroupId()));
    }

    @Test
    void deletePersonToGroupMapping() {
        assertTrue(modelManager.deletePersonToGroupMapping(MAP00));
        assertFalse(modelManager.deletePersonToGroupMapping(MAP00));
    }

    @Test
    void deletePersonFromMapping() {
        Person person = modelManager.findPerson(ALICE.getName());
        assertNotNull(person);

        modelManager.deletePersonFromMapping(person.getPersonId());
        ArrayList<GroupId> arr = modelManager.findGroupsOfPerson(person.getPersonId());
        assertTrue(arr.size() == 0);
    }

    @Test
    void deleteGroupFromMapping() {
        Group group = modelManager.findGroup(GROUPNAME1);
        assertNotNull(group);

        modelManager.deleteGroupFromMapping(group.getGroupId());
        ArrayList<PersonId> arr = modelManager.findPersonsOfGroup(group.getGroupId());
        assertTrue(arr.size() == 0);
    }
}
