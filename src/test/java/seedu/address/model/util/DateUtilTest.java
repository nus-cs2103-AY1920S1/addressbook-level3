package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.util.DateUtil.findDaysPasswordExpireAt;
import static seedu.address.model.util.DateUtil.findPasswordExpireAt;

import java.util.Date;

import org.junit.jupiter.api.Test;

public class DateUtilTest {

    @Test
    public void findDaysPasswordExpireAt_correctResult() {
        assertTrue(findDaysPasswordExpireAt(new Date(), new Date()) == 0);

        assertFalse(findDaysPasswordExpireAt(new Date(), new Date()) == 1);
    }

    @Test
    public void findPasswordExpireAt_correctResult() {
        assertFalse(findPasswordExpireAt(new Date()).equals(0));
        assertFalse(findPasswordExpireAt(new Date()).equals(new Date()));
    }
}
