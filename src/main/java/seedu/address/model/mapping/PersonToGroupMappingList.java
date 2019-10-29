package seedu.address.model.mapping;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.group.GroupId;
import seedu.address.model.mapping.exceptions.DuplicateMappingException;
import seedu.address.model.mapping.exceptions.MappingNotFoundException;
import seedu.address.model.person.PersonId;

/**
 * List of mappings.
 */
public class PersonToGroupMappingList {
    private ArrayList<PersonToGroupMapping> mappings;

    public PersonToGroupMappingList() {
        this.mappings = new ArrayList<>();
    }

    /**
     * Adds a mapping to the list of mappings, will not add when a duplicate is found.
     *
     * @param map mapping to be added
     */
    public void addPersonToGroupMapping(PersonToGroupMapping map) throws DuplicateMappingException {
        try {
            findPersonToGroupMapping(map.getPersonId(), map.getGroupId());
            throw new DuplicateMappingException();
        } catch (MappingNotFoundException e) {
            this.mappings.add(map);
        }

    }

    /**
     * finds a mapping with personId and groupId and returns the mapping.
     *
     * @param personId of the mapping
     * @param groupId  of the mapping
     * @return mapping found
     */
    public PersonToGroupMapping findPersonToGroupMapping(PersonId personId, GroupId groupId)
            throws MappingNotFoundException {

        for (int i = 0; i < mappings.size(); i++) {
            if (mappings.get(i).getPersonId().equals(personId) && mappings.get(i).getGroupId().equals(groupId)) {
                return mappings.get(i);
            }
        }
        throw new MappingNotFoundException();
    }

    /**
     * Deletes a mapping from the list of mapping.
     *
     * @param mapping to be deleted
     */
    public void deletePersonToGroupMapping(PersonToGroupMapping mapping) throws MappingNotFoundException {
        int i;
        for (i = 0; i < mappings.size(); i++) {
            if (mappings.get(i).equals(mapping)) {
                mappings.remove(i);
                return;
            }
        }
        throw new MappingNotFoundException();
    }

    /**
     * Deletes all mapping with personId.
     *
     * @param personId to be deleted
     */
    public void deletePersonFromMapping(PersonId personId) {
        int i;
        for (i = 0; i < mappings.size(); i++) {
            if (mappings.get(i).getPersonId().equals(personId)) {
                mappings.remove(i);
                i -= 1;
            }
        }
    }

    /**
     * Deletes all mapping with groupId.
     *
     * @param groupId to be deleted
     */
    public void deleteGroupFromMapping(GroupId groupId) {
        int i;
        for (i = 0; i < mappings.size(); i++) {
            if (mappings.get(i).getGroupId().equals(groupId)) {
                mappings.remove(i);
                i -= 1;
            }
        }
    }

    /**
     * Finds all groups with person with personId.
     *
     * @param personId to be found
     * @return list of groupId
     */
    public ArrayList<GroupId> findGroupsOfPerson(PersonId personId) {
        int i;
        ArrayList<GroupId> groups = new ArrayList<GroupId>();
        for (i = 0; i < mappings.size(); i++) {
            if (mappings.get(i).getPersonId().equals(personId)) {
                groups.add(mappings.get(i).getGroupId());
            }
        }
        return groups;
    }

    /**
     * Finds all persons of a Group with groupId.
     *
     * @param groupId to be found
     * @return list of personId
     */
    public ArrayList<PersonId> findPersonsOfGroup(GroupId groupId) {
        int i;
        ArrayList<PersonId> persons = new ArrayList<PersonId>();
        for (i = 0; i < mappings.size(); i++) {
            if (mappings.get(i).getGroupId().equals(groupId)) {
                persons.add(mappings.get(i).getPersonId());
            }
        }
        return persons;
    }

    /**
     * Converts to String.
     *
     * @return String
     */
    public String toString() {
        int i;
        String output = "";
        for (i = 0; i < mappings.size(); i++) {
            output += mappings.get(i).toString();
            output += "\n";
        }
        return output;
    }

    /**
     * Returns an unmodifiable observable list of mappings.
     *
     * @return ObservableList
     */
    public ObservableList<PersonToGroupMapping> asUnmodifiableObservableList() {
        ObservableList<PersonToGroupMapping> observableList = FXCollections.observableArrayList(mappings);
        return FXCollections.unmodifiableObservableList(observableList);
    }

    /**
     * Returns the role of the mapping.
     *
     * @param personId of the mapping
     * @param groupId  of the mapping
     * @return Role
     */
    public Role findRole(PersonId personId, GroupId groupId) throws MappingNotFoundException {

        for (int i = 0; i < mappings.size(); i++) {
            if (mappings.get(i).getGroupId().equals(groupId) && mappings.get(i).getPersonId().equals(personId)) {
                return mappings.get(i).getRole();
            }
        }

        throw new MappingNotFoundException();
    }
}
