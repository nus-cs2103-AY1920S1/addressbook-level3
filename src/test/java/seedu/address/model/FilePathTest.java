package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.export.FilePath;

public class FilePathTest {

    @Test
    public void filePath_invalidPath_throwsException() {
        String invalidFilePathStrings[] = {
                "C:\\Users\\User\\Documents\\cheatsheet",
                "cheatsheet.txt",
                "cheatsheet.docxx",
                "cheat.sheet.docx",
                "cheatsheet。docx",
                "cheatsheetdotdocx",
                "cheat|sheet.docx",
                "*cheatsheet*.docx",
                "https://www.user.com/cheatsheet.docx"
        };

        for (String invalidFilePathString : invalidFilePathStrings) {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> new FilePath(
                            invalidFilePathString
                    )
            );
        }
    }

    @Test
    public void filePath_validPath_success() {
        String validFilePathStrings[] = {
                "cheat_sheet.docx",
                "cheat-sheet.docx",
                "windows_directory/cheatsheet.docx",
                "unix_directory\\cheatsheet.docx",
                "C:\\Users\\User\\Desktop\\[CS2105] Midterm Cheat Sheet (v2).docx",
                "~/Desktop/[CS2105] Midterm Cheat Sheet (v3).docx"
        };

        for (String validFilePathString : validFilePathStrings) {
            FilePath validFilePath = new FilePath(validFilePathString);
            assertEquals(validFilePath.toString(), validFilePathString);
        }
    }
}
