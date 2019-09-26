package seedu.address.model.mapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.group.GroupId;
import seedu.address.model.person.PersonId;

class PersonToGroupMappingListTest {

    private PersonToGroupMappingList mappingList;
    private PersonToGroupMapping map11;
    private PersonToGroupMapping map12;
    private PersonToGroupMapping map13;
    private PersonToGroupMapping map21;
    private PersonToGroupMapping map22;
    private PersonToGroupMapping map33;

    @BeforeEach
    void init() {
        mappingList = new PersonToGroupMappingList();
        map11 = new PersonToGroupMapping(new PersonId(1), new GroupId(1));
        map12 = new PersonToGroupMapping(new PersonId(1), new GroupId(2));
        map13 = new PersonToGroupMapping(new PersonId(1), new GroupId(3));
        map21 = new PersonToGroupMapping(new PersonId(2), new GroupId(1));
        map22 = new PersonToGroupMapping(new PersonId(2), new GroupId(2));
        map33 = new PersonToGroupMapping(new PersonId(3), new GroupId(3));
        mappingList.addPersonToGroupMapping(map11);
        mappingList.addPersonToGroupMapping(map12);
        mappingList.addPersonToGroupMapping(map13);
        mappingList.addPersonToGroupMapping(map21);
        mappingList.addPersonToGroupMapping(map22);
        mappingList.addPersonToGroupMapping(map33);
    }

    @Test
    void addPersonToGroupMapping() {
        PersonToGroupMapping map44 = new PersonToGroupMapping(new PersonId(4), new GroupId(4));
        assertTrue(mappingList.addPersonToGroupMapping(map44));

        assertFalse(mappingList.addPersonToGroupMapping(map11));
    }

    @Test
    void findPersonToGroupMapping() {
        PersonToGroupMapping map = mappingList.findPersonToGroupMapping(map11.getPersonId(), map11.getGroupId());
        assertTrue(map.getPersonId().equals(map11.getPersonId()));
        assertTrue(map.getGroupId().equals(map11.getGroupId()));
        assertFalse(map.getPersonId().equals(map22.getPersonId()));
        assertFalse(map.getGroupId().equals(map22.getGroupId()));
    }

    @Test
    void deletePersonToGroupMapping() {
        assertTrue(mappingList.deletePersonToGroupMapping(map11));
        assertFalse(mappingList.deletePersonToGroupMapping(map11));
    }

    @Test
    void deletePersonFromMapping() {
        mappingList.deletePersonFromMapping(map11.getPersonId());

        assertNotNull(mappingList.findPersonToGroupMapping(map21.getPersonId(), map21.getGroupId()));
        assertNotNull(mappingList.findPersonToGroupMapping(map22.getPersonId(), map22.getGroupId()));
        assertNotNull(mappingList.findPersonToGroupMapping(map33.getPersonId(), map33.getGroupId()));

        assertNull(mappingList.findPersonToGroupMapping(map11.getPersonId(), map11.getGroupId()));
        assertNull(mappingList.findPersonToGroupMapping(map12.getPersonId(), map12.getGroupId()));
        assertNull(mappingList.findPersonToGroupMapping(map13.getPersonId(), map13.getGroupId()));
    }

    @Test
    void deleteGroupFromMapping() {
        mappingList.deleteGroupFromMapping(map11.getGroupId());

        assertNull(mappingList.findPersonToGroupMapping(map21.getPersonId(), map21.getGroupId()));
        assertNotNull(mappingList.findPersonToGroupMapping(map22.getPersonId(), map22.getGroupId()));
        assertNotNull(mappingList.findPersonToGroupMapping(map33.getPersonId(), map33.getGroupId()));

        assertNull(mappingList.findPersonToGroupMapping(map11.getPersonId(), map11.getGroupId()));
        assertNotNull(mappingList.findPersonToGroupMapping(map12.getPersonId(), map12.getGroupId()));
        assertNotNull(mappingList.findPersonToGroupMapping(map13.getPersonId(), map13.getGroupId()));
    }

    @Test
    void findGroupsOfPerson() {
        ArrayList<GroupId> groups = mappingList.findGroupsOfPerson(map11.getPersonId());

        assertEquals(3, groups.size());
    }

    @Test
    void findPersonsOfGroup() {
        ArrayList<PersonId> persons = mappingList.findPersonsOfGroup(map11.getGroupId());

        assertEquals(2, persons.size());
    }
}
