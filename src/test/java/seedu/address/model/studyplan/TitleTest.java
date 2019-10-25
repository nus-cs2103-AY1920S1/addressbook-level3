package seedu.address.model.studyplan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

public class TitleTest {
    @Test
    public void equals_valid_success() {
        assertEquals(new Title("Study Plan 1"), new Title("Study Plan 1"));
        assertNotSame(new Title("Study Plan 1"), new Title("Study Plan 2"));
    }

    @Test
    public void toString_valid_success() {
        assertEquals(new Title("Study Plan 1").toString(), "Study Plan 1");
        assertNotSame(new Title("Study Plan 1").toString(), "Study Plan 2");
    }
}
