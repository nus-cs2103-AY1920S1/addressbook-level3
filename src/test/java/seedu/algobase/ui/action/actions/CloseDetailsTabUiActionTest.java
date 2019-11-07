package seedu.algobase.ui.action.actions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CloseDetailsTabUiActionTest {
    @Test
    public void constructor_nullProblem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CloseDetailsTabUiAction(null, null));
    }

}
