package sugarmummy.recmfood.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains tests for the crucial {@link FoodType#getFrom(String)} method.
 */
class FoodTypeTest {

    @Test
    void getFrom_allValidTypes_success() throws ParseException {
        assertEquals(FoodType.NON_STARCHY_VEGETABLE, FoodType.getFrom("nsv"));
        assertEquals(FoodType.STARCHY_VEGETABLE, FoodType.getFrom("sv"));
        assertEquals(FoodType.FRUIT, FoodType.getFrom("f"));
        assertEquals(FoodType.PROTEIN, FoodType.getFrom("p"));
        assertEquals(FoodType.SNACK, FoodType.getFrom("s"));
        assertEquals(FoodType.MEAL, FoodType.getFrom("m"));

        assertEquals(FoodType.NON_STARCHY_VEGETABLE, FoodType.getFrom("nSv"));
    }

    @Test
    void getFrom_mismatchTypes_fail() throws ParseException {
        assertNotEquals(FoodType.NON_STARCHY_VEGETABLE, FoodType.getFrom("sv"));
        assertNotEquals(FoodType.STARCHY_VEGETABLE, FoodType.getFrom("p"));

        assertNotEquals(FoodType.NON_STARCHY_VEGETABLE, FoodType.getFrom("Sv"));
    }

    @Test
    void getFrom_invalidTypes_throwParseException() {
        assertThrows(ParseException.class, () -> FoodType.getFrom(null));
        assertThrows(ParseException.class, () -> FoodType.getFrom(""));
        assertThrows(ParseException.class, () -> FoodType.getFrom("  "));
        assertThrows(ParseException.class, () -> FoodType.getFrom("p  a"));
        assertThrows(ParseException.class, () -> FoodType.getFrom("#s"));
    }
}
