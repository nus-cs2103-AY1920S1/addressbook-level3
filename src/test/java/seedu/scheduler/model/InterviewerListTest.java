package seedu.scheduler.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.scheduler.testutil.Assert.assertThrows;
import static seedu.scheduler.testutil.TypicalPersons.ALICE_INTERVIEWER;
import static seedu.scheduler.testutil.TypicalPersons.BENSON_INTERVIEWER;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.model.person.UniquePersonList;
import seedu.scheduler.model.person.exceptions.DuplicatePersonException;
import seedu.scheduler.model.person.exceptions.PersonNotFoundException;

class InterviewerListTest {

    private InterviewerList interviewerList;

    @BeforeEach
    public void setUp() {
        interviewerList = new InterviewerList();
    }

    @Test
    public void constructor_emptyInterviewerList_success() {
        ObservableList<Interviewer> emptyInterviewerList = FXCollections.observableArrayList();
        assertEquals(emptyInterviewerList, interviewerList.getEntityList());
    }

    @Test
    public void addEntity_validInterviewer_success() {
        interviewerList.addEntity(ALICE_INTERVIEWER);
        assertTrue(interviewerList.hasEntity(ALICE_INTERVIEWER));
    }

    @Test
    public void addEntity_duplicateInterviewer_throwsDuplicatePersonException() {
        interviewerList.addEntity(ALICE_INTERVIEWER);
        assertThrows(DuplicatePersonException.class, () -> interviewerList.addEntity(ALICE_INTERVIEWER));
    }

    @Test
    public void getEntity_validInterviewer_success() {
        interviewerList.addEntity(ALICE_INTERVIEWER);
        assertEquals(ALICE_INTERVIEWER, interviewerList.getEntity(ALICE_INTERVIEWER.getName()));
    }

    @Test
    public void getEntity_invalidInterviewer_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> interviewerList.getEntity(ALICE_INTERVIEWER.getName()));
    }

    @Test
    public void setEntity_validInterviewer_success() {
        interviewerList.addEntity(ALICE_INTERVIEWER);
        interviewerList.setEntity(ALICE_INTERVIEWER, BENSON_INTERVIEWER);
        assertEquals(BENSON_INTERVIEWER, interviewerList.getEntity(BENSON_INTERVIEWER.getName()));
        assertThrows(PersonNotFoundException.class, () -> interviewerList.getEntity(ALICE_INTERVIEWER.getName()));
    }

    @Test
    public void setEntity_invalidInterviewer_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () ->
                interviewerList.setEntity(ALICE_INTERVIEWER, BENSON_INTERVIEWER));
    }

    @Test
    public void removeEntity_validInterviewer_success() {
        interviewerList.addEntity(ALICE_INTERVIEWER);
        assertTrue(interviewerList.hasEntity(ALICE_INTERVIEWER));
        interviewerList.removeEntity(ALICE_INTERVIEWER);
        assertFalse(interviewerList.hasEntity(ALICE_INTERVIEWER));
    }

    @Test
    public void removeEntity_invalidInterviewer_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> interviewerList.removeEntity(ALICE_INTERVIEWER));
    }

    @Test
    public void hasEntity_validInterviewerExists_returnsTrue() {
        interviewerList.addEntity(ALICE_INTERVIEWER);
        assertTrue(interviewerList.hasEntity(ALICE_INTERVIEWER));
    }

    @Test
    public void hasEntity_validInterviewerDoesNotExist_returnsFalse() {
        assertFalse(interviewerList.hasEntity(ALICE_INTERVIEWER));
    }

    @Test
    public void getEntityList_validInterviewer_success() {
        assertEquals((new UniquePersonList<Interviewer>()).asUnmodifiableObservableList(),
                interviewerList.getEntityList());

        UniquePersonList<Interviewer> uniqueInterviewerList = new UniquePersonList<>();
        uniqueInterviewerList.add(ALICE_INTERVIEWER);
        uniqueInterviewerList.add(BENSON_INTERVIEWER);
        ObservableList<Interviewer> expectedList = uniqueInterviewerList.asUnmodifiableObservableList();

        interviewerList.addEntity(ALICE_INTERVIEWER);
        interviewerList.addEntity(BENSON_INTERVIEWER);
        assertEquals(expectedList, interviewerList.getEntityList());
    }

    @Test
    public void setInterviewerList_validInterviewers_success() {
        List<Interviewer> listOfInterviewers = Arrays.asList(ALICE_INTERVIEWER, BENSON_INTERVIEWER);
        assertFalse(interviewerList.hasEntity(ALICE_INTERVIEWER));
        assertFalse(interviewerList.hasEntity(BENSON_INTERVIEWER));
        interviewerList.setInterviewerList(listOfInterviewers);
        assertTrue(interviewerList.hasEntity(ALICE_INTERVIEWER));
        assertTrue(interviewerList.hasEntity(BENSON_INTERVIEWER));
    }

    @Test
    public void toString_validInterviewers_success() {
        assertEquals("0 interviewers", interviewerList.toString());

        interviewerList.addEntity(ALICE_INTERVIEWER);
        assertEquals("1 interviewers", interviewerList.toString());
    }

    @Test
    public void equals_success() {
        // Same object -> returns true
        assertTrue(interviewerList.equals(interviewerList));

        // Null object -> returns false
        assertFalse(interviewerList.equals(null));

        // Different objects, same contents -> returns true
        interviewerList.addEntity(ALICE_INTERVIEWER);
        InterviewerList anotherInterviewerList = new InterviewerList();
        anotherInterviewerList.addEntity(ALICE_INTERVIEWER);
        assertTrue(interviewerList.equals(anotherInterviewerList));

        // Different objects, different contents -> return false
        InterviewerList yetAnotherInterviewerList = new InterviewerList();
        yetAnotherInterviewerList.addEntity(BENSON_INTERVIEWER);
        assertFalse(interviewerList.equals(yetAnotherInterviewerList));
    }

    @Test
    public void hashCode_validInterviewers_success() {
        interviewerList.addEntity(ALICE_INTERVIEWER);
        UniquePersonList<Interviewer> uniquePersonList = new UniquePersonList<>();
        uniquePersonList.add(ALICE_INTERVIEWER);
        assertEquals(uniquePersonList.hashCode(), interviewerList.hashCode());
    }

}
