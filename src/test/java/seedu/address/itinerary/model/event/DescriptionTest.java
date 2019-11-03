package seedu.address.itinerary.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DescriptionTest {

    @Test
    void isValidDescription() {
        // invalid description
        assertFalse(Description.isValidDescription(
                "0000000000000000000000000000000000000000000000000000")); // More than 50 characters

        // valid description
        assertTrue(Description.isValidDescription("This is a valid description")); // Valid description
        assertFalse(Description.isValidDescription(
                "000000000000000000000000000000000000000000000000000")); // Exactly 50 characters
        assertTrue(Description.isValidDescription("")); // Empty description
        assertTrue(Description.isValidDescription("1")); // One character description
        assertTrue(Description.isValidDescription("Θ θ, Ι ι, Κ κ, Λ λ, Μ μ, Ν ν, Ξ ξ, Ο ο, Π π")); // Greek
    }

    @Test
    void testToString() {
        Description description = new Description("Nice description");
        Description description2 = new Description("Θ θ, Ι ι, Κ κ, Λ λ, Μ μ, Ν ν, Ξ ξ, Ο ο, Π π");
        assertEquals(description.toString(), "Nice description");
        assertEquals(description2.toString(), "Θ θ, Ι ι, Κ κ, Λ λ, Μ μ, Ν ν, Ξ ξ, Ο ο, Π π");
    }
}
