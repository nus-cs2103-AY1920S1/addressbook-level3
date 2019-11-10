package seedu.algobase.integration;

import static seedu.algobase.integration.IntegrationTestUtil.getTempFilePath;
import static seedu.algobase.logic.parser.ParserUtil.FORMATTER;

import java.nio.file.Path;
import java.time.LocalDate;

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

public class EditPlanCommandIntegrationTest {
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
    public void editplan_allconstrains() throws CommandException, ParseException {
        logicManager.execute("editplan 1 n/Basic Data Structures d/DS"
                + " start/" + LocalDate.now().plusMonths(1).format(FORMATTER)
                + " end/" + LocalDate.now().plusMonths(1).format(FORMATTER));
    }

    @Test
    public void editplan_name() throws CommandException, ParseException {
        logicManager.execute("editplan 1 n/Advanced Data Structures");
    }

    @Test
    public void editplan_description() throws CommandException, ParseException {
        logicManager.execute("editplan 1 d/");
        logicManager.execute("editplan 1 d/CS2040");
    }

    @Test
    public void editplan_timerange() throws CommandException, ParseException {
        logicManager.execute("editplan 1"
                + " start/" + LocalDate.now().minusMonths(1).format(FORMATTER)
                + " end/" + LocalDate.now().plusMonths(1).format(FORMATTER));
        logicManager.execute("editplan 1"
                + " start/" + LocalDate.now().plusMonths(1).format(FORMATTER)
                + " end/" + LocalDate.now().plusMonths(1).format(FORMATTER));
        logicManager.execute("editplan 1"
                + " start/" + LocalDate.now().plusMonths(1).format(FORMATTER)
                + " end/" + LocalDate.now().plusMonths(2).format(FORMATTER));
    }
}
