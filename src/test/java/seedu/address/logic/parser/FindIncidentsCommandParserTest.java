package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.SEARCH_PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindIncidentsCommand;
import seedu.address.model.incident.Description;
import seedu.address.model.incident.DescriptionKeywordsPredicate;
import seedu.address.model.incident.Incident;

public class FindIncidentsCommandParserTest {

    private FindIncidentsCommandParser parser = new FindIncidentsCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindIncidentsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        List<Predicate<Incident>> predicateArr = new ArrayList<>();
        List<String> descriptionArr = new ArrayList<>();
        descriptionArr.add(new Description("test").toString());
        predicateArr.add(new DescriptionKeywordsPredicate(descriptionArr));

        // no leading and trailing whitespaces
        FindIncidentsCommand expectedSearchIncidentsCommand =
                new FindIncidentsCommand(predicateArr);
        assertParseSuccess(parser, " " + SEARCH_PREFIX_DESCRIPTION + "test", expectedSearchIncidentsCommand);
    }

}
