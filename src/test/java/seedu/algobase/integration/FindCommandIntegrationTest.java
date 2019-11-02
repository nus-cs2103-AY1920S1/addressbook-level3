package seedu.algobase.integration;

import static seedu.algobase.integration.IntegrationTestUtil.assertFirstListedProblemOfAuthor;
import static seedu.algobase.integration.IntegrationTestUtil.assertFirstListedProblemOfName;
import static seedu.algobase.integration.IntegrationTestUtil.assertProcessedProblemListOfLength;
import static seedu.algobase.integration.IntegrationTestUtil.getTempFilePath;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.algobase.logic.LogicManager;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.ModelManager;
import seedu.algobase.model.ReadOnlyAlgoBase;
import seedu.algobase.model.UserPrefs;
import seedu.algobase.model.util.SampleDataUtil;
import seedu.algobase.storage.JsonAlgoBaseStorage;
import seedu.algobase.storage.JsonUserPrefsStorage;
import seedu.algobase.storage.StorageManager;

public class FindCommandIntegrationTest {

    // --- COPY BELOW FOR INTEGRATION TESTS ----------------------------------------------------------------

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;
    private ModelManager modelManager;
    private LogicManager logicManager;

    @BeforeEach
    public void setup() {
        ReadOnlyAlgoBase sampleAlgoBase = SampleDataUtil.getSampleAlgoBase();
        JsonAlgoBaseStorage algoBaseStorage = new JsonAlgoBaseStorage(getTempFilePath(testFolder, "ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath(testFolder, "prefs"));
        storageManager = new StorageManager(algoBaseStorage, userPrefsStorage);
        modelManager = new ModelManager(sampleAlgoBase, new UserPrefs());
        logicManager = new LogicManager(modelManager, storageManager);
    }

    // --- COPY ABOVE FOR INTEGRATION TESTS ----------------------------------------------------------------

    @Test
    public void find_allConstraints() throws CommandException, ParseException {
        logicManager.execute("find n/freedom trail d/video game diff/4.7-4.9 src/LeetCode");
        assertProcessedProblemListOfLength(logicManager, 1);
        assertFirstListedProblemOfName(logicManager, "Freedom Trail");
        logicManager.execute("find n/sequences a/Tung Kam Chuen d/inversions");
        assertProcessedProblemListOfLength(logicManager, 1);
        assertFirstListedProblemOfName(logicManager, "Sequences");
    }

    @Test
    public void find_name_caseInsensitive() throws CommandException, ParseException {
        logicManager.execute("find n/Sequences");
        assertProcessedProblemListOfLength(logicManager, 1);
        assertFirstListedProblemOfName(logicManager, "Sequences");
        logicManager.execute("find n/SEquENCEs");
        assertProcessedProblemListOfLength(logicManager, 1);
        assertFirstListedProblemOfName(logicManager, "Sequences");
    }

    @Test
    public void find_name_oneWordMatch() throws CommandException, ParseException {
        logicManager.execute("find n/freedom randomimpossibleword");
        assertProcessedProblemListOfLength(logicManager, 1);
        assertFirstListedProblemOfName(logicManager, "Freedom Trail");
        logicManager.execute("find n/freedom sequences");
        assertProcessedProblemListOfLength(logicManager, 2); // Should match both "Sequences" and "Freedom Trail"
    }

    @Test
    public void find_name_wordByWordMatch() throws CommandException, ParseException {
        logicManager.execute("find n/Sequencess");
        assertProcessedProblemListOfLength(logicManager, 0);
        logicManager.execute("find n/Sudoku Solver");
        assertProcessedProblemListOfLength(logicManager, 1);
        logicManager.execute("find n/SudokuSolver");
        assertProcessedProblemListOfLength(logicManager, 0);
    }

    @Test
    public void find_author_exactMatch() throws CommandException, ParseException {
        logicManager.execute("find a/Wee Han");
        assertProcessedProblemListOfLength(logicManager, 1);
        assertFirstListedProblemOfAuthor(logicManager, "Wee Han");
        logicManager.execute("find a/WeeHan");
        assertProcessedProblemListOfLength(logicManager, 0);
        logicManager.execute("find a/wee han");
        assertProcessedProblemListOfLength(logicManager, 0);
    }

}
