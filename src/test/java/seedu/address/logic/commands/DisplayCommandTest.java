package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertDisplayCommandSuccess;
import static seedu.address.model.visual.DisplayFormat.LINECHART;
import static seedu.address.model.visual.DisplayIndicator.GENDER_BREAKDOWN;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.visual.DisplayFormat;
import seedu.address.model.visual.DisplayIndicator;
import seedu.address.testutil.TestUtil.ModelStub;

class DisplayCommandTest {

    // Both expected and actual model uses model stub as display command does not manipulate model.
    private ModelStub modelStub;

    @BeforeEach
    public void setUp() {
        modelStub = new ModelStub();
    }

    @Test
    public void execute_validInputs_success() {
        DisplayIndicator displayIndicator = new DisplayIndicator(GENDER_BREAKDOWN);
        DisplayFormat displayFormat = new DisplayFormat(LINECHART);
        assertDisplayCommandSuccess(
            new DisplayCommand(displayIndicator, displayFormat),
            modelStub,
            DisplayCommand.getMessageSuccess(displayIndicator, displayFormat),
            modelStub);
    }
}
