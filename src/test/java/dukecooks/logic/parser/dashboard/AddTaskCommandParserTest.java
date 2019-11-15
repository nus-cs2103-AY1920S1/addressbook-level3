package dukecooks.logic.parser.dashboard;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.logic.commands.dashboard.AddTaskCommand;
import dukecooks.logic.parser.CommandParserTestUtil;
import dukecooks.model.dashboard.components.TaskDate;

public class AddTaskCommandParserTest {
    private AddTaskCommandParser parser = new AddTaskCommandParser();

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE);

        // missing name prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_DASHBOARDNAME_YOGA,
                expectedMessage);

        // all prefixes missing
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_DASHBOARDNAME_YOGA,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid date
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_YOGA
                + CommandTestUtil.INVALID_TASKDATE_DESC, TaskDate.MESSAGE_CONSTRAINTS);

    }
}
