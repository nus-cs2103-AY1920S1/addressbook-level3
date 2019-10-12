package dream.fcard.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import org.junit.jupiter.api.Test;

public class FileReadWriteTest {
    @Test
    void testResolve() {
        assertEquals(System.getProperty("user.home") + "/Desktop/b", FileReadWrite.resolve("~/Desktop/a", "../b"));
    }

    @Test
    void testNormalize() {
        assertEquals("a", FileReadWrite.normalizePath("a/b/c/../../"));
    }

    @Test
    void testReadWriteFile() {
        FileReadWrite.write("~/TESTFILE.txt", "abc123");
        try {
            assertEquals("abc123", FileReadWrite.read("~/TESTFILE.txt"));
            FileReadWrite.delete("~/TESTFILE.txt");
        } catch (FileNotFoundException e) {
            fail();
        }
    }

}
