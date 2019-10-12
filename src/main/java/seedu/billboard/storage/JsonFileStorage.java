package seedu.billboard.storage;

import java.nio.file.Path;

public class JsonFileStorage {

    private Path filePath;

    JsonFileStorage(Path filePath) {
        this.filePath = filePath;
    }

    protected Path getFilePath() {
        return filePath;
    }
}
