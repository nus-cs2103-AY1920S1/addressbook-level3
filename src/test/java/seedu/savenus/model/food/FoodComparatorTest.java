package seedu.savenus.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.logic.commands.CommandTestUtil.ASC_DIRECTION;
import static seedu.savenus.logic.commands.CommandTestUtil.DESC_DIRECTION;
import static seedu.savenus.logic.commands.CommandTestUtil.NAME_FIELD;
import static seedu.savenus.testutil.TypicalMenu.CARBONARA;
import static seedu.savenus.testutil.TypicalMenu.NASI_AYAM;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class FoodComparatorTest {
    private List<String> fields = new ArrayList<String>();

    @Test
    public void check_compare() {
        fields.add(NAME_FIELD);
        fields.add(ASC_DIRECTION);
        assertThrows(NullPointerException.class, () -> new FoodComparator(fields).compare(CARBONARA, null));
        assertEquals(new FoodComparator(fields).compare(CARBONARA, CARBONARA), 0);
        assertNotEquals(new FoodComparator(fields).compare(CARBONARA, NASI_AYAM), 0);
    }

    @Test
    public void check_direction() {
        FoodComparator test = new FoodComparator(fields);
        assertTrue(test.isDirectionAscending(ASC_DIRECTION));
        assertFalse(test.isDirectionAscending(DESC_DIRECTION));
    }
}
