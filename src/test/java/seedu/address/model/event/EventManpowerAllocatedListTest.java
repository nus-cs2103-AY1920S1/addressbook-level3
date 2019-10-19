package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EventManpowerAllocatedListTest {

    @Test
    void allocateEmployee() {
        String employeeIdOne = "000";
        EventManpowerAllocatedList allocatedList = new EventManpowerAllocatedList();
        assertTrue(allocatedList.allocateEmployee("000"));
        assertFalse(allocatedList.allocateEmployee(employeeIdOne)); //Cannot add same employee
    }

    @Test
    void eventManpowerAllocatedListEquals() {
        String employeeIdOne = "000";
        EventManpowerAllocatedList allocatedListA = new EventManpowerAllocatedList();
        EventManpowerAllocatedList allocatedListB = new EventManpowerAllocatedList();

        assertEquals(allocatedListA, allocatedListB);
        allocatedListA.allocateEmployee(employeeIdOne);
        assertNotEquals(allocatedListA, allocatedListB);
    }
}
