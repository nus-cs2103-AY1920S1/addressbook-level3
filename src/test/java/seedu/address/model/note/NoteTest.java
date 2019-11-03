package seedu.address.model.note;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.note.NoteBuilder;

public class NoteTest {

    private static final String note1 = "Note Title 1";
    private static final String note2 = "Note Title 2";
    private static final String desc1 = "Note Desc 1";
    private static final String desc2 = "Note Desc 2";
    private static final Priority high = Priority.HIGH;
    private static final Priority medium = Priority.MEDIUM;
    private static final Priority low = Priority.LOW;
    private static final Priority unmarked = Priority.UNMARKED;

    @Test
    public void noteComparator_higherPriority_returnsNegative() {
        Note.NoteComparator comparator = new Note.NoteComparator();
        assertTrue(comparator.compare(new NoteBuilder().withPriority(high).build(),
                new NoteBuilder().withPriority(medium).build()) < 0);
        assertTrue(comparator.compare(new NoteBuilder().withPriority(high).build(),
                new NoteBuilder().withPriority(low).build()) < 0);
        assertTrue(comparator.compare(new NoteBuilder().withPriority(high).build(),
                new NoteBuilder().withPriority(unmarked).build()) < 0);
        assertTrue(comparator.compare(new NoteBuilder().withPriority(medium).build(),
                new NoteBuilder().withPriority(low).build()) < 0);
        assertTrue(comparator.compare(new NoteBuilder().withPriority(medium).build(),
                new NoteBuilder().withPriority(unmarked).build()) < 0);
        assertTrue(comparator.compare(new NoteBuilder().withPriority(low).build(),
                new NoteBuilder().withPriority(unmarked).build()) < 0);
    }

    @Test
    public void noteComparator_lowerPriority_returnsPositive() {
        Note.NoteComparator comparator = new Note.NoteComparator();
        assertTrue(comparator.compare(new NoteBuilder().withPriority(unmarked).build(),
                new NoteBuilder().withPriority(low).build()) > 0);
        assertTrue(comparator.compare(new NoteBuilder().withPriority(unmarked).build(),
                new NoteBuilder().withPriority(medium).build()) > 0);
        assertTrue(comparator.compare(new NoteBuilder().withPriority(unmarked).build(),
                new NoteBuilder().withPriority(high).build()) > 0);
        assertTrue(comparator.compare(new NoteBuilder().withPriority(low).build(),
                new NoteBuilder().withPriority(medium).build()) > 0);
        assertTrue(comparator.compare(new NoteBuilder().withPriority(low).build(),
                new NoteBuilder().withPriority(high).build()) > 0);
        assertTrue(comparator.compare(new NoteBuilder().withPriority(medium).build(),
                new NoteBuilder().withPriority(high).build()) > 0);
    }

    @Test
    public void noteComparator_samePriority_returnsZero() {
        Note.NoteComparator comparator = new Note.NoteComparator();
        assertTrue(comparator.compare(new NoteBuilder().withPriority(unmarked).build(),
                new NoteBuilder().withPriority(unmarked).build()) == 0);
        assertTrue(comparator.compare(new NoteBuilder().withPriority(low).build(),
                new NoteBuilder().withPriority(low).build()) == 0);
        assertTrue(comparator.compare(new NoteBuilder().withPriority(medium).build(),
                new NoteBuilder().withPriority(medium).build()) == 0);
        assertTrue(comparator.compare(new NoteBuilder().withPriority(high).build(),
                new NoteBuilder().withPriority(high).build()) == 0);
    }

    @Test
    public void sameNoteTitleSameDescription_consideredDuplicateNote() {
        assertTrue(new NoteBuilder().withNote(note1).withDesc(desc1).build()
                .isSameNote(new NoteBuilder().withNote(note1).withDesc(desc1).build()));
    }

    @Test
    public void sameNoteTitleDifferentDescription_consideredDuplicateNote() {
        assertTrue(new NoteBuilder().withNote(note1).withDesc(desc1).build()
                .isSameNote(new NoteBuilder().withNote(note1).withDesc(desc2).build()));
    }

    @Test
    public void differentNoteTitleSameDescription_consideredUnique() {
        assertFalse(new NoteBuilder().withNote(note1).build()
                .isSameNote(new NoteBuilder().withNote(note2).build()));
    }

    @Test
    public void sameNoteTitleSamePriority_consideredDuplicateNote() {
        assertTrue(new NoteBuilder().withPriority(high).build()
                .isSameNote(new NoteBuilder().withPriority(high).build()));
    }

    @Test
    public void sameNoteTitleDifferentPriority_consideredDuplicateNote() {
        assertTrue(new NoteBuilder().withPriority(high).build()
                .isSameNote(new NoteBuilder().withPriority(medium).build()));
    }

    @Test
    public void sameNoteTitleSameDescriptionSamePriority_consideredEquals() {
        assertTrue(new NoteBuilder().withNote(note1).withDesc(desc1).withPriority(medium).build()
                .equals(new NoteBuilder().withNote(note1).withDesc(desc1).withPriority(medium).build()));
    }

    @Test
    public void sameNoteTitleSameDescriptionDifferentPriority_notEquals() {
        assertFalse(new NoteBuilder().withNote(note1).withDesc(desc1).withPriority(medium).build()
                .equals(new NoteBuilder().withNote(note1).withDesc(desc1).withPriority(unmarked).build()));
    }
}
