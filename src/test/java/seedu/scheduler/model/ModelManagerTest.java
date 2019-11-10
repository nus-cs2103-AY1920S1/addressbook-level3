package seedu.scheduler.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.scheduler.testutil.Assert.assertThrows;
import static seedu.scheduler.testutil.TypicalPersons.ALICE_INTERVIEWEE;
import static seedu.scheduler.testutil.TypicalPersons.BENSON_INTERVIEWER;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import seedu.scheduler.commons.core.GuiSettings;
import seedu.scheduler.testutil.IntervieweeListBuilder;
import seedu.scheduler.testutil.InterviewerListBuilder;
import seedu.scheduler.testutil.SampleInterviewer;
import seedu.scheduler.testutil.SampleSchedules;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new IntervieweeList(), new IntervieweeList(modelManager.getIntervieweeList()));
        assertEquals(new InterviewerList(), new InterviewerList(modelManager.getInterviewerList()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setIntervieweeListFilePath(Paths.get("interviewee/list/file/path"));
        userPrefs.setInterviewerListFilePath(Paths.get("interviewer/list/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setIntervieweeListFilePath(Paths.get("new/interviewee/list/file/path"));
        userPrefs.setInterviewerListFilePath(Paths.get("new/interviewer/list/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setIntervieweeListFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setIntervieweeListFilePath(null));
    }

    @Test
    public void setInterviewerListFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setInterviewerListFilePath(null));
    }

    @Test
    public void setIntervieweeListFilePath_validPath_setsIntervieweeListFilePath() {
        Path path = Paths.get("interviewee/list/file/path");
        modelManager.setIntervieweeListFilePath(path);
        assertEquals(path, modelManager.getIntervieweeListFilePath());
    }

    @Test
    public void setInterviewerListFilePath_validPath_setsInterviewerListFilePath() {
        Path path = Paths.get("interviewer/list/file/path");
        modelManager.setInterviewerListFilePath(path);
        assertEquals(path, modelManager.getInterviewerListFilePath());
    }

    @Test
    public void generateEmptyScheduleList_success() throws ParseException {
        ArrayList<Schedule> expectedList = new ArrayList<>();
        expectedList.add(SampleSchedules.getSampleAvailabilityTable2());
        modelManager.setInterviewerList(SampleInterviewer.getSampleListOfInterviewers());
        ArrayList<Schedule> testOutput = modelManager.generateEmptyScheduleList();
        assertEquals(expectedList, testOutput);
    }

    @Test
    public void equals() {
        IntervieweeList intervieweeList = new IntervieweeListBuilder().withInterviewee(ALICE_INTERVIEWEE).build();
        InterviewerList interviewerList = new InterviewerListBuilder().withInterviewer(BENSON_INTERVIEWER).build();

        IntervieweeList differentIntervieweeList = new IntervieweeList();
        InterviewerList differentInterviewerList = new InterviewerList();

        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(intervieweeList, interviewerList, userPrefs, new LinkedList<>());
        ModelManager modelManagerCopy = new ModelManager(intervieweeList, interviewerList, userPrefs,
                new LinkedList<>());
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different intervieweeList -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentIntervieweeList, interviewerList, userPrefs,
                new LinkedList<>())));

        // different interviewerList -> returns false
        assertFalse(modelManager.equals(new ModelManager(intervieweeList, differentInterviewerList, userPrefs,
                new LinkedList<>())));

        // different intervieweeList and interviewerList -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentIntervieweeList, differentInterviewerList,
                userPrefs, new LinkedList<>())));

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setInterviewerListFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(intervieweeList, interviewerList, differentUserPrefs,
                new LinkedList<>())));
    }
}
