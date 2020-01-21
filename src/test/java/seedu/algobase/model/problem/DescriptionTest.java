package seedu.algobase.model.problem;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.algobase.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String emptyDescription = " ";
        assertThrows(IllegalArgumentException.class, () -> new Description(emptyDescription));
    }

    @Test
    public void isValidDescription() {
        // null description
        assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // invalid description
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // only spaces

        // valid description
        assertTrue(Description.isValidDescription("VertexCover")); // only alphabets
        assertTrue(Description.isValidDescription("Dominating Set")); // alphabets with spaces
        assertTrue(Description.isValidDescription("123456789")); // only numbers
        assertTrue(Description.isValidDescription("2nd MST")); // combination of alphanumeric characters and spaces
        assertTrue(Description.isValidDescription("*Subset Sum*")); // combination of any characters
        // long description
        assertTrue(Description.isValidDescription("In the TV game show Gladiators, the final competition is to "
            + "run through a steeplechase course. To get some variation, the producer has decided to change the course "
            + "each week. The course is always built out of m obstacles, all of different heights, placed along "
            + "a straight line. An obstacle consists of two initially connected platforms which may be separated. "
            + "Between the two platforms of an obstacle, other higher obstacles may be put. Also, obstacles may be put "
            + "after one another. No platform should cover another platform, as seen from above."));
    }

}
