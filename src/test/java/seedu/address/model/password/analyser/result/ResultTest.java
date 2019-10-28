package seedu.address.model.password.analyser.result;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.util.SampleDataUtil.getTagSet;
import static seedu.address.testutil.Assert.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import seedu.address.model.password.Description;
import seedu.address.model.password.Password;
import seedu.address.model.password.PasswordValue;
import seedu.address.model.password.Username;

class ResultTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ResultImpl(null, "dummy"));
    }


    public class ResultImpl extends Result {
        public ResultImpl(Password p, String description) {
            super(p, description);
        }

        @Override
        public String getGreaterDetail() {
            return null;
        }
    }

}