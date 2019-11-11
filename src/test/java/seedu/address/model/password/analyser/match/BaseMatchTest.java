package seedu.address.model.password.analyser.match;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class BaseMatchTest {

    private BaseMatch instance = new BaseMatchImpl(0 , 5, "dummy");

    @Test
    public void getToken() {
        assertEquals(instance.getToken(), "dummy");
    }

    @Test
    public void getStartIndex() {
        assertTrue(instance.getStartIndex() == 0);
    }

    @Test
    public void getEndIndex() {
        assertTrue(instance.getEndIndex() == 5);
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BaseMatchImpl(0, 5, null));
    }

    @Test
    public void constructor_invalidMatchIndex_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new BaseMatchImpl(5, 0, "dummy"));
    }

    @Test
    public void constructor_invalidMatchToken_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new BaseMatchImpl(0, 5, ""));
    }

    public class BaseMatchImpl extends BaseMatch {
        public BaseMatchImpl(int start, int end, String token) {
            super(start, end, token);
        }
    }
}
