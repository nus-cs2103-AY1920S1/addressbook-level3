package seedu.address.model.entity.worker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;


//@@author shaoyi
class DesignationTest {

    @Test
    void enumerateDesignation_indexOne_correct() {
        assertEquals(Designation.MANAGER.toString(), "MANAGER");
    }

    @Test
    void enumerateDesignation_indexOne_wrong() {
        assertNotEquals(Designation.TECHNICIAN.toString(), "MANAGER");
    }
}
