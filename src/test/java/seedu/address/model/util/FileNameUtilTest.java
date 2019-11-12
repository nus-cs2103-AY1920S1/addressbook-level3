package seedu.address.model.util;

import static seedu.address.testutil.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class FileNameUtilTest {
    @Test
    public void getFileNameWithPrefix() {
        assertEquals("[LOCKED] Test File.txt",
                FileNameUtil.getFileNameWithPrefix("Test File.txt"));
        assertEquals("[LOCKED] Test File.txt",
                FileNameUtil.getFileNameWithPrefix("[LOCKED] Test File.txt"));
    }

    @Test
    public void getFileNameWithoutPrefix() {
        assertEquals("Test File.txt",
                FileNameUtil.getFileNameWithoutPrefix("Test File.txt"));
        assertEquals("Test File.txt",
                FileNameUtil.getFileNameWithoutPrefix("[LOCKED] Test File.txt"));
    }
}
