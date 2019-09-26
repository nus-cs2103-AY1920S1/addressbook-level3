package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.TypicalPersons.CARL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupDescriptor;
import seedu.address.model.group.GroupId;
import seedu.address.model.group.GroupList;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.GroupRemark;
import seedu.address.model.mapping.PersonToGroupMapping;
import seedu.address.model.mapping.PersonToGroupMappingList;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonDescriptor;
import seedu.address.model.person.PersonList;
import seedu.address.model.person.schedule.Event;
import seedu.address.model.person.schedule.Timeslot;
import seedu.address.model.person.schedule.Venue;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonDescriptorBuilder;

public class ModelManagerTest {

    private ModelManager modelManager;
    private Person alice;
    private Person benson;
    private Event event;
    private Timeslot timeslot;

    @BeforeEach
    void init(){
        PersonList personList = new PersonList();
        GroupList groupList = new GroupList();
        PersonToGroupMappingList personToGroupMappingList = new PersonToGroupMappingList();

        alice = personList.addPerson(new PersonDescriptorBuilder(ALICE).build());
        benson = personList.addPerson(new PersonDescriptorBuilder(BENSON).build());

        Group group1 = groupList.addGroup(new GroupDescriptor(new GroupName("group1"), new GroupRemark("remark1")));
        Group group2 = groupList.addGroup(new GroupDescriptor(new GroupName("group2"), new GroupRemark("remark2")));

        personToGroupMappingList.addPersonToGroupMapping(new PersonToGroupMapping(alice.getPersonId(), group1.getGroupId()));
        personToGroupMappingList.addPersonToGroupMapping(new PersonToGroupMapping(alice.getPersonId(), group2.getGroupId()));
        personToGroupMappingList.addPersonToGroupMapping(new PersonToGroupMapping(benson.getPersonId(), group1.getGroupId()));

        modelManager = new ModelManager(personList, groupList, personToGroupMappingList);

        LocalDateTime startTime = LocalDateTime.parse("2007-12-03T10:15:30");
        LocalDateTime endTime = LocalDateTime.parse("2007-12-03T10:16:30");
        Venue venue = new Venue("venue1");
        timeslot = new Timeslot(startTime, endTime, venue);
        event = new Event("event");

    }

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
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

    @Test
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
    }


    @Test
    void getPersonList() {
        PersonList personList2 = modelManager.getPersonList();
        assertNotNull(personList2);

        assertNotNull(personList2.findPerson(alice.getName()));
    }

    @Test
    void addPerson() {
        PersonDescriptor personDescriptor = new PersonDescriptorBuilder(CARL).build();
        PersonBuilder personBuilder = new PersonBuilder(personDescriptor);
        Person person = modelManager.addPerson(personDescriptor);
        assertTrue(person.equals(personBuilder.build()));
    }

    @Test
    void findPerson() {
        Person person = modelManager.findPerson(ALICE.getName());
        assertTrue(ALICE.equals(person));
    }

    @Test
    void testFindPerson() {
        Person person = modelManager.findPerson(BENSON.getName());
        assertTrue(BENSON.equals(person));
    }

    @Test
    void addEvent() {
        assertTrue(modelManager.addEvent(ALICE.getName(), event));
    }

    @Test
    void editPerson() {
        assertNull(modelManager.editPerson(ALICE.getName(), new PersonDescriptorBuilder(BENSON).build()));
        assertNotNull(modelManager.editPerson(ALICE.getName(), new PersonDescriptorBuilder(CARL).build()));

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
        assertTrue(groups.size() == 2);
    }

    @Test
    void getGroupList() {
    }

    @Test
    void addGroup() {
    }

    @Test
    void editGroup() {
    }

    @Test
    void findGroup() {
    }

    @Test
    void testFindGroup() {
    }

    @Test
    void deleteGroup() {
    }

    @Test
    void findPersonsOfGroup() {
    }

    @Test
    void getPersonToGroupMappingList() {
    }

    @Test
    void addPersonToGroupMapping() {
    }

    @Test
    void findPersonToGroupMapping() {
    }

    @Test
    void deletePersonToGroupMapping() {
    }

    @Test
    void deletePersonFromMapping() {
    }

    @Test
    void deleteGroupFromMapping() {
    }
}
