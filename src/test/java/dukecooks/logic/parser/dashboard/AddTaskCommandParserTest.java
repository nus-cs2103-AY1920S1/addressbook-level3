package dukecooks.logic.parser.dashboard;

import static dukecooks.testutil.dashboard.TypicalDashboard.TASK1;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.dashboard.AddTaskCommand;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.logic.parser.CommandParserTestUtil;
import dukecooks.model.dashboard.components.Dashboard;
import dukecooks.model.dashboard.components.DashboardName;
import dukecooks.model.dashboard.components.TaskDate;
import dukecooks.testutil.dashboard.DashboardBuilder;

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
