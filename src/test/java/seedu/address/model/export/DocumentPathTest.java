//@@author LeowWB

package seedu.address.model.export;

import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.ExportTestUtil.isRunningOnWindows;

import org.junit.jupiter.api.Test;

public class DocumentPathTest {

    @Test
    public void documentPath_invalidPath_throwsException() {
        String[] invalidDocumentPathStrings = {
            "C:\\Users\\User\\Documents\\cheatsheet",
            "cheatsheet.txt",
            "cheatsheet.docxx",
            "cheat.sheet.docx",
            "cheatsheet。docx",
            "cheatsheetdotdocx",
            "cheat|sheet.docx",
            "*cheatsheet*.docx",
            "cheatsheet.json",
            ""
        };

        for (String invalidDocumentPathString : invalidDocumentPathStrings) {
            assertThrows(
                    IllegalArgumentException.class, ()
                    -> new DocumentPath(
                        invalidDocumentPathString
                    )
            );
        }
    }

    @Test
    public void documentPath_validPath_success() {
        String[] validDocumentPathStrings = {
            "cheat_sheet.docx",
            "cheat-sheet.docx",
            "directory/cheatsheet.docx",
            "ChEAtShEAT.doCx",
            "../cheatsheet.docx",
            "~/Desktop/[CS2105] Midterm Cheat Sheet (v3).docx"
        };

        for (String validDocumentPathString : validDocumentPathStrings) {
            try {
                new DocumentPath(validDocumentPathString);
            } catch (IllegalArgumentException e) {
                fail("Valid document path was not recognized as being valid: " + validDocumentPathString);
            }
        }
    }

    @Test
    public void documentPath_validWindowsPath_success() {
        String pathString = "C:\\Users\\User\\Desktop\\[CS2105] Midterm Cheat Sheet (v2).docx";

        if (isRunningOnWindows()) {
            try {
                new DocumentPath(pathString);
            } catch (IllegalArgumentException e) {
                fail("Valid document path was not recognized as being valid: " + pathString);
            }
        }
    }
}
