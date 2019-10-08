package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupList;
import seedu.address.model.mapping.PersonToGroupMapping;
import seedu.address.model.mapping.PersonToGroupMappingList;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonList;

/**
 * Contains all the information about the TimeBook.
 */
public class TimeBook {

    private PersonList personList;
    private GroupList groupList;
    private PersonToGroupMappingList personToGroupMappingList;

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

    public void addMapping(PersonToGroupMapping map) {
        this.personToGroupMappingList.addPersonToGroupMapping(map);
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
}
