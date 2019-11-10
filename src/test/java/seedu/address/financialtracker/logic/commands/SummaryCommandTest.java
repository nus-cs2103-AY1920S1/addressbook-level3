package seedu.address.financialtracker.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import seedu.address.financialtracker.model.Model;

class SummaryCommandTest {

    @Test
    void execute_withoutModel() {
        assertThrows(NullPointerException.class,
                () -> new SummaryCommand().execute(null));
    }

    @Test
    void execute_withModel() {
        Model model = new Model();

        // should not have a window to initialize
        assertThrows(ExceptionInInitializerError.class, () -> new SummaryCommand().execute(model));
    }

}
