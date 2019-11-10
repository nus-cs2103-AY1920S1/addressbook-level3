package seedu.tarence.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Stack;
import java.util.logging.Logger;

import seedu.tarence.commons.core.LogsCenter;
import seedu.tarence.commons.exceptions.DataConversionException;
import seedu.tarence.commons.exceptions.IllegalValueException;
import seedu.tarence.commons.util.FileUtil;
import seedu.tarence.commons.util.JsonUtil;
import seedu.tarence.model.ReadOnlyApplication;
import seedu.tarence.model.module.Module;

/**
 * A class to access the application states stored as json files on the hard disk.
 */
public class JsonStateStorage implements ApplicationStateStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonApplicationStorage.class);
    private static final String STATE_FILE_PREFIX = "state";
    private static final String STATE_FILE_SUFFIX = ".json";
    private String dataFolderName;
    private String stateFolderName;

    private Stack<Integer> stateStack;

    /**
     * Constructor will be initialised with the folder names.
     *
     * @param dataFolderName Folder name of the data folder used.
     * @param stateFolderName Folder name of the state folder.
     */
    public JsonStateStorage(String dataFolderName, String stateFolderName) {

        this.dataFolderName = dataFolderName;
        this.stateFolderName = stateFolderName;

        stateStack = new Stack<Integer>();
        stateStack.add(0);

        try {
            clearStateFolder();
            logger.fine(Paths.get(dataFolderName, stateFolderName).toString() + " successfully cleared.");
        } catch (IOException e) {
            logger.info("Error in clearing state folder. Possible error with specified directory: "
                    + dataFolderName + "/" + stateFolderName);
        }

    }

    /**
     * Clears the folder used to store temporary state json files.
     * @throws IOException if got error in accessing the file directory.
     */
    public void clearStateFolder() throws IOException {
        Path filePath = Paths.get(dataFolderName, stateFolderName);
        File folderDirectory = filePath.toFile();

        // Delete all files in the directory
        File[] listOfFiles = folderDirectory.listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }

        // Delete directory itself
        folderDirectory.delete();

    }


    /**
     * Saves the application state whenever there is a change in state.
     * @param application Contains TArence's model.
     * @throws IOException thrown when there is an error in saving.
     */
    public void saveApplicationState(ReadOnlyApplication application) {
        requireNonNull(application);

        // Get the next filePath eg "data\states\state5.json".
        Path filePath = getNextFilePath();

        try {
            Date currSemStart = Module.getSemStart();
            // Only save the state if the incoming application is different from the latest application
            ReadOnlyApplication latestApplication = getLatestState();
            Date prevSemStart = Module.getSemStart();
            Module.setSemStart(currSemStart);

            // Only saves the state when there is a change with the current state
            if (!latestApplication.equalsUsingStringComparison(application) || !prevSemStart.equals(currSemStart)) {

                // Save the application state
                FileUtil.createIfMissing(filePath);
                JsonUtil.saveJsonFile(new JsonSerializableApplication(application), filePath);

                // Increment the stack counter
                stateStack.push(stateStack.peek() + 1);

            }
        } catch (NoSuchElementException | IOException e) {
            String loggerMessage = "Unable to save state as state-storage-structure has been altered."
                    + "\nWill be resetting to new state ie start from state 0";

            logger.info(loggerMessage);

            resetStateStorage(application);

        }
    }

    /**
     * Resets the state storage when there is an error ie user deletes the folder.
     *
     * @param application
     */
    public void resetStateStorage(ReadOnlyApplication application) {

        try {
            clearStateFolder();
            this.stateStack = new Stack<>();
            stateStack.add(0);

            saveFirstState(application);

        } catch (IOException e) {
            logger.info("Problem with resetting state storage");
        }

    }

    public String getSemesterStartDateOfLatestState() throws IOException {
        String latestSemStartDate = "";

        try {
            // Read the previous semester start date from the latest state.
            Path filePath = getFilePathFromIndex(getLatestStateIndex());
            Optional<JsonSerializableApplication> jsonApplication = JsonUtil.readJsonFile(
                    filePath, JsonSerializableApplication.class);

            if (jsonApplication.isPresent()) {
                // Iterate through all as all modules will have the same semester start date since it is a class atrbute
                for (JsonAdaptedModule m : jsonApplication.get().getJsonAdaptedModules()) {
                    latestSemStartDate = m.getSemesterStartString();
                }
            } else {
                throw new IOException("Unable to undo as there is a problem with the state file");
            }

        } catch (DataConversionException | IOException e) {
            throw new IOException("Unable to undo as there is a problem with the state file");
        }

        return latestSemStartDate;
    }


    /**
     * Checks if the semester start date has been changed by comparing with the previously saved-state file.
     * @return
     * @throws IOException
     */
    public Boolean hasSemesterStartDateChanged() throws IOException {

        // Semester start is assigned a class level attribute.
        String currentStartDate = String.valueOf(Module.getSemStart());

        String latestSavedStartDate = "";
        try {
            // Read the previous semester start date from the latest state.
            Path filePath = getFilePathFromIndex(getLatestStateIndex());
            Optional<JsonSerializableApplication> jsonApplication = JsonUtil.readJsonFile(
                    filePath, JsonSerializableApplication.class);

            if (jsonApplication.isPresent()) {
                // Iterate through all as all modules will have the same semester start date since it is a class atrbute
                for (JsonAdaptedModule m : jsonApplication.get().getJsonAdaptedModules()) {
                    latestSavedStartDate = m.getSemesterStartString();
                }
            } else {
                throw new IOException("Unable to undo as there is a problem with the state file");
            }

        } catch (DataConversionException e) {
            throw new IOException("Unable to undo as there is a problem with the state file");
        }



        System.out.println("Saved app state start date: " + String.valueOf(latestSavedStartDate));

        if (latestSavedStartDate.equals(currentStartDate)) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * Saves the first state. Has to be handled seperately as in the normal saving, saving is only done when there is
     * a change with the previous state, which does not exist for the first state.
     *
     * @param application
     * @throws IOException thrown when error in saving first state.
     */
    public void saveFirstState(ReadOnlyApplication application) throws IOException {
        requireNonNull(application);

        Path filePath = getNextFilePath();

        // Save the application state
        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableApplication(application), filePath);

        // Increment the stack counter
        stateStack.push(stateStack.peek() + 1);
    }

    /**
     * Checks if the number of rollbacks doesn't exceed the stack size.
     * @param i Specified number of rollback.
     * @return Boolean.
     */
    public Boolean isValidNumberOfRollbacks(Integer i) {
        int finalStateIndex = stateStack.peek() - i;

        return finalStateIndex > 0;
    }

    /**
     * Returns the file path that will be used to save the next application state.
     * @return Path.
     */
    public Path getNextFilePath() {
        String fileName = STATE_FILE_PREFIX + getNextStateIndex().toString()
                + STATE_FILE_SUFFIX;
        return Paths.get(dataFolderName, stateFolderName, fileName);
    }

    /**
     * Returns the state index that will be used to save the next application state.
     * @return Integer.
     */
    public Integer getNextStateIndex() {
        return stateStack.peek() + 1;
    }

    /**
     * Returns the current index in the stack.
     * @return Integer.
     */
    public Integer getLatestStateIndex() {
        return stateStack.peek();
    }

    /**
     * Returns the filepath representing the file of the state, indexed by the stack.
     *
     * @param index Index of said state.
     * @return File Path eg "//data//states//state5.json"
     */
    public Path getFilePathFromIndex(Integer index) {
        String fileName = STATE_FILE_PREFIX + index.toString() + STATE_FILE_SUFFIX;
        return Paths.get(dataFolderName, stateFolderName, fileName);
    }

    /**
     * Get the latest application state.
     *
     * @return ReadOnlyApplication
     * @throws IOException thrown when there is an error in reading of the file.
     */
    public ReadOnlyApplication getLatestState() throws IOException {
        try {
            Path filePath = getFilePathFromIndex(getLatestStateIndex());
            Optional<ReadOnlyApplication> applicationOptional = readApplication(filePath);
            return applicationOptional.get();
        } catch (DataConversionException e) {
            throw new IOException("Unable to undo as there is a problem with the state file");
        }

    }

    /**
     * Returns the ReadOnlyApplication of a specified index.
     * Pre-condition: Index supplied is valid ie handled by UndoCommand
     *
     * @param index of the state that you want to retrieve.
     * @return ReadOnlyApplication from state json file.
     * @throws IOException thrown when got error in reading the state.
     */
    public ReadOnlyApplication getSpecifiedState(Integer index) throws IOException {

        try {
            Path filePath = getFilePathFromIndex(index);
            Optional<ReadOnlyApplication> applicationOptional = readApplication(filePath);

            int numOfStatesToPop = getLatestStateIndex() - index;

            for (int i = 0; i < numOfStatesToPop; i++) {
                stateStack.pop();
            }

            return applicationOptional.get();
        } catch (DataConversionException e) {
            throw new IOException("Unable to undo as there is a problem with the state file");
        }
    }

    /**
     * Reads the json file at the specified filepath
     *
     * @param filePath Where the json file is stored.
     * @return Optional Application
     * @throws DataConversionException If there is a problem with parsing the json file.
     */
    public Optional<ReadOnlyApplication> readApplication(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        // From the Json file, creates an optional JsonSerializableApplication.
        // Relies on @JsonCreator of JsonSerializableApplication class.
        Optional<JsonSerializableApplication> jsonApplication = JsonUtil.readJsonFile(
                filePath, JsonSerializableApplication.class);

        if (!jsonApplication.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonApplication.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    /**
     * Returns the max number that the undo command can accept as an argument.
     *
     * @return Integer.
     */
    public Integer maxNumberOfRollbacksAllowed() {
        return stateStack.peek() - 1;
    }




}
