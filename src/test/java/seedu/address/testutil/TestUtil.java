package seedu.address.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.graph.IntervieweeVertex;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final Path SANDBOX_FOLDER = Paths.get("src", "test", "data", "sandbox");

    /**
     * Appends {@code fileName} to the sandbox folder path and returns the resulting path.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static Path getFilePathInSandboxFolder(String fileName) {
        try {
            Files.createDirectories(SANDBOX_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER.resolve(fileName);
    }

    /**
     * (WONT WORK) Returns the middle index of the person in the {@code model}'s person list.
     */
    public static Index getMidIndex(Model model) {
        return Index.fromOneBased(model.getFilteredIntervieweeList().size() / 2);
    }

    /**
     * (WONT WORK) Returns the last index of the person in the {@code model}'s person list.
     */
    public static Index getLastIndex(Model model) {
        return Index.fromOneBased(model.getFilteredIntervieweeList().size());
    }

    /**
     * (WONT WORK) Returns the person in the {@code model}'s person list at {@code index}.
     */
    public static Person getPerson(Model model, Index index) {
        return model.getFilteredInterviewerList().get(index.getZeroBased());
    }

    /**
     * Returns the given two dimensional array of strings as a two dimensional LinkedList of strings.
     */
    public static LinkedList<LinkedList<String>> toTwoDimensionalLinkedList(String[][] table) {
        LinkedList<LinkedList<String>> tableCopy = new LinkedList<>();
        for (String[] row : table) {
            LinkedList<String> rowCopy = new LinkedList<>(Arrays.asList(row));
            tableCopy.add(rowCopy);
        }
        return tableCopy;
    }

    /**
     * Fills up each item in the list given with a sublist.
     */
    public static void fillWithSubLists(List<List<IntervieweeVertex>> list, int size) {
        for (int i = 0; i < size; i++) {
            list.add(new LinkedList<>());
        }
    }
}
