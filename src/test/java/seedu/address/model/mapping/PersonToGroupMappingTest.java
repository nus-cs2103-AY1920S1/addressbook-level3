package seedu.address.model.mapping;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.mappingutil.TypicalMappings.MAP00;
import static seedu.address.testutil.mappingutil.TypicalMappings.MAP01;
import static seedu.address.testutil.mappingutil.TypicalMappings.MAP22;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonToGroupMappingTest {

    private PersonToGroupMapping mapping;

    @BeforeEach
    void init() {
        mapping = MAP00;
    }

    @Test
    void getGroupId() {
        assertTrue(MAP00.getGroupId().equals(mapping.getGroupId()));
        assertFalse(MAP22.getGroupId().equals(mapping.getGroupId()));
    }

    @Test
    void getPersonId() {
        assertTrue(MAP00.getPersonId().equals(mapping.getPersonId()));
        assertFalse(MAP22.getPersonId().equals(mapping.getPersonId()));
    }

    @Test
    void testEquals() {
        assertTrue(mapping.equals(MAP00));
        assertTrue(MAP00.equals(MAP00));
        assertFalse(mapping.equals(MAP22));
        assertFalse(MAP00.equals(MAP01));
    }
}
