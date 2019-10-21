package budgetbuddy.logic.parser.commandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static budgetbuddy.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static budgetbuddy.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static budgetbuddy.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static budgetbuddy.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static budgetbuddy.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static budgetbuddy.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static budgetbuddy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static budgetbuddy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static budgetbuddy.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import budgetbuddy.logic.commands.AddCommand;
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.person.Person;
import budgetbuddy.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB,
                new AddCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
