package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.AlfredException;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.entitylist.TeamList;

/**
 * A class to access TeamList data stored as a json file on the hard disk.
 */
public class JsonTeamListStorage implements TeamListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTeamListStorage.class);

    private Path filePath;

    public JsonTeamListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTeamListFilePath() {
        return filePath;
    }

    @Override
    public Optional<TeamList> readTeamList() throws AlfredException {
        return readTeamList(filePath);
    }

    /**
     * Similar to {@link #readTeamList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<TeamList> readTeamList(Path filePath) throws AlfredException {
        requireNonNull(filePath);

        Optional<JsonSerializableTeamList> jsonTeamList = JsonUtil.readJsonFile(
                filePath, JsonSerializableTeamList.class);
        if (!jsonTeamList.isPresent()) {
            return Optional.empty();
        }

        try {
            //Converts to Optional<TeamList>
            return Optional.of(jsonTeamList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveTeamList(TeamList tList) throws IOException {
        saveTeamList(tList, filePath);
    }

    /**
     * Similar to {@link #saveTeamList(TeamList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveTeamList(TeamList tList, Path filePath) throws IOException {
        requireNonNull(tList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTeamList(tList), filePath);
    }
}

