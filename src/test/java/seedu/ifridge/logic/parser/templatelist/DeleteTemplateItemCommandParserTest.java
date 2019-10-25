package seedu.ifridge.logic.parser.templatelist;

import org.junit.jupiter.api.Test;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteTemplateItemCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteTemplateItemCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteTemplateItemCommandParserTest {

    private DeleteTemplateListCommandParser parser = new DeleteTemplateListCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteTemplateCommand() {
        //assertParseSuccess(parser, "1", new DeleteTemplateItemCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        //assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
        //DeleteTemplateItemCommand.MESSAGE_USAGE));
    }
}
