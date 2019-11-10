package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

//@@author weikiat97
public class MementoTest {

    private ReadOnlyNotebook state;
    private ReadOnlyNotebook notebook;
    private Memento memento;

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Memento(null));
    }

    @Test
    public void getState_assertEqualsState() {
        notebook = new Notebook();
        memento = new Memento(notebook);
        assertEquals(notebook, memento.getState());
    }
}
