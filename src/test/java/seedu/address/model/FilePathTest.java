package seedu.address.model;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

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

}
