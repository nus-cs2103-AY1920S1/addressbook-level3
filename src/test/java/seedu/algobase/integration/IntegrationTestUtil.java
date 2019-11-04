package seedu.algobase.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import seedu.algobase.logic.LogicManager;

/**
 * A utility class for integration tests.
 */
public class IntegrationTestUtil {

    public static Path getTempFilePath(Path testFolder, String fileName) {
        return testFolder.resolve(fileName);
    }

    public static void assertProcessedProblemListOfLength(LogicManager logicManager, int length) {
        assertEquals(length, logicManager.getProcessedProblemList().size());
    }

    public static void assertFirstListedProblemOfName(LogicManager logicManager, String fullName) {
        assertEquals(fullName, logicManager.getProcessedProblemList().get(0).getName().fullName);
    }

    public static void assertFirstListedProblemOfAuthor(LogicManager logicManager, String authorName) {
        assertEquals(authorName, logicManager.getProcessedProblemList().get(0).getAuthor().value);
    }

    public static void assertProcessedPlanListOfLength(LogicManager logicManager, int length) {
        assertEquals(length, logicManager.getProcessedPlanList().size());
    }

    public static void assertPlanName(LogicManager logicManager, String fullName) {
        assertEquals(1, logicManager.getProcessedPlanList().size());
        assertEquals(fullName, logicManager.getProcessedPlanList().get(0).getPlanName().fullName);
    }

}
