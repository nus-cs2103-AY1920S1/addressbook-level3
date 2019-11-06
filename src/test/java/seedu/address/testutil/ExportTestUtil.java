//@@author LeowWB

package seedu.address.testutil;

import static seedu.address.commons.util.FileUtil.isFileExists;

import java.io.File;

import seedu.address.model.export.ExportPath;

/**
 * A utility class for export test cases.
 */
public class ExportTestUtil {

    /**
     * Helper method to check if there's a file present at the given {@code ExportPath}.
     *
     * @param exportPath ExportPath to check presence of a file at
     * @return True if a file is present at the given ExportPath; false otherwise.
     */
    public static boolean isFilePresent(ExportPath exportPath) {
        return isFileExists(
                exportPath.getPath()
        );
    }

    /**
     * Deletes a file at the given ExportPath. Behavior is undefined if there is no file at the specified path.
     *
     * @param exportPath ExportPath of file to delete.
     */
    public static void deleteFile(ExportPath exportPath) {
        File file = new File(exportPath.toString());
        file.delete();
    }

    /**
     * Deletes a file at the given ExportPath, iff it exists. Does not do anything otherwise.
     *
     * @param exportPath ExportPath of file to delete.
     */
    public static void deleteFileIfExists(ExportPath exportPath) {
        if (isFilePresent(exportPath)) {
            deleteFile(exportPath);
        }
    }
}
