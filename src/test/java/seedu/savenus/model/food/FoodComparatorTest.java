package seedu.savenus.model.food;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.logic.commands.CommandTestUtil.ASC_DIRECTION;
import static seedu.savenus.logic.commands.CommandTestUtil.DESC_DIRECTION;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class FoodComparatorTest {
    private List<String> fields = new ArrayList<String>();

    @Test
    public void check_direction() {
        FoodComparator test = new FoodComparator(fields);
        assertTrue(test.isDirectionAscending(ASC_DIRECTION));
        assertFalse(test.isDirectionAscending(DESC_DIRECTION));
    }
}
