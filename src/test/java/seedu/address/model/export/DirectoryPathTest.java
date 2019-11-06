//@@author LeowWB

package seedu.address.model.export;

import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class DirectoryPathTest {

    @Test
    public void directoryPath_invalidPath_throwsException() {
        String[] invalidDirectoryPathStrings = {
            "directory\\?????\\directory2",
            "directory/?????/directory2",
            "directory/*****/directory2",
            "directory\\*****\\directory2"
        };

        for (String invalidDirectoryPathString : invalidDirectoryPathStrings) {
            assertThrows(
                IllegalArgumentException.class, () ->
                    new DirectoryPath(
                            Paths.get(invalidDirectoryPathString)
                    )
            );
        }
    }

    @Test
    public void directoryPath_validPath_success() {
        String[] validDirectoryPathStrings = {
            "directory",
            "/directory/",
            "\\directory\\",
            "\\b c\\d",
            "/b c/d",
            "C:\\Users\\User\\Desktop\\",
            "~/Desktop/",
            "../",
            "..\\",
            "/",
            "\\",
            "GitHub\\main\\.git"
        };

        for (String validDirectoryPathString : validDirectoryPathStrings) {
            try {
                new DirectoryPath(
                        Paths.get(validDirectoryPathString)
                );
            } catch (IllegalArgumentException e) {
                fail("Valid directory file path was not recognized as being valid");
            }
        }
    }
}
