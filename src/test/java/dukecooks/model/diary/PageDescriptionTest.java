package dukecooks.model.diary;

import static dukecooks.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.model.diary.components.PageDescription;

public class PageDescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PageDescription(null));
    }

    @Test
    public void constructor_invalidPageDescription_throwsIllegalArgumentException() {
        String invalidPageDescription = "";
        assertThrows(IllegalArgumentException.class, () -> new PageDescription(invalidPageDescription));
    }

    @Test
    public void isValidPageDescription() {

        // null description
        assertThrows(NullPointerException.class, () -> PageDescription.isValidPageDescription(null));

        // invalid description
        assertFalse(PageDescription.isValidPageDescription("")); // empty string
        assertFalse(PageDescription.isValidPageDescription(" ")); // spaces only

        // valid description
        assertTrue(PageDescription.isValidPageDescription("This is a valid description!!"));
        assertTrue(PageDescription.isValidPageDescription("With symbols @!#!@#~~")); // with symbols
        assertTrue(PageDescription.isValidPageDescription("23131231")); // with numbers
        assertTrue(PageDescription.isValidPageDescription("hello 123 !!!@#")); // a mixture

    }

    @Test
    void testPageDescriptionEquals() {
        PageDescription description1 = new PageDescription("Same description");
        PageDescription description2 = new PageDescription("Same description");
        PageDescription description3 = new PageDescription("Different description");

        // same object
        assertTrue(description1.equals(description1));

        // different object, same fields
        assertTrue(description2.equals(description2));

        // different objects and fields
        assertFalse(description1.equals(null));
        assertFalse(description1.equals(description3));
    }

    @Test
    public void testPageDescriptionToString() {
        PageDescription description = new PageDescription("A valid description!");
        String expected = "A valid description!";
        assertEquals(description.toString(), expected);
    }

    @Test
    public void testPageDescriptionHashCode() {
        PageDescription firstDesc = new PageDescription("description");
        PageDescription secondDesc = new PageDescription("description");

        assertEquals(firstDesc.hashCode(), secondDesc.hashCode());
    }
}
