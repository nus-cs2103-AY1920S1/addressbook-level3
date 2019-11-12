package seedu.address.model.note;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Date;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Address;

public class DateModifiedTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
    public void update() {
        Date oldDate = new Date(1570752000);
        DateModified oldDateModified = new DateModified(oldDate);
        DateModified newDateModified = new DateModified(new Date());
        assertTrue(oldDateModified.update().equals(newDateModified));
    }
}
