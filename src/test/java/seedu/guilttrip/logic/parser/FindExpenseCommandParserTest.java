package seedu.guilttrip.logic.parser;

import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.guilttrip.logic.commands.CommandTestUtil.AMOUNT;
import static seedu.guilttrip.logic.commands.CommandTestUtil.FIND_BY_AMOUNT;
import static seedu.guilttrip.logic.commands.CommandTestUtil.FIND_BY_DESCRIPTION;
import static seedu.guilttrip.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.guilttrip.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.logic.commands.findcommands.FindExpenseCommand;
import seedu.guilttrip.logic.parser.findcommandparsers.FindExpenseCommandParser;
import seedu.guilttrip.model.entry.Entry;
import seedu.guilttrip.model.entry.predicates.EntryContainsAmountPredicate;
import seedu.guilttrip.model.entry.predicates.EntryContainsDescriptionPredicate;

public class FindExpenseCommandParserTest {

    private FindExpenseCommandParser parser = new FindExpenseCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindExpenseCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_singleArgs_returnsFindCommand() {
        EntryContainsDescriptionPredicate descriptionPredicate =
                new EntryContainsDescriptionPredicate(Arrays.asList("pgp", "cotton"));
        List<Predicate<Entry>> prediEntry = Arrays.asList(descriptionPredicate);

        // no leading and trailing whitespaces
        FindExpenseCommand expectedFindCommand =
                new FindExpenseCommand(prediEntry);
        assertParseSuccess(parser, FIND_BY_DESCRIPTION, expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n n/pgp \n \t cotton  \t", expectedFindCommand);
    }

    @Test
    public void parse_multipleArgs_returnsFindCommand() {
        EntryContainsDescriptionPredicate descriptionPredicate =
                new EntryContainsDescriptionPredicate(Arrays.asList("pgp", "cotton"));
        EntryContainsAmountPredicate amountPredicate =
                new EntryContainsAmountPredicate(AMOUNT);
        List<Predicate<Entry>> prediEntry = Arrays.asList(descriptionPredicate, amountPredicate);

        // no leading and trailing whitespaces
        FindExpenseCommand expectedFindCommand =
                new FindExpenseCommand(prediEntry);
        assertParseSuccess(parser, FIND_BY_DESCRIPTION + FIND_BY_AMOUNT, expectedFindCommand);
    }
}
