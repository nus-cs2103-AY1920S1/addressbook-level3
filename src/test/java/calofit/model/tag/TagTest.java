package calofit.model.tag;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import calofit.testutil.Assert;

public class TagTest {

    public static final String VALID_TAG_STRING = "abc";

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        Assert.assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    public void toString_test() {
        Assertions.assertEquals("[" + VALID_TAG_STRING + "]",
            new Tag(VALID_TAG_STRING).toString());
    }

}
