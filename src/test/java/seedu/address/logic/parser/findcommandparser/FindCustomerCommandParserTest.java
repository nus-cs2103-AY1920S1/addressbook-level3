/*package seedu.address.logic.parser.findcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import java.util.function.Predicate;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.findcommand.FindCustomerCommand;
import seedu.address.model.customer.predicates.ContactNumberContainsKeywordsPredicate;
import seedu.address.model.customer.predicates.CustomerNameContainsKeywordsPredicate;
import seedu.address.model.customer.predicates.CustomerTagContainsKeywordsPredicate;
import seedu.address.model.customer.predicates.EmailContainsKeywordsPredicate;

public class FindCustomerCommandParserTest {

    private FindCustomerCommandParser parser = new FindCustomerCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
        FindCustomerCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {

        // no leading and trailing whitespaces

        FindCustomerCommand expectedFindCommand =
                new FindCustomerCommand(new CustomerNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"))
                        .or(new ContactNumberContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")))
                        .or(new EmailContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")))
                        .or(new CustomerTagContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"))));
        assertParseSuccess(parser, "Alice", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/\n Alice \n \t Bob  \t", expectedFindCommand);
    }

}*/

