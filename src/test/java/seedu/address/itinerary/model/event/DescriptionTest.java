package seedu.address.itinerary.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DescriptionTest {

    @Test
    void isValidDescription() {
        // invalid description
        assertFalse(Description.isValidDescription(
                "000000000000000000000000000000000000000000000000000")); // More than 50 characters, 51 in total

        // valid description
        assertTrue(Description.isValidDescription("")); // Empty description

        assertTrue(Description.isValidDescription(" ")); // One space character

        assertTrue(Description.isValidDescription("1")); // One character description

        assertTrue(Description.isValidDescription("This is a valid description")); // Valid description

        assertTrue(Description.isValidDescription("Θ θ, Ι ι, Κ κ, Λ λ, Μ μ, Ν ν, Ξ ξ, Ο ο, Π π")); // Greek

        assertTrue(Description.isValidDescription(
                "00000000000000000000000000000000000000000000000000")); // Exactly 50 characters
    }

    @Test
    void testToString() {
        Description description = new Description("Nice description");
        Description description2 = new Description("Θ θ, Ι ι, Κ κ, Λ λ, Μ μ, Ν ν, Ξ ξ, Ο ο, Π π");
        Description description3 = new Description("00000000000000000000000000000000000000000000000000");

        // Testing unexpected result
        assertNotEquals("", description.toString());

        // Description object will only be formed if the description pass the validation test
        assertEquals("Nice description", description.toString());

        assertEquals("Θ θ, Ι ι, Κ κ, Λ λ, Μ μ, Ν ν, Ξ ξ, Ο ο, Π π", description2.toString());

        assertEquals("00000000000000000000000000000000000000000000000000", description3.toString());
    }
}
