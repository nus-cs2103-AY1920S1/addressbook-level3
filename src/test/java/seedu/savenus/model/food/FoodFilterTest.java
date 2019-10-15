package seedu.savenus.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_CATEGORY;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_DESCRIPTION;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_LOCATION;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_NAME;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_OPENING_HOURS;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_PRICE;
import static seedu.savenus.logic.parser.CliSyntax.FIELD_NAME_RESTRICTIONS;
import static seedu.savenus.logic.parser.CliSyntax.QUANTIFY_EQUALS_TO;
import static seedu.savenus.logic.parser.CliSyntax.QUANTIFY_LESS_THAN;
import static seedu.savenus.logic.parser.CliSyntax.QUANTIFY_MORE_THAN;
import static seedu.savenus.testutil.TypicalMenu.CARBONARA;
import static seedu.savenus.testutil.TypicalMenu.TEH_PING;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class FoodFilterTest {
    private List<String> fieldList;
    private FoodFilter foodFilter;

    @Test
    public void create_dummyFields_test() {
        fieldList = new ArrayList<>();
        foodFilter = new FoodFilter(fieldList);

        // Create new Price
        assertEquals(foodFilter.createDummyField(FIELD_NAME_PRICE, "4.00"), new Price("4.00"));
        assertNotEquals(foodFilter.createDummyField(FIELD_NAME_PRICE, "4.00"), new Price("3.00"));

        // Create new Category
        assertEquals(foodFilter.createDummyField(FIELD_NAME_CATEGORY, "400"), new Category("400"));
        assertNotEquals(foodFilter.createDummyField(FIELD_NAME_CATEGORY, "400"), new Category("300"));

        // Create new Description
        assertEquals(foodFilter.createDummyField(FIELD_NAME_DESCRIPTION, "400"), new Description("400"));
        assertNotEquals(foodFilter.createDummyField(FIELD_NAME_DESCRIPTION, "400"), new Description("300"));

        // Create new Location
        assertEquals(foodFilter.createDummyField(FIELD_NAME_LOCATION, "400"), new Location("400"));
        assertNotEquals(foodFilter.createDummyField(FIELD_NAME_LOCATION, "400"), new Location("300"));

        // Create new Name
        assertEquals(foodFilter.createDummyField(FIELD_NAME_NAME, "400"), new Name("400"));
        assertNotEquals(foodFilter.createDummyField(FIELD_NAME_NAME, "400"), new Name("300"));

        // Create new OpeningHours
        assertEquals(foodFilter.createDummyField(FIELD_NAME_OPENING_HOURS, "0000 0000"),
            new OpeningHours("0000 0000"));
        assertNotEquals(foodFilter.createDummyField(FIELD_NAME_OPENING_HOURS, "0000 0000"),
            new OpeningHours("0000 0001"));

        // Create new Restrictions
        assertEquals(foodFilter.createDummyField(FIELD_NAME_RESTRICTIONS, "400"), new Restrictions("400"));
        assertNotEquals(foodFilter.createDummyField(FIELD_NAME_RESTRICTIONS, "400"), new Restrictions("300"));

        assertEquals(foodFilter.createDummyField("OOF", "400"), null);

    }

    @Test
    public void quantifier_tests() {
        fieldList = new ArrayList<>();
        foodFilter = new FoodFilter(fieldList);

        // Check for equals
        assertTrue(foodFilter.quantifierMatches(QUANTIFY_EQUALS_TO, 0));
        assertFalse(foodFilter.quantifierMatches(QUANTIFY_EQUALS_TO, -100));
        assertFalse(foodFilter.quantifierMatches(QUANTIFY_EQUALS_TO, 100));

        assertTrue(foodFilter.quantifierMatches(QUANTIFY_LESS_THAN, -100));
        assertFalse(foodFilter.quantifierMatches(QUANTIFY_LESS_THAN, 0));
        assertFalse(foodFilter.quantifierMatches(QUANTIFY_LESS_THAN, 100));

        assertTrue(foodFilter.quantifierMatches(QUANTIFY_MORE_THAN, 100));
        assertFalse(foodFilter.quantifierMatches(QUANTIFY_MORE_THAN, -100));
        assertFalse(foodFilter.quantifierMatches(QUANTIFY_MORE_THAN, 0));
    }

    @Test
    public void test_main_test() {
        fieldList = new ArrayList<>();
        fieldList.add(FIELD_NAME_PRICE);
        fieldList.add(QUANTIFY_MORE_THAN);
        fieldList.add("4.00");
        foodFilter = new FoodFilter(fieldList);

        // True
        assertTrue(foodFilter.doesFoodPassTest(CARBONARA));
        assertFalse(foodFilter.doesFoodPassTest(TEH_PING));
    }

    @Test
    public void predicate_tests() {
        fieldList = new ArrayList<>();
        fieldList.add(FIELD_NAME_PRICE);
        fieldList.add(QUANTIFY_MORE_THAN);
        fieldList.add("4.00");
        foodFilter = new FoodFilter(fieldList);

        assertTrue(foodFilter.test(CARBONARA));
        assertFalse(foodFilter.test(TEH_PING));
    }
}
