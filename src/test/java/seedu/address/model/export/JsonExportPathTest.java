//@@author LeowWB

package seedu.address.model.export;

import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class JsonExportPathTest {

    @Test
    public void jsonExportPath_invalidPath_throwsException() {
        String[] invalidJsonExportPathStrings = {
            "C:\\Users\\User\\Documents\\cs2105",
            "directory/cs2105",
            "cs2105.txt",
            "cs2105.jsonn",
            "cs.2105.json",
            "cs2105。json",
            "cs2105dotjson",
            "cs|2105.json",
            "*cs2105*.json",
            "cs2105.docx",
            ""
        };

        for (String invalidJsonExportPathString : invalidJsonExportPathStrings) {
            assertThrows(
                    IllegalArgumentException.class, ()
                    -> new JsonExportPath(
                        invalidJsonExportPathString
                    )
            );
        }
    }

    @Test
    public void jsonExportPath_validPath_success() {
        String[] validJsonExportPathStrings = {
            "cs_2105.json",
            "cs-2105.json",
            "directory/cs2105.json",
            "cS2105.JsOn",
            "../cs2105.json",
            "~/Desktop/[CS2105] exported flashcards.json"
        };

        for (String validJsonExportPathString : validJsonExportPathStrings) {
            try {
                new JsonExportPath(validJsonExportPathString);
            } catch (IllegalArgumentException e) {
                fail("Valid JSON export path was not recognized as being valid");
            }
        }
    }
}
