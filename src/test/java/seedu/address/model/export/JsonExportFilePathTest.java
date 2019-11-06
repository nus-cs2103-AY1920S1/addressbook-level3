//@@author LeowWB

package seedu.address.model.export;

import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class JsonExportFilePathTest {

    @Test
    public void jsonExportFilePath_invalidPath_throwsException() {
        String[] invalidJsonExportFilePathStrings = {
            "cs2105",
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

        for (String invalidJsonExportFilePathString : invalidJsonExportFilePathStrings) {
            assertThrows(
                    IllegalArgumentException.class, ()
                    -> new JsonExportFilePath(
                        invalidJsonExportFilePathString
                    )
            );
        }
    }

    @Test
    public void jsonExportFilePath_validPath_success() {
        String[] validJsonExportFilePathStrings = {
            "cs_2105.json",
            "cs-2105.json",
            "cs2105.json",
            "cS2105.JsOn",
            "cs2105.json",
            "[CS2105] exported flashcards.json",
            "[CS2105] exported flashcards.json"
        };

        for (String validJsonExportFilePathString : validJsonExportFilePathStrings) {
            try {
                new JsonExportFilePath(validJsonExportFilePathString);
            } catch (IllegalArgumentException e) {
                fail("Valid JSON export file path was not recognized as being valid");
            }
        }
    }
}
