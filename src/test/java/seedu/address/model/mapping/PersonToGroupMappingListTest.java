package seedu.address.model.mapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.mappingutil.TypicalMappings.MAP00;
import static seedu.address.testutil.mappingutil.TypicalMappings.MAP01;
import static seedu.address.testutil.mappingutil.TypicalMappings.MAP02;
import static seedu.address.testutil.mappingutil.TypicalMappings.MAP10;
import static seedu.address.testutil.mappingutil.TypicalMappings.MAP22;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.group.GroupId;
import seedu.address.model.person.PersonId;
import seedu.address.testutil.mappingutil.TypicalMappings;

class PersonToGroupMappingListTest {

    private PersonToGroupMappingList mappingList;

    @BeforeEach
    void init() {
        mappingList = new PersonToGroupMappingList();
    }

    @Test
    void addPersonToGroupMapping() {
        // add new mappings -> true
        assertTrue(mappingList.addPersonToGroupMapping(MAP00));
        assertTrue(mappingList.addPersonToGroupMapping(MAP01));

        //add mappings that are already in maplist -> false
        assertFalse(mappingList.addPersonToGroupMapping(MAP00));
        assertFalse(mappingList.addPersonToGroupMapping(MAP01));
    }

    @Test
    void findPersonToGroupMapping() {
        mappingList.addPersonToGroupMapping(MAP00);
        PersonToGroupMapping map = mappingList.findPersonToGroupMapping(MAP00.getPersonId(), MAP00.getGroupId());

        assertTrue(map.equals(MAP00));
        assertTrue(map.getPersonId().equals(MAP00.getPersonId()));
        assertTrue(map.getGroupId().equals(MAP00.getGroupId()));

        assertFalse(map.equals(MAP22));
        assertFalse(map.getPersonId().equals(MAP22.getPersonId()));
        assertFalse(map.getGroupId().equals(MAP22.getGroupId()));
    }

    @Test
    void deletePersonToGroupMapping() {
        mappingList.addPersonToGroupMapping(MAP00);

        // delete a map in the list -> true
        assertTrue(mappingList.deletePersonToGroupMapping(MAP00));

        // delete a map not in the list -> false
        assertFalse(mappingList.deletePersonToGroupMapping(MAP00));
        assertFalse(mappingList.deletePersonToGroupMapping(MAP22));
    }

    @Test
    void deletePersonFromMapping() {
        mappingList = TypicalMappings.generateTypicalMappingList();
        mappingList.deletePersonFromMapping(MAP00.getPersonId());

        // these maps are deleted
        assertNull(mappingList.findPersonToGroupMapping(MAP00.getPersonId(), MAP00.getGroupId()));
        assertNull(mappingList.findPersonToGroupMapping(MAP01.getPersonId(), MAP01.getGroupId()));
        assertNull(mappingList.findPersonToGroupMapping(MAP02.getPersonId(), MAP02.getGroupId()));

        // these maps are not deleted
        assertNotNull(mappingList.findPersonToGroupMapping(MAP10.getPersonId(), MAP10.getGroupId()));
        assertNotNull(mappingList.findPersonToGroupMapping(MAP22.getPersonId(), MAP22.getGroupId()));
        assertNotNull(mappingList.findPersonToGroupMapping(MAP22.getPersonId(), MAP22.getGroupId()));


    }

    @Test
    void deleteGroupFromMapping() {
        mappingList = TypicalMappings.generateTypicalMappingList();
        mappingList.deleteGroupFromMapping(MAP00.getGroupId());

        // these maps are deleted
        assertNull(mappingList.findPersonToGroupMapping(MAP10.getPersonId(), MAP10.getGroupId()));
        assertNull(mappingList.findPersonToGroupMapping(MAP00.getPersonId(), MAP00.getGroupId()));

        // these maps are not deleted
        assertNotNull(mappingList.findPersonToGroupMapping(MAP22.getPersonId(), MAP22.getGroupId()));
        assertNotNull(mappingList.findPersonToGroupMapping(MAP22.getPersonId(), MAP22.getGroupId()));
        assertNotNull(mappingList.findPersonToGroupMapping(MAP01.getPersonId(), MAP01.getGroupId()));
        assertNotNull(mappingList.findPersonToGroupMapping(MAP02.getPersonId(), MAP02.getGroupId()));
    }

    @Test
    void findGroupsOfPerson() {
        mappingList = TypicalMappings.generateTypicalMappingList();

        ArrayList<GroupId> groups = mappingList.findGroupsOfPerson(MAP00.getPersonId());

        assertEquals(3, groups.size());
    }

    @Test
    void findPersonsOfGroup() {
        mappingList = TypicalMappings.generateTypicalMappingList();

        ArrayList<PersonId> persons = mappingList.findPersonsOfGroup(MAP00.getGroupId());

        assertEquals(2, persons.size());
    }
}
