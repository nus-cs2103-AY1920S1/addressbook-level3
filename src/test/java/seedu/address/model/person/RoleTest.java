package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class RoleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Role(null));
    }

    @Test
    public void constructor_invalidRole_throwsIllegalArgumentException() {
        String invalidRole = "";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidRole));
    }

    @Test
    public void isValidRole() {
        // null role
        assertThrows(NullPointerException.class, () -> Role.isValidRole(null));

        // invalid role
        assertFalse(Role.isValidRole(""));
        assertFalse(Role.isValidRole("Interviewer")); // no capitalised roles
        assertFalse(Role.isValidRole("Interviewee"));
        assertFalse(Role.isValidRole("teacher"));
        assertFalse(Role.isValidRole("interviewwee"));

        // valid role
        assertTrue(Role.isValidRole("interviewee"));
        assertTrue(Role.isValidRole("interviewer"));
    }

    @Test public void getRole() {
        // interviewee role
        assertEquals(RoleType.INTERVIEWEE, new Role("interviewee").getRole());

        // interviewer role
        assertEquals(RoleType.INTERVIEWER, new Role("interviewer").getRole());

        // invalid role
        assertThrows(IllegalArgumentException.class, () -> new Role("Interviewer").getRole());
    }
}
