package seedu.algobase.integration;

import static seedu.algobase.integration.IntegrationTestUtil.assertProcessedPlanListOfLength;
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

public class AddPlanCommandIntegrationTest {
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
    public void addplan_allConstraints() throws CommandException, ParseException {
        logicManager.execute("addplan n/test d/1234 start/2019-01-01 end/2019-01-01\n");
        logicManager.execute("addplan n/test1 d/_test123 start/2018-01-01 end/2018-02-28\n");
        logicManager.execute("addplan n/test2 d/T E S T start/2018-01-01 end/2020-02-29\n");
        logicManager.execute("addplan n/test3 d/t-e-s-t start/2018-01-01 end/2020-03-31\n");
        logicManager.execute("addplan n/test4 d/test? start/2018-01-01 end/2020-04-30\n");
        logicManager.execute("addplan n/test5 d/test ??? start/2018-01-01 end/2020-05-31\n");
        logicManager.execute("addplan n/test6 d/?test start/2018-01-01 end/2020-06-30\n");
        logicManager.execute("addplan n/test7 d/? test start/2018-01-01 end/2020-07-31\n");
        logicManager.execute("addplan n/test8 d/$$Test$$ start/2018-01-01 end/2020-08-31\n");
        logicManager.execute("addplan n/test9 d/test^{n} start/2018-01-01 end/2020-09-30\n");
        logicManager.execute("addplan n/test10 d/test start/2018-01-01 end/2020-10-31\n");
        logicManager.execute("addplan n/test11 d/test start/2018-01-01 end/2020-11-30\n");
        logicManager.execute("addplan n/test12 d/test start/0000-01-01 end/0999-01-31\n");
        logicManager.execute("addplan n/test13 d/test start/2018-01-01 end/9999-12-31\n");
        assertProcessedPlanListOfLength(logicManager, 16);
    }
}
