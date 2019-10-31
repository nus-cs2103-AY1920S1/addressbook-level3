package seedu.scheduler.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import seedu.scheduler.logic.commands.EditIntervieweeCommand.EditIntervieweeDescriptor;
import seedu.scheduler.logic.commands.EditInterviewerCommand.EditInterviewerDescriptor;
import seedu.scheduler.logic.graph.IntervieweeVertex;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.Interviewer;

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
     * Returns the interviewee descriptor with details from the Interviewee {@code i}.
     */
    public static EditIntervieweeDescriptor getDescriptorFromInterviewee(Interviewee i) {
        EditIntervieweeDescriptor e = new EditIntervieweeDescriptor();
        e.setName(i.getName());
        e.setPhone(i.getPhone());
        e.setTags(i.getTags());
        e.setFaculty(i.getFaculty());
        e.setYearOfStudy(i.getYearOfStudy());
        e.setDepartmentChoices(i.getDepartmentChoices());
        e.setAvailableTimeslots(i.getAvailableTimeslots());
        e.setEmails(i.getEmails());
        return e;
    }

    public static EditInterviewerDescriptor getDescriptorFromInterviewer(Interviewer i) {
        EditInterviewerDescriptor e = new EditInterviewerDescriptor();
        e.setName(i.getName());
        e.setPhone(i.getPhone());
        e.setDepartment(i.getDepartment());
        e.setAvailabilities(i.getAvailabilities());
        e.setEmail(i.getEmail());
        return e;
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
