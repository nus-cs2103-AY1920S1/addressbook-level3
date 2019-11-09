package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.category.Category;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.TransactionPredicate;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // no leading and trailing whitespaces
        Optional<Set<Category>> categories = Optional.of(new HashSet<>(Arrays.asList(
            new Category("Alice"), new Category("Bob"))));
        Optional<Integer> month = Optional.of(11);
        Optional<Integer> year = Optional.of(2019);
        Optional<Description> description = Optional.of(new Description("KFC"));
        FilterCommand expectedFilterCommand =
            new FilterCommand(new TransactionPredicate(categories,
                month, year, description));
        assertParseSuccess(parser, " c/Alice c/Bob m/11 y/2019 n/KFC", expectedFilterCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n c/Alice m/11 y/2019 n/KFC \n \t c/Bob  \t", expectedFilterCommand);
    }

}
