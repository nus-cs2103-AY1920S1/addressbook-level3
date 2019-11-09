//@@author LeowWB

package seedu.address.model.export;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.ExportTestUtil.isRunningOnWindows;

import org.junit.jupiter.api.Test;

public class ExportPathFactoryTest {

    private String[] filePathsNoExtensions = {
        "cheat_sheet",
        "cheat-sheet",
        "directory/cheatsheet",
        "ChEAtShEAT",
        "../cheatsheet",
        "~/Desktop/[CS2105] Midterm Cheat Sheet (v3)"
    };

    private String[] validExtensions = {
        ".json",
        ".docx",
        ".JsOn",
        ".DocX"
    };

    private String[] invalidExtensions = {
        ".html",
        ".png",
        ".txt",
        ".jsonn",
        "."
    };

    @Test
    public void getExportPath_valid_success() {
        for (String filePathNoExtension : filePathsNoExtensions) {
            for (String validExtension : validExtensions) {
                String filePath = filePathNoExtension + validExtension;
                ExportPath exportPath = ExportPathFactory.getExportPath(filePath);

                switch (validExtension.toLowerCase()) {
                case ".docx":
                    assertTrue(
                            exportPath instanceof DocumentPath
                    );
                    break;
                case ".json":
                    assertTrue(
                            exportPath instanceof JsonExportPath
                    );
                    break;
                default:
                    fail("Valid export path not recognized as valid: " + filePath);
                }
            }
        }
    }

    @Test
    public void getExportPath_invalidPath_exceptionThrown() {
        for (String filePathNoExtension : filePathsNoExtensions) {
            for (String invalidExtension : invalidExtensions) {
                String filePath = filePathNoExtension + invalidExtension;

                assertThrows(IllegalArgumentException.class, () ->
                        ExportPathFactory.getExportPath(filePath)
                );
            }
        }
    }

    @Test
    public void getExportPath_validWindowsPath_success() {
        if (isRunningOnWindows()) {
            String filePathNoExtension = "C:\\Users\\User\\Desktop\\[CS2105] Midterm Cheat Sheet (v2)";

            for (String validExtension : validExtensions) {
                String filePath = filePathNoExtension + validExtension;
                ExportPath exportPath = ExportPathFactory.getExportPath(filePath);

                switch (validExtension.toLowerCase()) {
                case ".docx":
                    assertTrue(
                            exportPath instanceof DocumentPath
                    );
                    break;
                case ".json":
                    assertTrue(
                            exportPath instanceof JsonExportPath
                    );
                    break;
                default:
                    fail("Valid export path not recognized as valid: " + filePath);
                }
            }
        }
    }
}
