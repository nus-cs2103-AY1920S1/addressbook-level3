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

public class AddTagCommandIntegrationTest {
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
    public void addtag_allConstraints() throws CommandException, ParseException {
        logicManager.execute("addtag t/test\n");
        logicManager.execute("addtag t/test1 c/RED\n");
        logicManager.execute("addtag t/test2 c/YELLOW\n");
        logicManager.execute("addtag t/test3 c/ORANGE\n");
        logicManager.execute("addtag t/test4 c/GREEN\n");
        logicManager.execute("addtag t/test5 c/BLUE\n");
        logicManager.execute("addtag t/test6 c/PURPLE\n");
        logicManager.execute("addtag t/test7 c/BLACK\n");
        logicManager.execute("addtag t/test8 c/TEAL\n");
        logicManager.execute("addtag t/test9 c/DEFAULT\n");
    }
}
