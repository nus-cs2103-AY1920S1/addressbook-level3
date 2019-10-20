package seedu.address.logic.commands;

import static seedu.address.logic.commands.AverageCommand.MESSAGE_NO_RECORD;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.statistics.AverageType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.record.RecordType;

public class AverageCommandTest {
    private Model model = new ModelManager();

    @Test
    public void execute_zeroRecordType_throwsCommandException() {
        AverageCommand command = new AverageCommand(AverageType.DAILY,
                RecordType.HEIGHTANDWEIGHT, 5);
        assertThrows(CommandException.class, String.format(MESSAGE_NO_RECORD,
                RecordType.HEIGHTANDWEIGHT), () -> command.execute(model));
    }

}
