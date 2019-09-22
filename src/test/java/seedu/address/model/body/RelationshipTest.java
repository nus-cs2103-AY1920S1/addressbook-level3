package seedu.address.model.body;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

//@@author ambervoong
class RelationshipTest {
    @Test
    void enumerateRelationship_indexOne_correct() {
        assertEquals(Relationship.WIFE.toString(), "WIFE");
    }

    @Test
    void enumerateRelationship_indexZero_wrong() {
        assertNotEquals(Relationship.HUSBAND.toString(), "WIFE");
    }
}
