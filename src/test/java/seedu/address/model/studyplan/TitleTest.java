package seedu.address.model.studyplan;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalStudyPlans.SP_1;
import static seedu.address.testutil.TypicalStudyPlans.SP_2;

import org.junit.jupiter.api.Test;

/**
 * A test class for {@code Title}.
 */
public class TitleTest {
    @Test
    public void equals() {

        // same title -> true
        assertTrue(SP_1.getTitle().equals(SP_1.getTitle()));
        assertTrue(SP_1.getTitle().equals(new Title("first study plan")));

        // different titles -> false
        assertFalse(SP_1.getTitle().equals(SP_2.getTitle()));
    }
}
