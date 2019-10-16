package seedu.billboard.model.tag;

import static seedu.billboard.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagCountManagerTest {
    private final TagCountManager count = new TagCountManager();

    @Test
    public void contains_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> count.contains(null));
    }


}
