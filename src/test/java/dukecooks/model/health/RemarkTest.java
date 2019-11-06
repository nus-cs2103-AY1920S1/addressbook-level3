package dukecooks.model.health;

import org.junit.jupiter.api.Test;

import dukecooks.model.health.components.Remark;
import dukecooks.testutil.Assert;

public class RemarkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Remark(null));
    }

    @Test
    public void constructor_invalidRemarkName_throwsIllegalArgumentException() {
        String invalidRemarkName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Remark(invalidRemarkName));
    }

    @Test
    public void isValidRemarkName() {
        // null remark name
        Assert.assertThrows(NullPointerException.class, () -> Remark.isValidRemarkName(null));
    }

}
