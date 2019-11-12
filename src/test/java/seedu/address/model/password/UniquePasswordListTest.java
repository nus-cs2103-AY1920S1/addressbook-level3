package seedu.address.model.password;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPasswords.PASSWORD1;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PasswordBuilder;


public class UniquePasswordListTest {
    private final UniquePasswordList uniquePasswordList = new UniquePasswordList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePasswordList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniquePasswordList.contains(PASSWORD1));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniquePasswordList.add(PASSWORD1);
        assertTrue(uniquePasswordList.contains(PASSWORD1));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniquePasswordList.add(PASSWORD1);
        Password editedPassword = new PasswordBuilder(PASSWORD1)
                .withWebsite("www.gmail.com")
                .build();
        assertTrue(uniquePasswordList.contains(editedPassword));
    }

}
