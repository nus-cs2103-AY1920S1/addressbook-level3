package seedu.address.storage.export;

import java.io.IOException;

public interface Export {
    String EXPORT_DIRECTORY_PATH = "../export/";

    void saveExport() throws IOException;
}
