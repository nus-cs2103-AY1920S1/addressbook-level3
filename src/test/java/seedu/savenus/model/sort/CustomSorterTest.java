package seedu.savenus.model.sort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

//@@author seanlowjk
public class CustomSorterTest {
    @Test
    public void equals() {
        CustomSorter sorter = new CustomSorter();
        assertEquals(sorter, sorter);
        assertNotEquals(sorter, new Object());
    }
}
