package seedu.address.model.studyplan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    public void isValidTitle_valid_returnsTrue() {
        // at most 20 ASCII characters
        assertTrue(Title.isValidTitle("valid title"));
    }

    @Test
    public void isValidTitle_exceeds20_returnsFalse() {
        // more than 20 ASCII characters
        assertFalse(Title.isValidTitle("aaaaa aaaaa aaaaa aaa"));
    }

    @Test
    public void isValidTitle_nonAscii_returnsFalse() {
        // non ASCII characters
        assertFalse(Title.isValidTitle("学习计划"));
    }

}
