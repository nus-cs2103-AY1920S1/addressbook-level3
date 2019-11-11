package seedu.weme.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class VersionedWemeTest extends ApplicationTest {

    private final VersionedWeme versionedWeme = new VersionedWeme(new Weme());

    @Test
    public void undo_noUndoStates_throwsNoUndoableStateException() {
        assertThrows(VersionedWeme.NoUndoableStateException.class, () -> versionedWeme.undo());
    }

    @Test
    public void redo_noRedoStates_throwsNoRedoableStateException() {
        assertThrows(VersionedWeme.NoRedoableStateException.class, () -> versionedWeme.redo());
    }

    @Test
    public void equals() {
        assertTrue(versionedWeme.equals(versionedWeme));

        assertFalse(versionedWeme.equals("random string"));
    }
}
