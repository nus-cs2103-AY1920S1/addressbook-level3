package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static seedu.address.testutil.TypicalPersons.AMY;

import org.junit.jupiter.api.Test;

class SessionTest {

    @Test
    void getLoggedInPerson() {
        Session session = new Session(AMY);
        assertEquals(session.getLoggedInPerson(), AMY);

        session = new Session(null);
        assertNull(session.getLoggedInPerson());
    }
}
