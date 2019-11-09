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

public class HelpCommandIntegrationTest {
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
    public void help_allConstraints() throws CommandException, ParseException {
        logicManager.execute("help\n");
        logicManager.execute("help addprob\n");
        logicManager.execute("help addplan\n");
        logicManager.execute("help addtag\n");
        logicManager.execute("help clear\n");
        logicManager.execute("help deleteprob\n");
        logicManager.execute("help deleteplan\n");
        logicManager.execute("help deletetag\n");
        logicManager.execute("help deletetask\n");
        logicManager.execute("help donetask\n");
        logicManager.execute("help editprob\n");
        logicManager.execute("help editplan\n");
        logicManager.execute("help edittag\n");
        logicManager.execute("help exit\n");
        logicManager.execute("help findprob\n");
        logicManager.execute("help findplan\n");
        logicManager.execute("help help\n");
        logicManager.execute("help listprob\n");
        logicManager.execute("help listplan\n");
        logicManager.execute("help listtag\n");
        logicManager.execute("help sortprob\n");
        logicManager.execute("help switchtab\n");
        logicManager.execute("help undonetask\n");
        logicManager.execute("help apply\n");
        logicManager.execute("help addfindrule\n");
        logicManager.execute("help deletefindrule\n");
        logicManager.execute("help opentab\n");
        logicManager.execute("help closetab\n");
        logicManager.execute("help donetask\n");
    }
}
