package seedu.address.model.password.analyser.result;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.password.Password;

class ResultTest {

    private Password p = new Password()
    private Result instance = new ResultImpl()

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ResultImpl(null, ResultOutcome.PASS));
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
