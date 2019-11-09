package seedu.address.ui;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StylesTest {

    @Test
    public void inValidTextFlowArgs() {
        assertThrows(IllegalArgumentException.class, () -> Styles.buildTextFlow(null, "hi"));
        assertThrows(IllegalArgumentException.class, () -> Styles.buildTextFlow("hi", null));
        assertThrows(IllegalArgumentException.class, () -> Styles.buildTextFlow("", ""));
    }
}
