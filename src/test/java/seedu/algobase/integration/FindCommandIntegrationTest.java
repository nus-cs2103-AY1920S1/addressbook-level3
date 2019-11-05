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

    @Test
    public void find_allConstraints() throws CommandException, ParseException {
        logicManager.execute("findprob n/freedom trail d/video game diff/4.7-4.9 src/LeetCode");
        assertProcessedProblemListOfLength(logicManager, 1);
        assertFirstListedProblemOfName(logicManager, "Freedom Trail");
        logicManager.execute("findprob n/sequences a/Tung Kam Chuen d/inversions");
        assertProcessedProblemListOfLength(logicManager, 1);
        assertFirstListedProblemOfName(logicManager, "Sequences");
    }

    @Test
    public void find_name_caseInsensitive() throws CommandException, ParseException {
        logicManager.execute("findprob n/Sequences");
        assertProcessedProblemListOfLength(logicManager, 1);
        assertFirstListedProblemOfName(logicManager, "Sequences");
        logicManager.execute("findprob n/SEquENCEs");
        assertProcessedProblemListOfLength(logicManager, 1);
        assertFirstListedProblemOfName(logicManager, "Sequences");
    }

    @Test
    public void find_name_oneWordMatch() throws CommandException, ParseException {
        logicManager.execute("findprob n/freedom randomimpossibleword");
        assertProcessedProblemListOfLength(logicManager, 1);
        assertFirstListedProblemOfName(logicManager, "Freedom Trail");
        logicManager.execute("findprob n/freedom sequences");
        assertProcessedProblemListOfLength(logicManager, 2); // Should match both "Sequences" and "Freedom Trail"
    }

    @Test
    public void find_name_wordByWordMatch() throws CommandException, ParseException {
        logicManager.execute("findprob n/Sequencess");
        assertProcessedProblemListOfLength(logicManager, 0);
        logicManager.execute("findprob n/Sudoku Solver");
        assertProcessedProblemListOfLength(logicManager, 1);
        logicManager.execute("findprob n/SudokuSolver");
        assertProcessedProblemListOfLength(logicManager, 0);
    }

    @Test
    public void find_author_exactMatch() throws CommandException, ParseException {
        logicManager.execute("findprob a/Wee Han");
        assertProcessedProblemListOfLength(logicManager, 1);
        assertFirstListedProblemOfAuthor(logicManager, "Wee Han");
        logicManager.execute("findprob a/WeeHan");
        assertProcessedProblemListOfLength(logicManager, 0);
        logicManager.execute("findprob a/wee han");
        assertProcessedProblemListOfLength(logicManager, 0);
    }

    @Test
    public void find_nonAlphanumericTag_noException() throws CommandException, ParseException {
        logicManager.execute("findprob t/O&*^(&*^");
        assertProcessedProblemListOfLength(logicManager, 0);
    }

    @Test
    public void find_nonAlphanumericSource_noException() throws CommandException, ParseException {
        logicManager.execute("findprob src/O&*^(&*^");
        assertProcessedProblemListOfLength(logicManager, 0);
    }

    @Test
    public void find_nonAlphanumericAuthor_noException() throws CommandException, ParseException {
        logicManager.execute("findprob a/O&*^(&*^");
        assertProcessedProblemListOfLength(logicManager, 0);
    }

    @Test
    public void find_nonAlphanumericName_noException() throws CommandException, ParseException {
        logicManager.execute("findprob n/O&*^(&*^");
        assertProcessedProblemListOfLength(logicManager, 0);
    }

    @Test
    public void find_nonAlphanumericDescription_noException() throws CommandException, ParseException {
        logicManager.execute("findprob d/O&*^(&*^");
        assertProcessedProblemListOfLength(logicManager, 0);
    }
}
