package seedu.address.storage.printable;

import java.io.IOException;

/**
 * Represents saving a printable file task.
 */
public interface NjoyPrintable {

    String PRINTABLE_DIRECTORY_PATH = "../printable/";

    void savePrintable() throws IOException;
}
