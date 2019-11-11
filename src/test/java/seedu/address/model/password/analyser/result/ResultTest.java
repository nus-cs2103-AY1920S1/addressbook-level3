package seedu.address.model.password.analyser.result;

import static seedu.address.model.util.SampleDataUtil.getTagSet;
import static seedu.address.testutil.Assert.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Date;

import org.junit.jupiter.api.Test;

import seedu.address.model.password.Password;
import seedu.address.model.password.PasswordDescription;
import seedu.address.model.password.PasswordModifiedAt;
import seedu.address.model.password.PasswordValue;
import seedu.address.model.password.Username;
import seedu.address.model.password.Website;

class ResultTest {

    private Password password = new Password(new PasswordDescription("Gmail"), new Username("Randomguy"),
            new PasswordValue("qwerty"), new PasswordModifiedAt(new Date()),
            new Website("NIL"), getTagSet("SocialMedia"));

    private Result instance = new ResultImpl(password, ResultOutcome.PASS);

    @Test
    public void getDescription() {
        assertEquals(instance.getDescription().toString(), "PASS");
    }

    @Test
    public void setDescription() {
        instance.setDescription(ResultOutcome.WEAK);
        assertEquals(instance.getDescription().toString(), "WEAK");
    }

    @Test
    public void getPasswordDesc() {
        assertEquals(instance.getPasswordDesc(), "Gmail");
    }

    @Test
    public void getPasswordUser() {
        assertEquals(instance.getPasswordUser(), "Randomguy");
    }

    @Test
    public void getPasswordValue() {
        assertEquals(instance.getPasswordValue(), "******");
    }

    @Test
    public void constructor_nullPassword_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ResultImpl(null, ResultOutcome.PASS));
    }

    @Test
    public void constructor_nullDescription_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ResultImpl(password, null));
    }

    public class ResultImpl extends Result {
        public ResultImpl(Password p, ResultOutcome description) {
            super(p, description);
        }

        @Override
        public String getGreaterDetail() {
            return null;
        }
    }

}
