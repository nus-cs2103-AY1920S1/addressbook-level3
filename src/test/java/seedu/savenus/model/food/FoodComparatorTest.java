package seedu.savenus.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.logic.parser.CliSyntax.ASCENDING_DIRECTION;
import static seedu.savenus.logic.parser.CliSyntax.DESCENDING_DIRECTION;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_NAME;
import static seedu.savenus.testutil.TypicalMenu.CARBONARA;
import static seedu.savenus.testutil.TypicalMenu.NASI_AYAM;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class FoodComparatorTest {
    private List<String> fields = new ArrayList<String>();

    @Test
    public void check_compare() {
        fields.add(FIELD_NAME_NAME);
        fields.add(ASCENDING_DIRECTION);
        assertThrows(NullPointerException.class, () -> new FoodComparator(fields).compare(CARBONARA, null));
        assertEquals(new FoodComparator(fields).compare(CARBONARA, CARBONARA), 0);
        assertNotEquals(new FoodComparator(fields).compare(CARBONARA, NASI_AYAM), 0);
    }

    @Test
    public void check_direction() {
        FoodComparator test = new FoodComparator(fields);
        assertTrue(test.isDirectionAscending(ASCENDING_DIRECTION));
        assertFalse(test.isDirectionAscending(DESCENDING_DIRECTION));
    }
}
