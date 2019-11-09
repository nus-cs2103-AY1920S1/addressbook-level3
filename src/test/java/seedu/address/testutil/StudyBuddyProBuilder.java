package seedu.address.testutil;

import seedu.address.model.StudyBuddyPro;
import seedu.address.model.cheatsheet.CheatSheet;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.note.Note;

/**
 * A utility class to help with building StudyBuddyPro objects.
 */
public class StudyBuddyProBuilder {

    private StudyBuddyPro studyBuddyPro;

    public StudyBuddyProBuilder() {
        studyBuddyPro = new StudyBuddyPro();
    }

    public StudyBuddyProBuilder(StudyBuddyPro studyBuddyPro) {
        this.studyBuddyPro = studyBuddyPro;
    }

    /**
     * Adds a new {@code Flashcard} to the {@code StudyBuddyPro} that we are building.
     */
    public StudyBuddyProBuilder withFlashcard(Flashcard flashcard) {
        studyBuddyPro.addFlashcard(flashcard);
        return this;
    }

    /**
     * Adds a new {@code Note} to the {@code StudyBuddyPro} that we are building.
     */
    public StudyBuddyProBuilder withNote(Note note) {
        studyBuddyPro.addNote(note);
        return this;
    }

    /**
     * Adds a new {@code CheatSheet} to the {@code StudyBuddyPro} that we are building.
     */
    public StudyBuddyProBuilder withCheatSheet(CheatSheet cheatsheet) {
        studyBuddyPro.addCheatSheet(cheatsheet);
        return this;
    }

    public StudyBuddyPro build() {
        return studyBuddyPro;
    }
}
