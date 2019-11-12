package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_ONE;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_INTELLIJ;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_ONE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCheatSheets.CS1;
import static seedu.address.testutil.TypicalFlashcards.MATH_ONE;
import static seedu.address.testutil.TypicalFlashcards.getTypicalStudyBuddyPro;
import static seedu.address.testutil.TypicalNotes.PIPELINE;
//import java.util.Arrays;

import java.util.Collection;
import java.util.Collections;
//import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.cheatsheet.CheatSheet;
import seedu.address.model.flashcard.Flashcard;
//import seedu.address.model.flashcard.exceptions.DuplicateFlashcardQuestionException;
//import seedu.address.model.flashcard.exceptions.DuplicateFlashcardTitleException;
import seedu.address.model.note.Note;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
//import seedu.address.testutil.FlashcardBuilder;

public class StudyBuddyProTest {

    private final StudyBuddyPro studyBuddyPro = new StudyBuddyPro();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), studyBuddyPro.getFlashcardList());
        assertEquals(Collections.emptyList(), studyBuddyPro.getNoteList());
        assertEquals(Collections.emptyList(), studyBuddyPro.getCheatSheetList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> studyBuddyPro.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyStudyBuddyPro_replacesData() {
        StudyBuddyPro newData = getTypicalStudyBuddyPro();
        studyBuddyPro.resetData(newData);
        assertEquals(newData, studyBuddyPro);
    }

    /* To fix, test design wrong?
    @Test
    public void resetData_withDuplicateFlashcards_throwsDuplicateFlashcardQuestionException() {
        // Two flashcards with the same question fields.
        Flashcard editedMathOne = new FlashcardBuilder(MATH_ONE).withTitle(VALID_TITLE_ONE).withTags(VALID_TAG_INTELLIJ)
                .build();
        List<Flashcard> newFlashcards = Arrays.asList(MATH_ONE, editedMathOne);
        StudyBuddyProStub newData = new StudyBuddyProStub(newFlashcards);

        assertThrows(DuplicateFlashcardQuestionException.class, () -> studyBuddyPro.resetData(newData));
    }

    @Test
    public void resetData_withDuplicateFlashcards_throwsDuplicateFlashcardTitleException() {
        // Two flashcards with the same title fields.
        Flashcard editedMathOne =
                new FlashcardBuilder(MATH_ONE).withQuestion(VALID_QUESTION_ONE).withTags(VALID_TAG_INTELLIJ)
                .build();
        List<Flashcard> newFlashcards = Arrays.asList(MATH_ONE, editedMathOne);
        StudyBuddyProStub newData = new StudyBuddyProStub(newFlashcards);

        assertThrows(DuplicateFlashcardTitleException.class, () -> studyBuddyPro.resetData(newData));
    }
    */

    @Test
    public void hasFlashcard_nullFlashcard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> studyBuddyPro.hasFlashcard(null));
    }

    @Test
    public void hasNote_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> studyBuddyPro.hasNote(null));
    }

    @Test
    public void hasCheatSheet_nullCheatSheet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> studyBuddyPro.hasCheatSheet(null));
    }

    @Test
    public void hasFlashcard_flashcardNotInStudyBuddyPro_returnsFalse() {
        assertFalse(studyBuddyPro.hasFlashcard(MATH_ONE));
    }

    @Test
    public void hasNote_noteNotInStudyBuddyPro_returnsFalse() {
        assertFalse(studyBuddyPro.hasNote(PIPELINE));
    }

    @Test
    public void hasCheatSheet_cheatsheetNotInStudyBuddyPro_returnsFalse() {
        assertFalse(studyBuddyPro.hasCheatSheet(CS1));
    }

    @Test
    public void hasFlashcard_flashcardInStudyBuddyPro_returnsTrue() {
        studyBuddyPro.addFlashcard(MATH_ONE);
        assertTrue(studyBuddyPro.hasFlashcard(MATH_ONE));
    }

    @Test
    public void hasNote_noteInStudyBuddyPro_returnsTrue() {
        studyBuddyPro.addNote(PIPELINE);
        assertTrue(studyBuddyPro.hasNote(PIPELINE));
    }

    @Test
    public void hasCheatSheet_cheatsheetInStudyBuddyPro_returnsTrue() {
        studyBuddyPro.addCheatSheet(CS1);
        assertTrue(studyBuddyPro.hasCheatSheet(CS1));
    }

    /* Unused test, kept for reference
    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        studyBuddyPro.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(studyBuddyPro.hasPerson(editedAlice));
    }
    */

    @Test
    public void getFlashcardList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> studyBuddyPro.getFlashcardList().remove(0));
    }

    @Test
    public void getNoteList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> studyBuddyPro.getNoteList().remove(0));
    }

    @Test
    public void getCheatSheetList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> studyBuddyPro.getCheatSheetList().remove(0));
    }

    /**
     * A stub ReadOnlyStudyBuddyPro whose flashcard list can violate interface constraints.
     */
    private static class StudyBuddyProStub implements ReadOnlyStudyBuddyPro {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Flashcard> flashcards = FXCollections.observableArrayList();
        private final ObservableList<Note> notes = FXCollections.observableArrayList();
        private final ObservableList<CheatSheet> cheatSheets = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        StudyBuddyProStub(Collection<Flashcard> flashcards) {
            this.flashcards.setAll(flashcards);
        }

        @Override
        public ObservableList<CheatSheet> getCheatSheetList() {
            return cheatSheets;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }

        @Override
        public ObservableList<Flashcard> getFlashcardList() {
            return flashcards;
        }

        @Override
        public ObservableList<Note> getNoteList() {
            return notes;
        }
    }
}
