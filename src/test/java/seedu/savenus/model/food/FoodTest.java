package seedu.savenus.model.food;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.logic.commands.CommandTestUtil.CATEGORY_FIELD;
import static seedu.savenus.logic.commands.CommandTestUtil.LOCATION_FIELD;
import static seedu.savenus.logic.commands.CommandTestUtil.NAME_FIELD;
import static seedu.savenus.logic.commands.CommandTestUtil.OPENING_HOURS_FIELD;
import static seedu.savenus.logic.commands.CommandTestUtil.PRICE_FIELD;
import static seedu.savenus.logic.commands.CommandTestUtil.RESTRICTIONS_FIELD;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_DESCRIPTION_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_LOCATION_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_NAME_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_PRICE_NASI_LEMAK;
import static seedu.savenus.logic.commands.CommandTestUtil.VALID_TAG_CHICKEN;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalMenu.CARBONARA;
import static seedu.savenus.testutil.TypicalMenu.NASI_LEMAK;

import org.junit.jupiter.api.Test;

import seedu.savenus.testutil.FoodBuilder;

public class FoodTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Food food = new FoodBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> food.getTags().remove(0));
    }

    @Test
    public void isSameFood() {
        // same object -> returns true
        assertTrue(CARBONARA.isSameFood(CARBONARA));

        // null -> returns false
        assertFalse(CARBONARA.isSameFood(null));

        // different price and description -> returns false
        Food editedAlice = new FoodBuilder(CARBONARA).withPrice(VALID_PRICE_NASI_LEMAK)
                .withDescription(VALID_DESCRIPTION_NASI_LEMAK).build();
        assertFalse(CARBONARA.isSameFood(editedAlice));

        // different name -> returns false
        editedAlice = new FoodBuilder(CARBONARA).withName(VALID_NAME_NASI_LEMAK).build();
        assertFalse(CARBONARA.isSameFood(editedAlice));

        // same name, same price, different description -> returns false
        editedAlice = new FoodBuilder(CARBONARA).withDescription(VALID_DESCRIPTION_NASI_LEMAK)
                .build();
        assertFalse(CARBONARA.isSameFood(editedAlice));

        // same name, same description, different price -> returns false
        editedAlice = new FoodBuilder(CARBONARA).withPrice(VALID_PRICE_NASI_LEMAK)
                .build();
        assertFalse(CARBONARA.isSameFood(editedAlice));

        // same name, same price, same description, different tags -> returns true
        editedAlice = new FoodBuilder(CARBONARA).withTags(VALID_TAG_CHICKEN).build();
        assertTrue(CARBONARA.isSameFood(editedAlice));
    }

    @Test
    public void get_field_tests() {
        Food carbonaraCopy = new FoodBuilder(CARBONARA).build();
        assertEquals(carbonaraCopy.getField(CATEGORY_FIELD), carbonaraCopy.getCategory());
        assertEquals(carbonaraCopy.getField(NAME_FIELD), carbonaraCopy.getName());
        assertEquals(carbonaraCopy.getField(PRICE_FIELD), carbonaraCopy.getPrice());
        assertEquals(carbonaraCopy.getField(LOCATION_FIELD), carbonaraCopy.getLocation());
        assertEquals(carbonaraCopy.getField(OPENING_HOURS_FIELD), carbonaraCopy.getOpeningHours());
        assertEquals(carbonaraCopy.getField(RESTRICTIONS_FIELD), carbonaraCopy.getRestrictions());
    }

    @Test
    public void equals() {
        // same values -> returns true
        Food aliceCopy = new FoodBuilder(CARBONARA).build();
        assertTrue(CARBONARA.equals(aliceCopy));

        // same object -> returns true
        assertTrue(CARBONARA.equals(CARBONARA));

        // null -> returns false
        assertFalse(CARBONARA.equals(null));

        // different type -> returns false
        assertFalse(CARBONARA.equals(5));

        // different food -> returns false
        assertFalse(CARBONARA.equals(NASI_LEMAK));

        // different name -> returns false
        Food editedAlice = new FoodBuilder(CARBONARA).withName(VALID_NAME_NASI_LEMAK).build();
        assertFalse(CARBONARA.equals(editedAlice));

        // different price -> returns false
        editedAlice = new FoodBuilder(CARBONARA).withPrice(VALID_PRICE_NASI_LEMAK).build();
        assertFalse(CARBONARA.equals(editedAlice));

        // different description -> returns false
        editedAlice = new FoodBuilder(CARBONARA).withDescription(VALID_DESCRIPTION_NASI_LEMAK).build();
        assertFalse(CARBONARA.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new FoodBuilder(CARBONARA).withTags(VALID_TAG_CHICKEN).build();
        assertFalse(CARBONARA.equals(editedAlice));

        // different location -> returns false
        editedAlice = new FoodBuilder(CARBONARA).withLocation(VALID_LOCATION_NASI_LEMAK).build();
        assertFalse(CARBONARA.equals(editedAlice));
    }
}
