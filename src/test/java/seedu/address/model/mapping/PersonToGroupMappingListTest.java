package seedu.address.model.mapping;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import seedu.address.model.mapping.exceptions.AlreadyInGroupException;
import seedu.address.model.mapping.exceptions.DuplicateMappingException;
import seedu.address.model.mapping.exceptions.MappingNotFoundException;
import seedu.address.model.person.PersonId;
import seedu.address.testutil.mappingutil.TypicalMappings;

class PersonToGroupMappingListTest {

    private PersonToGroupMappingList mappingList;

    @BeforeEach
    void init() {
        mappingList = new PersonToGroupMappingList();
    }

    @Test
    void addPersonToGroupMapping() throws DuplicateMappingException {
        // add new mappings -> true
        assertDoesNotThrow(() -> mappingList.addPersonToGroupMapping(MAP00));
        assertDoesNotThrow(() -> mappingList.addPersonToGroupMapping(MAP01));

        //add mappings that are already in maplist -> false
        assertThrows(DuplicateMappingException.class, () -> mappingList.addPersonToGroupMapping(MAP00));
        assertThrows(DuplicateMappingException.class, () -> mappingList.addPersonToGroupMapping(MAP01));
    }

    @Test
    void findPersonToGroupMapping() throws DuplicateMappingException, MappingNotFoundException {
        try {
            mappingList.addPersonToGroupMapping(MAP00);
        } catch (AlreadyInGroupException e) {
            e.printStackTrace();
        }
        PersonToGroupMapping map = mappingList.findPersonToGroupMapping(MAP00.getPersonId(), MAP00.getGroupId());

        assertTrue(map.equals(MAP00));
        assertTrue(map.getPersonId().equals(MAP00.getPersonId()));
        assertTrue(map.getGroupId().equals(MAP00.getGroupId()));

        assertFalse(map.equals(MAP22));
        assertFalse(map.getPersonId().equals(MAP22.getPersonId()));
        assertFalse(map.getGroupId().equals(MAP22.getGroupId()));
    }

    @Test
    void deletePersonToGroupMapping() throws DuplicateMappingException, MappingNotFoundException {
        try {
            mappingList.addPersonToGroupMapping(MAP00);
        } catch (AlreadyInGroupException e) {
            e.printStackTrace();
        }

        // delete a map in the list -> true
        assertDoesNotThrow(() -> mappingList.deletePersonToGroupMapping(MAP00));

        // delete a map not in the list -> false
        assertThrows(MappingNotFoundException.class, () -> mappingList.deletePersonToGroupMapping(MAP00));
        assertThrows(MappingNotFoundException.class, () -> mappingList.deletePersonToGroupMapping(MAP22));
    }

    @Test
    void deletePersonFromMapping() throws MappingNotFoundException, DuplicateMappingException {
        mappingList = TypicalMappings.generateTypicalMappingList();
        mappingList.deletePersonFromMapping(MAP00.getPersonId());

        // these maps are deleted
        assertThrows(MappingNotFoundException.class, () ->
                mappingList.findPersonToGroupMapping(MAP00.getPersonId(), MAP00.getGroupId()));
        assertThrows(MappingNotFoundException.class, () ->
                mappingList.findPersonToGroupMapping(MAP01.getPersonId(), MAP01.getGroupId()));
        assertThrows(MappingNotFoundException.class, () ->
                mappingList.findPersonToGroupMapping(MAP02.getPersonId(), MAP02.getGroupId()));

        // these maps are not deleted
        assertDoesNotThrow(() -> mappingList.findPersonToGroupMapping(MAP10.getPersonId(), MAP10.getGroupId()));
        assertDoesNotThrow(() -> mappingList.findPersonToGroupMapping(MAP22.getPersonId(), MAP22.getGroupId()));
        assertDoesNotThrow(() -> mappingList.findPersonToGroupMapping(MAP22.getPersonId(), MAP22.getGroupId()));


    }

    @Test
    void deleteGroupFromMapping() throws MappingNotFoundException, DuplicateMappingException {
        mappingList = TypicalMappings.generateTypicalMappingList();
        mappingList.deleteGroupFromMapping(MAP00.getGroupId());

        // these maps are deleted
        assertThrows(MappingNotFoundException.class, () ->
                mappingList.findPersonToGroupMapping(MAP10.getPersonId(), MAP10.getGroupId()));
        assertThrows(MappingNotFoundException.class, () ->
                mappingList.findPersonToGroupMapping(MAP00.getPersonId(), MAP00.getGroupId()));

        // these maps are not deleted
        assertDoesNotThrow(() -> mappingList.findPersonToGroupMapping(MAP22.getPersonId(), MAP22.getGroupId()));
        assertDoesNotThrow(() -> mappingList.findPersonToGroupMapping(MAP22.getPersonId(), MAP22.getGroupId()));
        assertDoesNotThrow(() -> mappingList.findPersonToGroupMapping(MAP01.getPersonId(), MAP01.getGroupId()));
        assertDoesNotThrow(() -> mappingList.findPersonToGroupMapping(MAP02.getPersonId(), MAP02.getGroupId()));
    }

    @Test
    void findGroupsOfPerson() throws DuplicateMappingException {
        mappingList = TypicalMappings.generateTypicalMappingList();

        ArrayList<GroupId> groups = mappingList.findGroupsOfPerson(MAP00.getPersonId());

        assertEquals(3, groups.size());
    }

    @Test
    void findPersonsOfGroup() throws DuplicateMappingException {
        mappingList = TypicalMappings.generateTypicalMappingList();

        ArrayList<PersonId> persons = mappingList.findPersonsOfGroup(MAP00.getGroupId());

        assertEquals(2, persons.size());
    }
}
