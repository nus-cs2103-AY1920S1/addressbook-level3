package seedu.address.logic.commands;

import static seedu.address.logic.commands.AverageCommand.MESSAGE_NO_RECORD;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.AverageType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RecordBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.record.RecordType;
import seedu.sgm.model.food.FoodMap;

public class AverageCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new FoodMap(), new RecordBook());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new FoodMap(),
            new RecordBook());

    @Test
    public void execute_zeroRecordType_throwsCommandException() {
        AverageCommand command = new AverageCommand(AverageType.DAILY,
                RecordType.HEIGHTANDWEIGHT, 5);
        assertThrows(CommandException.class, String.format(MESSAGE_NO_RECORD,
                RecordType.HEIGHTANDWEIGHT), () -> command.execute(model));
    }

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
