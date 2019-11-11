package seedu.address.logic.parser.patients;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.patients.ListPatientCommand;
import seedu.address.model.person.predicates.PersonContainsKeywordPredicate;

public class ListPatientCommandParserTest {

    private ListPatientCommandParser parser = new ListPatientCommandParser();

    @Test
    public void parse_validArgs_returnsListPatientCommand() {
        // no leading and trailing whitespaces
        ListPatientCommand expectedListPatientCommand =
                new ListPatientCommand(new PersonContainsKeywordPredicate("Alice Bob"));
        assertParseSuccess(parser, "Alice Bob", expectedListPatientCommand);

        // multiple whitespaces between keywords
        expectedListPatientCommand = new ListPatientCommand(new PersonContainsKeywordPredicate("Alice \n \t Bob"));
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedListPatientCommand);
    }

}
