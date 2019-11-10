//package seedu.guilttrip.logic.parser;
//
//import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.guilttrip.logic.parser.CommandParserTestUtil.assertParseFailure;
//import static seedu.guilttrip.logic.parser.CommandParserTestUtil.assertParseSuccess;
//
//import java.util.Arrays;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.guilttrip.logic.commands.findcommands.FindExpenseCommand;
//import seedu.guilttrip.model.entry.predicates.entries.DescriptionContainsKeywordsPredicate;
//
//public class FindCommandParserTest {
//

//    private FindExpenseCommandParserTest parser = new FindExpenseCommandParserTest();
//
//    @Test
//    public void parse_emptyArg_throwsParseException() {
//        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
//        FindExpenseCommand.MESSAGE_USAGE));
//    }
//
//    @Test
//    public void parse_validArgs_returnsFindCommand() {
//        // no leading and trailing whitespaces
//        FindExpenseCommand expectedFindCommand =
//                new FindExpenseCommand(new DescriptionContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
//        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);
//
//        // multiple whitespaces between keywords
//        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
//    }
//
//}
