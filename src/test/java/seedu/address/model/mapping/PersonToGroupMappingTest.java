package seedu.address.model.mapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.group.GroupId;
import seedu.address.model.person.PersonId;

class PersonToGroupMappingTest {

    private PersonToGroupMapping mapping;

    @BeforeEach
    void init() {
        mapping = new PersonToGroupMapping(new PersonId(1), new GroupId(2));
    }

    @Test
    void getGroupId() {
        assertEquals("2", mapping.getGroupId().toString());
    }

    @Test
    void getPersonId() {
        assertEquals("1", mapping.getPersonId().toString());
    }

    @Test
    void testEquals() {
        PersonToGroupMapping mapping2 = new PersonToGroupMapping(new PersonId(1), new GroupId(2));
        PersonToGroupMapping mapping3 = new PersonToGroupMapping(new PersonId(1), new GroupId(3));

        assertTrue(mapping.equals(mapping2));
        assertFalse(mapping.equals(mapping3));

    }
}
