package seedu.address.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupDescriptor;
import seedu.address.model.group.GroupId;
import seedu.address.model.group.GroupList;
import seedu.address.model.group.GroupName;
import seedu.address.model.mapping.PersonToGroupMapping;
import seedu.address.model.mapping.PersonToGroupMappingList;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonDescriptor;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.PersonList;
import seedu.address.model.person.schedule.Event;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);
    boolean deletePerson(PersonId personId);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    boolean addPerson(PersonDescriptor personDescriptor);

    String personListToString();


    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    boolean addGroup(GroupDescriptor groupDescriptor);

    String list();

    boolean addEvent(Name name, Event event);

    boolean addPersonToGroupMapping(PersonToGroupMapping mapping);

    Person findPerson(Name name);

    Person findPerson(PersonId personId);

    Group findGroup(GroupName groupName);

    Group findGroup(GroupId groupId);

    PersonToGroupMapping findPersonToGroupMapping(PersonId personId, GroupId groupId);

    boolean deletePersonToGroupMapping(PersonToGroupMapping mapping);

    void deletePersonFromMapping(PersonId personId);

    void deleteGroupFromMapping(GroupId groupId);

    boolean deleteGroup(GroupId groupId);

    ArrayList<GroupId> findGroupsOfPerson(PersonId personId);

    ArrayList<PersonId> findPersonsOfGroup(GroupId groupId);

    PersonList getPersonList();

    GroupList getGroupList();

    PersonToGroupMappingList getPersonToGroupMappingList();

    Person editPerson(Name name, PersonDescriptor personDescriptor);

}
