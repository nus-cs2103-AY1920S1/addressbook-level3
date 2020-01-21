package seedu.algobase.integration;

import static seedu.algobase.integration.IntegrationTestUtil.assertPlanName;
import static seedu.algobase.integration.IntegrationTestUtil.assertProcessedPlanListOfLength;
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


public class FindPlanCommandIntegrationTest {
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
    public void findplan_allConstraints() throws CommandException, ParseException {
        logicManager.execute("findplan n/Data Structures d/CS2040"
                + " start/" + LocalDate.now().plusDays(2).format(FORMATTER)
                + " end/" + LocalDate.now().plusDays(30).format(FORMATTER)
                + " task/Sequences");
        assertPlanName(logicManager, "Data Structures");
        logicManager.execute("findplan n/graph d/CS3230"
                + " start/" + LocalDate.now().minusDays(30).format(FORMATTER)
                + " end/" + LocalDate.now().plusDays(2).format(FORMATTER)
                + " task/" + "Reconstruct Itinerary");
        assertPlanName(logicManager, "Graph Algorithms");
    }

    @Test
    public void findplan_name() throws CommandException, ParseException {
        logicManager.execute("findplan n/data");
        assertPlanName(logicManager, "Data Structures");
        logicManager.execute("findplan n/graph algorithms");
        assertPlanName(logicManager, "Graph Algorithms");
    }

    @Test
    public void findplan_descirption() throws CommandException, ParseException {
        logicManager.execute("findplan d/CS2040");
        assertPlanName(logicManager, "Data Structures");
        logicManager.execute("findplan d/CS3230");
        assertPlanName(logicManager, "Graph Algorithms");
    }

    @Test
    public void findplan_timerange() throws CommandException, ParseException {
        logicManager.execute("findplan"
                + " start/" + LocalDate.now().minusDays(30).format(FORMATTER)
                + " end/" + LocalDate.now().plusDays(2).format(FORMATTER));
        assertProcessedPlanListOfLength(logicManager, 2);
    }

    @Test
    public void findplan_task() throws CommandException, ParseException {
        logicManager.execute("findplan task/Is Graph Bipartite");
        assertPlanName(logicManager, "Graph Algorithms");
        logicManager.execute("findplan task/Freedom Trail");
        assertPlanName(logicManager, "Data Structures");
    }
}
