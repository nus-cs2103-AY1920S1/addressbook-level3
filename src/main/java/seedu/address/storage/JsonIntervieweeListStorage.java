package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyList;
import seedu.address.model.person.Interviewee;

/**
 * A class to address Interviewee data stored as a JSON file on the hard disk.
 */
public class JsonIntervieweeListStorage implements IntervieweeListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonIntervieweeListStorage.class);

    private Path filePath;

    public JsonIntervieweeListStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getIntervieweeListFilePath() {
        return this.filePath;
    }

    @Override
    public Optional<ReadOnlyList<Interviewee>> readIntervieweeList() throws DataConversionException, IOException {
        return this.readIntervieweeList(this.filePath);
    }

    @Override
    public Optional<ReadOnlyList<Interviewee>> readIntervieweeList(Path filePath) throws DataConversionException,
            IOException {
        requireNonNull(filePath);

        Optional<JsonSerializableIntervieweeList> jsonIntervieweeList = JsonUtil.readJsonFile(
                filePath, JsonSerializableIntervieweeList.class);

        if (!jsonIntervieweeList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonIntervieweeList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveIntervieweeList(ReadOnlyList<Interviewee> intervieweeList) throws IOException {
        this.saveIntervieweeList(intervieweeList, this.filePath);
    }

    @Override
    public void saveIntervieweeList(ReadOnlyList<Interviewee> intervieweeList, Path filePath) throws IOException {
        requireNonNull(intervieweeList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableIntervieweeList(intervieweeList), filePath);
    }

}
