package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;


class EventManpowerAllocatedListTest {


    @Test
    void eventManpowerAllocatedListEquals() {
        EventManpowerAllocatedList allocatedListA = new EventManpowerAllocatedList();
        EventManpowerAllocatedList allocatedListB = new EventManpowerAllocatedList("001");
        EventManpowerAllocatedList allocatedListC = new EventManpowerAllocatedList("    ");

        assertNotEquals(allocatedListA, allocatedListB);
        assertEquals(allocatedListA, allocatedListC);
    }
}
