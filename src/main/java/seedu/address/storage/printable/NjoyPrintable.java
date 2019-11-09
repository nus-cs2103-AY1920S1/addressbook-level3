package seedu.address.storage.printable;

import java.io.IOException;

/**
 * Represents saving a printable file task.
 */
public interface NjoyPrintable {
    String FILE_SEPARATOR = System.getProperty("file.separator");
    String PRINTABLE_DIRECTORY_PATH = System.getProperty("user.dir") + FILE_SEPARATOR + "printable"
            + FILE_SEPARATOR;

    void savePrintable() throws IOException;
}
