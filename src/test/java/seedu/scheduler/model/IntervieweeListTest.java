package seedu.scheduler.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.scheduler.testutil.Assert.assertThrows;
import static seedu.scheduler.testutil.TypicalPersons.ALICE_INTERVIEWEE;
import static seedu.scheduler.testutil.TypicalPersons.BENSON_INTERVIEWEE;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.UniquePersonList;
import seedu.scheduler.model.person.exceptions.DuplicatePersonException;
import seedu.scheduler.model.person.exceptions.PersonNotFoundException;

class IntervieweeListTest {

    private IntervieweeList intervieweeList;

    @BeforeEach
    public void setUp() {
        intervieweeList = new IntervieweeList();
    }

    @Test
    public void constructor_emptyIntervieweeList_success() {
        ObservableList<Interviewee> emptyIntervieweeList = FXCollections.observableArrayList();
        assertEquals(emptyIntervieweeList, intervieweeList.getEntityList());
    }

    @Test
    public void addEntity_validInterviewee_success() {
        intervieweeList.addEntity(ALICE_INTERVIEWEE);
        assertTrue(intervieweeList.hasEntity(ALICE_INTERVIEWEE));
    }

    @Test
    public void addEntity_duplicateInterviewee_throwsDuplicatePersonException() {
        intervieweeList.addEntity(ALICE_INTERVIEWEE);
        assertThrows(DuplicatePersonException.class, () -> intervieweeList.addEntity(ALICE_INTERVIEWEE));
    }

    @Test
    public void getEntity_validInterviewee_success() {
        intervieweeList.addEntity(ALICE_INTERVIEWEE);
        assertEquals(ALICE_INTERVIEWEE, intervieweeList.getEntity(ALICE_INTERVIEWEE.getName()));
    }

    @Test
    public void getEntity_invalidInterviewee_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> intervieweeList.getEntity(ALICE_INTERVIEWEE.getName()));
    }

    @Test
    public void setEntity_validInterviewee_success() {
        intervieweeList.addEntity(ALICE_INTERVIEWEE);
        intervieweeList.setEntity(ALICE_INTERVIEWEE, BENSON_INTERVIEWEE);
        assertEquals(BENSON_INTERVIEWEE, intervieweeList.getEntity(BENSON_INTERVIEWEE.getName()));
        assertThrows(PersonNotFoundException.class, () -> intervieweeList.getEntity(ALICE_INTERVIEWEE.getName()));
    }

    @Test
    public void setEntity_invalidInterviewee_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () ->
                intervieweeList.setEntity(ALICE_INTERVIEWEE, BENSON_INTERVIEWEE));
    }

    @Test
    public void removeEntity_validInterviewee_success() {
        intervieweeList.addEntity(ALICE_INTERVIEWEE);
        assertTrue(intervieweeList.hasEntity(ALICE_INTERVIEWEE));
        intervieweeList.removeEntity(ALICE_INTERVIEWEE);
        assertFalse(intervieweeList.hasEntity(ALICE_INTERVIEWEE));
    }

    @Test
    public void removeEntity_invalidInterviewee_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> intervieweeList.removeEntity(ALICE_INTERVIEWEE));
    }

    @Test
    public void hasEntity_validIntervieweeExists_returnsTrue() {
        intervieweeList.addEntity(ALICE_INTERVIEWEE);
        assertTrue(intervieweeList.hasEntity(ALICE_INTERVIEWEE));
    }

    @Test
    public void hasEntity_validIntervieweeDoesNotExist_returnsFalse() {
        assertFalse(intervieweeList.hasEntity(ALICE_INTERVIEWEE));
    }

    @Test
    public void getEntityList_validInterviewee_success() {
        assertEquals((new UniquePersonList<Interviewee>()).asUnmodifiableObservableList(),
                intervieweeList.getEntityList());

        UniquePersonList<Interviewee> uniqueIntervieweeList = new UniquePersonList<>();
        uniqueIntervieweeList.add(ALICE_INTERVIEWEE);
        uniqueIntervieweeList.add(BENSON_INTERVIEWEE);
        ObservableList<Interviewee> expectedList = uniqueIntervieweeList.asUnmodifiableObservableList();

        intervieweeList.addEntity(ALICE_INTERVIEWEE);
        intervieweeList.addEntity(BENSON_INTERVIEWEE);
        assertEquals(expectedList, intervieweeList.getEntityList());
    }

    @Test
    public void setIntervieweeList_validInterviewees_success() {
        List<Interviewee> listOfInterviewees = Arrays.asList(ALICE_INTERVIEWEE, BENSON_INTERVIEWEE);
        assertFalse(intervieweeList.hasEntity(ALICE_INTERVIEWEE));
        assertFalse(intervieweeList.hasEntity(BENSON_INTERVIEWEE));
        intervieweeList.setIntervieweeList(listOfInterviewees);
        assertTrue(intervieweeList.hasEntity(ALICE_INTERVIEWEE));
        assertTrue(intervieweeList.hasEntity(BENSON_INTERVIEWEE));
    }

    @Test
    public void toString_validInterviewees_success() {
        assertEquals("0 interviewees", intervieweeList.toString());

        intervieweeList.addEntity(ALICE_INTERVIEWEE);
        assertEquals("1 interviewees", intervieweeList.toString());
    }

    @Test
    public void equals_success() {
        // Same object -> returns true
        assertTrue(intervieweeList.equals(intervieweeList));

        // Null object -> returns false
        assertFalse(intervieweeList.equals(null));

        // Different objects, same contents -> returns true
        intervieweeList.addEntity(ALICE_INTERVIEWEE);
        IntervieweeList anotherIntervieweeList = new IntervieweeList();
        anotherIntervieweeList.addEntity(ALICE_INTERVIEWEE);
        assertTrue(intervieweeList.equals(anotherIntervieweeList));

        // Different objects, different contents -> return false
        IntervieweeList yetAnotherIntervieweeList = new IntervieweeList();
        yetAnotherIntervieweeList.addEntity(BENSON_INTERVIEWEE);
        assertFalse(intervieweeList.equals(yetAnotherIntervieweeList));
    }

    @Test
    public void hashCode_validInterviewees_success() {
        intervieweeList.addEntity(ALICE_INTERVIEWEE);
        UniquePersonList<Interviewee> uniquePersonList = new UniquePersonList<>();
        uniquePersonList.add(ALICE_INTERVIEWEE);
        assertEquals(uniquePersonList.hashCode(), intervieweeList.hashCode());
    }

}
