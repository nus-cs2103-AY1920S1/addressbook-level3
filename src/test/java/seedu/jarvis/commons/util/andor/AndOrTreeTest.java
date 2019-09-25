package seedu.jarvis.commons.util.andor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.jarvis.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

public class AndOrTreeTest {
    @Test
    public void test() {
        String example = "{"
                + "\"and\": ["
                + "{"
                + "\"or\": ["
                + "\"CS1010\","
                + "\"CS2020\","
                + "\"CS2030\","
                + "\"CS2040\""
                + "]},{"
                + "\"or\": ["
                + "\"MA1101\","
                + "\"CS1231\""
                + "]}]}";
        try {
            AndOrTree aot = new AndOrTree("name", example);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
