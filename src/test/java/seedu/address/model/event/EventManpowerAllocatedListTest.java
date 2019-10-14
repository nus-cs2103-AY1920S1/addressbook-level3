package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.Employee.Employee;
import seedu.address.testutil.PersonBuilder;

class EventManpowerAllocatedListTest {

    @Test
    void allocateEmployee() {
        Employee employeeOne = new PersonBuilder().build();
        EventManpowerAllocatedList allocatedList = new EventManpowerAllocatedList();
        assertTrue(allocatedList.allocateEmployee(employeeOne));
        assertFalse(allocatedList.allocateEmployee(employeeOne)); //Cannot add same employee
    }

    @Test
    void eventManpowerAllocatedListEquals() {
        Employee employeeOne = new PersonBuilder().build();
        EventManpowerAllocatedList allocatedListA = new EventManpowerAllocatedList();
        EventManpowerAllocatedList allocatedListB = new EventManpowerAllocatedList();

        assertEquals(allocatedListA, allocatedListB);
        allocatedListA.allocateEmployee(employeeOne);
        assertNotEquals(allocatedListA, allocatedListB);
    }
}
