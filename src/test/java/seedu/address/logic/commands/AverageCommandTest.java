package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.AverageType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.record.RecordType;

public class AverageCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_dailyAverageType_success() {
        String expectedMessage = "average for BLOODSUGAR 2019-10-09 is 4.0\r\n"
                + "average for BLOODSUGAR 2019-10-08 is 3.0\r\n"
                + "average for BLOODSUGAR 2019-10-02 is 2.0\r\n"
                + "average for BLOODSUGAR 2019-10-01 is 1.0\r\n"
                + "average for BLOODSUGAR 2019-09-15 is 5.0";
        AverageCommand command = new AverageCommand(AverageType.DAILY, RecordType.BLOODSUGAR, 5);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }
}
