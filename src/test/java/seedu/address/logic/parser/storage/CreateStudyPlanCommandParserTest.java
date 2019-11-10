package seedu.address.logic.parser.storage;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.storage.CreateStudyPlanCommand;

/**
 * Test class for {@code CreateStudyPlanCommandParser}.
 */
public class CreateStudyPlanCommandParserTest {
    private CreateStudyPlanCommandParser parser = new CreateStudyPlanCommandParser();

    @Test
    public void parse_anyArg_returnsCreateStudyPlanCommand() {
        String validTitle = "title";
        CreateStudyPlanCommand expectedCreateStudyPlanCommand = new CreateStudyPlanCommand(validTitle);
        assertParseSuccess(parser, validTitle, expectedCreateStudyPlanCommand);
    }

}
