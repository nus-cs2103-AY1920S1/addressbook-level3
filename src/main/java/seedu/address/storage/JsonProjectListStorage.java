package seedu.address.storage;


import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.ExcelUtil;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyProjectList;

/**
 * A class to access ProjectList data stored as a json file on the hard disk.
 */
public class JsonProjectListStorage implements ProjectListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonProjectListStorage.class);

    private Path filePath;
    private Path budgetsExcelFilePath;

    public JsonProjectListStorage(Path filePath, Path excelFilePath) {
        this.filePath = filePath;
        this.budgetsExcelFilePath = excelFilePath;
    }

    public Path getProjectListFilePath() {
        return filePath;
    }

    public Path getBudgetsExcelFilePath() {
        return budgetsExcelFilePath;
    }

    @Override
    public Optional<ReadOnlyProjectList> readProjectList() throws DataConversionException {
        return readProjectList(filePath);
    }

    /**
     * Similar to {@link #readProjectList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyProjectList> readProjectList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableProjectList> jsonProjectList = JsonUtil.readJsonFile(
                filePath, JsonSerializableProjectList.class);
        if (jsonProjectList.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonProjectList.get().toModelType());
        } catch (IllegalValueException | ParseException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveProjectList(ReadOnlyProjectList projectList) throws IOException {
        saveProjectList(projectList, filePath);
    }

    /**
     * Similar to {@link #saveProjectList(ReadOnlyProjectList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveProjectList(ReadOnlyProjectList projectList, Path filePath) throws IOException {
        requireNonNull(projectList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableProjectList(projectList), filePath);
    }

    @Override
    public void saveBudgetsToExcel(ReadOnlyProjectList projectList) throws IOException {
        saveBudgetsToExcel(projectList, budgetsExcelFilePath);
    }

    @Override
    public void saveBudgetsToExcel(ReadOnlyProjectList projectList, Path filePath) throws IOException {
        requireNonNull(projectList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        ExcelUtil.writeBudgetsToFile(filePath, projectList);
    }

}
