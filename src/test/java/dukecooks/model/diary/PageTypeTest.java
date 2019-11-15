package dukecooks.model.diary;

import static dukecooks.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.model.diary.components.PageType;

public class PageTypeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PageType(null));
    }

    @Test
    public void constructor_invalidPageType_throwsIllegalArgumentException() {
        String invalidPageType = "";
        assertThrows(IllegalArgumentException.class, () -> new PageType(invalidPageType));
    }

    @Test
    public void isValidType() {

        // null type
        assertThrows(NullPointerException.class, () -> PageType.isValidPageType(null));

        // invalid description
        assertFalse(PageType.isValidPageType("")); // empty string
        assertFalse(PageType.isValidPageType(" ")); // spaces only
        assertFalse(PageType.isValidPageType("whatever string"));

        // valid description
        assertTrue(PageType.isValidPageType("health")); // health type
        assertTrue(PageType.isValidPageType("exercise")); // exercise type
        assertTrue(PageType.isValidPageType("food")); // food type

    }

    @Test
    void testPageTypeEquals() {
        PageType type1 = new PageType("health");
        PageType type2 = new PageType("health");
        PageType type3 = new PageType("exercise");

        // same object
        assertTrue(type1.equals(type1));

        // different object, same fields
        assertTrue(type1.equals(type2));

        // different objects and fields
        assertFalse(type1.equals(null));
        assertFalse(type1.equals(type3));
    }

    @Test
    public void testPageTypeToString() {
        PageType pageType = new PageType("health");
        String expected = "health";
        assertEquals(pageType.toString(), expected);
    }

    @Test
    public void testPageTypeHashCode() {
        PageType firstType = new PageType("health");
        PageType secondType = new PageType("health");

        assertEquals(firstType.hashCode(), secondType.hashCode());
    }
}
