package seedu.address.model;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupList;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.mapping.PersonToGroupMapping;
import seedu.address.model.mapping.PersonToGroupMappingList;
import seedu.address.model.mapping.exceptions.AlreadyInGroupException;
import seedu.address.model.mapping.exceptions.DuplicateMappingException;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.PersonList;
import seedu.address.model.person.User;

/**
 * Contains all the information about the TimeBook.
 */
public class TimeBook {

    private PersonList personList;
    private GroupList groupList;
    private PersonToGroupMappingList personToGroupMappingList;

    public TimeBook(Person user) {
        this.personList = new PersonList((User) user);
        this.groupList = new GroupList();
        this.personToGroupMappingList = new PersonToGroupMappingList();
    }

    public TimeBook() {
        this.personList = new PersonList();
        this.groupList = new GroupList();
        this.personToGroupMappingList = new PersonToGroupMappingList();
    }

    public TimeBook(PersonList personList,
                    GroupList groupList,
                    PersonToGroupMappingList personToGroupMappingList) {

        this.personList = personList;
        this.groupList = groupList;
        this.personToGroupMappingList = personToGroupMappingList;
    }

    public void addPerson(Person person) {
        this.personList.addPerson(person);
    }

    public void addGroup(Group group) {
        this.groupList.addGroup(group);
    }

    /**
     * Adds a mapping into TimeBook.
     */
    public void addMapping(PersonToGroupMapping map) {
        try {
            this.personToGroupMappingList.addPersonToGroupMapping(map);
        } catch (DuplicateMappingException | AlreadyInGroupException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Person> getPersonsOfGroup(GroupName groupName) throws GroupNotFoundException {
        Group group = groupList.findGroup(groupName);
        ArrayList<PersonId> personIds = personToGroupMappingList
                .findPersonsOfGroup(group.getGroupId());

        ArrayList<Person> persons = new ArrayList<>();
        persons.add(personList.getUser());
        for (PersonId personId : personIds) {
            persons.add(personList.findPerson(personId));
        }
        return persons;
    }

    public PersonList getPersonList() {
        return this.personList;
    }

    public ObservableList<Person> getUnmodifiablePersonList() {
        return personList.asUnmodifiableObservableList();
    }

    public GroupList getGroupList() {
        return this.groupList;
    }

    public ObservableList<Group> getUnmodifiableGroupList() {
        return groupList.asUnmodifiableObservableList();
    }

    public PersonToGroupMappingList getPersonToGroupMappingList() {
        return this.personToGroupMappingList;
    }

    public ObservableList<PersonToGroupMapping> getUnmodifiableMappingList() {
        return personToGroupMappingList.asUnmodifiableObservableList();
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TimeBook // instanceof handles nulls
                && personList.equals(((TimeBook) other).personList))
                && groupList.equals(((TimeBook) other).groupList)
                && personToGroupMappingList.equals(((TimeBook) other).personToGroupMappingList);
    }
}
