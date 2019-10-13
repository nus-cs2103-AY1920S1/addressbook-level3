package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PICTURE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PICTURE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PICTURE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PICTURE_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Picture;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).build();

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB
                + PICTURE_DESC_BOB, new AddCommand(expectedPerson));

        // multiple picture - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PICTURE_DESC_AMY
                + PICTURE_DESC_BOB, new AddCommand(expectedPerson));

    }


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PICTURE_DESC_BOB,
                expectedMessage);

        // missing picture prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PICTURE_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PICTURE_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PICTURE_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // invalid picture
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PICTURE_DESC, Picture.MESSAGE_CONSTRAINTS);


    }
}
