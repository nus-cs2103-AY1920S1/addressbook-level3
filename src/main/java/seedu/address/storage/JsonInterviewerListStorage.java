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
import seedu.address.model.person.Interviewer;

/**
 * A class to address Interviewer data stored as a JSON file on the hard disk.
 */
public class JsonInterviewerListStorage implements InterviewerListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonInterviewerListStorage.class);

    private Path filePath;

    public JsonInterviewerListStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getInterviewerListFilePath() {
        return this.filePath;
    }

    @Override
    public Optional<ReadOnlyList<Interviewer>> readInterviewerList() throws DataConversionException, IOException {
        return this.readInterviewerList(this.filePath);
    }

    @Override
    public Optional<ReadOnlyList<Interviewer>> readInterviewerList(Path filePath) throws DataConversionException,
            IOException {
        requireNonNull(filePath);

        Optional<JsonSerializableInterviewerList> jsonInterviewerList = JsonUtil.readJsonFile(
                filePath, JsonSerializableInterviewerList.class);

        if (!jsonInterviewerList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonInterviewerList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveInterviewerList(ReadOnlyList<Interviewer> interviewerList) throws IOException {
        this.saveInterviewerList(interviewerList, this.filePath);
    }

    @Override
    public void saveInterviewerList(ReadOnlyList<Interviewer> interviewerList, Path filePath) throws IOException {
        requireNonNull(interviewerList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableInterviewerList(interviewerList), filePath);
    }

}
