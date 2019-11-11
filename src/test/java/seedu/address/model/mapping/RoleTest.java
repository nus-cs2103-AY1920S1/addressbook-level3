package seedu.address.model.mapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoleTest {

    private Role role;

    @BeforeEach
    void init() {
        role = new Role("role");
    }


    @Test
    void emptyRole() {
        assertTrue(Role.emptyRole().equals(new Role("")));
    }

    @Test
    void getRole() {
        assertEquals("role", role.getRole());
    }

    @Test
    void setRole() {
        String roleString = "Role2";
        role.setRole(roleString);

        assertTrue(role.equals(new Role(roleString)));
    }

    @Test
    void isValid() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testToString() {
    }
}
