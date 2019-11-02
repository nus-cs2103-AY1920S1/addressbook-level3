package seedu.mark.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.testutil.Assert.assertThrows;
import static seedu.mark.testutil.TypicalBookmarks.AMY;
import static seedu.mark.testutil.TypicalBookmarks.BOB;
import static seedu.mark.testutil.TypicalBookmarks.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.mark.testutil.MarkBuilder;

public class VersionedMarkTest {

    private final ReadOnlyMark markWithAmy = new MarkBuilder().withBookmark(AMY).build();
    private final ReadOnlyMark markWithBob = new MarkBuilder().withBookmark(BOB).build();
    private final ReadOnlyMark markWithCarl = new MarkBuilder().withBookmark(CARL).build();
    private final ReadOnlyMark emptyMark = new MarkBuilder().build();

    @Test
    public void save_singleMark_noStatesRemovedCurrentStateSaved() {
        VersionedMark versionedMark = new VersionedMark(emptyMark);

        versionedMark.save("");
        assertMarkListStatus(versionedMark,
                Collections.singletonList(emptyMark),
                emptyMark,
                Collections.emptyList());
    }

    @Test
    public void save_multipleMarkPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedMark versionedMark = prepareMarkList(
                emptyMark, markWithAmy, markWithBob);

        versionedMark.save("");
        assertMarkListStatus(versionedMark,
                Arrays.asList(emptyMark, markWithAmy, markWithBob),
                markWithBob,
                Collections.emptyList());
    }

    @Test
    public void save_multipleMarkPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedMark versionedMark = prepareMarkList(
                emptyMark, markWithAmy, markWithBob);
        versionedMark.undo(2);

        versionedMark.save("");
        assertMarkListStatus(versionedMark,
                Collections.singletonList(emptyMark),
                emptyMark,
                Collections.emptyList());
    }

    @Test
    public void getMaxStepsToUndo_multipleMarkPointerAtEndOfStateList_returnsTrue() {
        VersionedMark versionedMark = prepareMarkList(
                emptyMark, markWithAmy, markWithBob);

        assertEquals(2, versionedMark.getMaxStepsToUndo());
    }

    @Test
    public void getMaxStepsToUndo_multipleMarkPointerNotAtEndOfStateList_returnsTrue() {
        VersionedMark versionedMark = prepareMarkList(
                emptyMark, markWithAmy, markWithBob);
        versionedMark.undo(1);

        assertEquals(1, versionedMark.getMaxStepsToUndo());
    }

    @Test
    public void getMaxStepsToUndo_singleMark_returnsFalse() {
        VersionedMark versionedMark = prepareMarkList(emptyMark);

        assertEquals(0, versionedMark.getMaxStepsToUndo());
    }

    @Test
    public void getMaxStepsToUndo_multipleMarkPointerAtStartOfStateList_returnsFalse() {
        VersionedMark versionedMark = prepareMarkList(
                emptyMark, markWithAmy, markWithBob);
        versionedMark.undo(2);

        assertEquals(0, versionedMark.getMaxStepsToUndo());
    }

    @Test
    public void getMaxStepsToRedo_multipleMarkPointerNotAtEndOfStateList_returnsTrue() {
        VersionedMark versionedMark = prepareMarkList(
                emptyMark, markWithAmy, markWithBob);
        versionedMark.undo(1);

        assertEquals(1, versionedMark.getMaxStepsToRedo());
    }

    @Test
    public void getMaxStepsToRedo_multipleMarkPointerAtStartOfStateList_returnsTrue() {
        VersionedMark versionedMark = prepareMarkList(
                emptyMark, markWithAmy, markWithBob);
        versionedMark.undo(2);

        assertEquals(2, versionedMark.getMaxStepsToRedo());
    }

    @Test
    public void getMaxStepsToRedo_singleMark_returnsFalse() {
        VersionedMark versionedMark = prepareMarkList(emptyMark);

        assertEquals(0, versionedMark.getMaxStepsToRedo());
    }

    @Test
    public void getMaxStepsToRedo_multipleMarkPointerAtEndOfStateList_returnsFalse() {
        VersionedMark versionedMark = prepareMarkList(
                emptyMark, markWithAmy, markWithBob);

        assertEquals(0, versionedMark.getMaxStepsToRedo());
    }

    @Test
    public void canUndo_multipleMarkPointerAtEndOfStateList_returnsTrue() {
        VersionedMark versionedMark = prepareMarkList(
                emptyMark, markWithAmy, markWithBob);

        assertTrue(versionedMark.canUndo(1));
    }

    @Test
    public void canUndo_multipleMarkPointerNotAtEndOfStateList_returnsTrue() {
        VersionedMark versionedMark = prepareMarkList(
                emptyMark, markWithAmy, markWithBob);
        versionedMark.undo(1);

        assertTrue(versionedMark.canUndo(1));
    }

    @Test
    public void canUndo_singleMark_returnsFalse() {
        VersionedMark versionedMark = prepareMarkList(emptyMark);

        assertFalse(versionedMark.canUndo(1));
    }

    @Test
    public void canUndo_multipleMarkPointerAtStartOfStateList_returnsFalse() {
        VersionedMark versionedMark = prepareMarkList(
                emptyMark, markWithAmy, markWithBob);
        versionedMark.undo(2);

        assertFalse(versionedMark.canUndo(1));
    }

    @Test
    public void canRedo_multipleMarkPointerNotAtEndOfStateList_returnsTrue() {
        VersionedMark versionedMark = prepareMarkList(
                emptyMark, markWithAmy, markWithBob);
        versionedMark.undo(1);

        assertTrue(versionedMark.canRedo(1));
    }

    @Test
    public void canRedo_multipleMarkPointerAtStartOfStateList_returnsTrue() {
        VersionedMark versionedMark = prepareMarkList(
                emptyMark, markWithAmy, markWithBob);
        versionedMark.undo(2);

        assertTrue(versionedMark.canRedo(1));
    }

    @Test
    public void canRedo_singleMark_returnsFalse() {
        VersionedMark versionedMark = prepareMarkList(emptyMark);

        assertFalse(versionedMark.canRedo(1));
    }

    @Test
    public void canRedo_multipleMarkPointerAtEndOfStateList_returnsFalse() {
        VersionedMark versionedMark = prepareMarkList(
                emptyMark, markWithAmy, markWithBob);

        assertFalse(versionedMark.canRedo(1));
    }

    @Test
    public void undo_multipleMarkPointerAtEndOfStateList_success() {
        VersionedMark versionedMark = prepareMarkList(
                emptyMark, markWithAmy, markWithBob);

        versionedMark.undo(1);
        assertMarkListStatus(versionedMark,
                Collections.singletonList(emptyMark),
                markWithAmy,
                Collections.singletonList(markWithBob));
    }

    @Test
    public void undo_multipleMarkPointerNotAtStartOfStateList_success() {
        VersionedMark versionedMark = prepareMarkList(
                emptyMark, markWithAmy, markWithBob);
        versionedMark.undo(1);

        versionedMark.undo(1);
        assertMarkListStatus(versionedMark,
                Collections.emptyList(),
                emptyMark,
                Arrays.asList(markWithAmy, markWithBob));
    }

    @Test
    public void undo_singleMark_throwsNoUndoableStateException() {
        VersionedMark versionedMark = prepareMarkList(emptyMark);

        assertThrows(VersionedMark.CannotUndoMarkException.class, () -> versionedMark.undo(1));
    }

    //
    @Test
    public void undo_multipleMarkPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedMark versionedMark = prepareMarkList(
                emptyMark, markWithAmy, markWithBob);
        versionedMark.undo(2);

        assertThrows(VersionedMark.CannotUndoMarkException.class, () -> versionedMark.undo(1));
    }

    @Test
    public void redo_multipleMarkPointerNotAtEndOfStateList_success() {
        VersionedMark versionedMark = prepareMarkList(
                emptyMark, markWithAmy, markWithBob);
        versionedMark.undo(1);

        versionedMark.redo(1);
        assertMarkListStatus(versionedMark,
                Arrays.asList(emptyMark, markWithAmy),
                markWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleMarkPointerAtStartOfStateList_success() {
        VersionedMark versionedMark = prepareMarkList(
                emptyMark, markWithAmy, markWithBob);
        versionedMark.undo(2);

        versionedMark.redo(1);
        assertMarkListStatus(versionedMark,
                Collections.singletonList(emptyMark),
                markWithAmy,
                Collections.singletonList(markWithBob));
    }

    @Test
    public void redo_singleMark_throwsCannotRedoMarkException() {
        VersionedMark versionedMark = prepareMarkList(emptyMark);

        assertThrows(VersionedMark.CannotRedoMarkException.class, () -> versionedMark.redo(1));
    }

    @Test
    public void redo_multipleMarkPointerAtEndOfStateList_throwsCannotRedoMarkException() {
        VersionedMark versionedMark = prepareMarkList(
                emptyMark, markWithAmy, markWithBob);

        assertThrows(VersionedMark.CannotRedoMarkException.class, () -> versionedMark.redo(1));
    }

    @Test
    public void equals() {
        VersionedMark versionedMark = prepareMarkList(markWithAmy, markWithBob);

        // same values -> returns true
        VersionedMark copy = prepareMarkList(markWithAmy, markWithBob);
        assertTrue(versionedMark.equals(copy));

        // same object -> returns true
        assertTrue(versionedMark.equals(versionedMark));

        // null -> returns false
        assertFalse(versionedMark.equals(null));

        // different types -> returns false
        assertFalse(versionedMark.equals(1));

        // different state record list -> returns false
        VersionedMark differentMarkStateRecords = prepareMarkList(markWithBob, markWithCarl);
        assertFalse(versionedMark.equals(differentMarkStateRecords));

        // different current pointer index -> returns false
        VersionedMark differentCurrentPointer = prepareMarkList(
                markWithAmy, markWithBob);
        versionedMark.undo(1);
        assertFalse(versionedMark.equals(differentCurrentPointer));
    }

    /**
     * Asserts that {@code versionedMark} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedMark#currentPointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedMark#currentPointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertMarkListStatus(VersionedMark versionedMark,
                                      List<ReadOnlyMark> expectedStatesBeforePointer,
                                      ReadOnlyMark expectedCurrentState,
                                      List<ReadOnlyMark> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new Mark(versionedMark), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedMark.canUndo(1)) {
            versionedMark.undo(1);
        }

        // check states before pointer are correct
        for (ReadOnlyMark expectedStateRecord : expectedStatesBeforePointer) {
            assertEquals(expectedStateRecord, new Mark(versionedMark));
            versionedMark.redo(1);
        }

        // check states after pointer are correct
        for (ReadOnlyMark expectedStateRecord : expectedStatesAfterPointer) {
            versionedMark.redo(1);
            assertEquals(expectedStateRecord, new Mark(versionedMark));
        }

        // check that there are no more states after pointer
        assertFalse(versionedMark.canRedo(1));

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedMark.undo(1));
    }

    /**
     * Creates and returns a {@code VersionedMark} with the {@code MarkStateRecords} added into it, and the
     * {@code VersionedMark#currentPointer} at the end of list.
     */
    private VersionedMark prepareMarkList(ReadOnlyMark... markStates) {
        assertFalse(markStates.length == 0);

        VersionedMark versionedMark = new VersionedMark(markStates[0]);
        for (int i = 1; i < markStates.length; i++) {
            versionedMark.resetData(markStates[i]);
            // empty record
            versionedMark.save("");
        }

        return versionedMark;
    }
}
