//@@author LeowWB

package seedu.address.testutil;

import static seedu.address.commons.util.FileUtil.isFileExists;

import java.io.File;

import seedu.address.model.export.ExportPath;

/**
 * A utility class for export test cases.
 */
public class ExportTestUtil {

    public static boolean isFilePresent(ExportPath exportPath) {
        return isFileExists(
                exportPath.getPath()
        );
    }

    public static void deleteFile(ExportPath exportPath) {
        File file = new File(exportPath.toString());
        file.delete();
    }

    public static void deleteFileIfExists(ExportPath exportPath) {
        if (isFilePresent(exportPath)) {
            deleteFile(exportPath);
        }
    }
}
