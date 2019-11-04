package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;

import seedu.address.commons.util.FileUtil;

/**
 * Stores images used for profiles
 */
public class ImageStorage {

    private Path path;

    public ImageStorage(Path path) {
        this.path = path;
    }

    public Path getImagePath() {
        return this.path;
    }

    public void createImageFile() throws IOException {
        FileUtil.createIfMissing(this.path);
    }
}
