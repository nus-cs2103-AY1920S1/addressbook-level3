package seedu.address.logic.parser;

//import seedu.address.logic.commands.DeleteStudyPlanCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteStudyPlanCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteStudyPlanCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    //private DeleteCommandParser parser = new DeleteCommandParser();

    /*
    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteStudyPlanCommand(INDEX_FIRST_STUDYPLAN));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
        DeleteStudyPlanCommand.MESSAGE_USAGE));
    }
     */
}
