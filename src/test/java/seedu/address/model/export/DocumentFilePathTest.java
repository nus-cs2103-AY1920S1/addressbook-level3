//@@author LeowWB

package seedu.address.model.export;

import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DocumentFilePathTest {

    @Test
    public void documentFilePath_invalidPath_throwsException() {
        String[] invalidDocumentFilePathStrings = {
            "directory/file.docx",
            "directory\\file.docx",
            "file.txt",
            "file.do cx"
        };

        for (String invalidDocumentFilePathString : invalidDocumentFilePathStrings) {
            assertThrows(
                    IllegalArgumentException.class, () ->
                     new DocumentFilePath(
                        invalidDocumentFilePathString
                    )
            );
        }
    }

    @Test
    public void documentFilePath_validPath_success() {
        String[] validDocumentFilePathStrings = {
            "cheat_sheet.docx",
            "cheat-sheet.docx",
            "ChEAtShEAT.doCx",
            "() [].docx",
            "[CS2105] Midterm Cheat Sheet (v2).docx"
        };

        for (String validDocumentFilePathString : validDocumentFilePathStrings) {
            try {
                new DocumentFilePath(validDocumentFilePathString);
            } catch (IllegalArgumentException e) {
                fail("Valid document file path was not recognized as being valid");
            }
        }
    }
}
