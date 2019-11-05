package seedu.algobase.integration;

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

public class SortProblemCommandIntegrationTest {
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
    public void sortproblem_allConstraints() throws CommandException, ParseException {
        logicManager.execute("sortprob m/name\n");
        logicManager.execute("sortprob m/name ord/ascend\n");
        logicManager.execute("sortprob m/author ord/ascend\n");
        logicManager.execute("sortprob m/weblink ord/ascend\n");
        logicManager.execute("sortprob m/difficulty ord/ascend\n");
        logicManager.execute("sortprob m/source ord/ascend\n");
        logicManager.execute("sortprob m/name ord/descend\n");
        logicManager.execute("sortprob m/author ord/descend\n");
        logicManager.execute("sortprob m/weblink ord/descend\n");
        logicManager.execute("sortprob m/difficulty ord/descend\n");
        logicManager.execute("sortprob m/source ord/descend\n");
        logicManager.execute("sortprob m/source ord/descend\n");
    }
}
