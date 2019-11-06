package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_IRRELEVANT_PREFIXES;
import static seedu.address.logic.commands.CommandTestUtil.CALLER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.DISTRICT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DISTRICT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALLER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DISTRICT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISTRICT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.IncidentBuilder.DEFAULT_DISTRICT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ENTITY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ENTITY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditIncidentCommand;
import seedu.address.logic.commands.EditIncidentCommand.EditIncident;
import seedu.address.model.incident.CallerNumber;
import seedu.address.model.vehicle.District;
import seedu.address.testutil.EditIncidentBuilder;

public class EditIncidentCommandParserTest {

    private static final String INVALID_PREFIX_DESC = " qwe/hello";

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditIncidentCommand.MESSAGE_USAGE);

    private EditIncidentCommandParser parser = new EditIncidentCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, EditIncidentCommand.COMMAND_WORD, (MESSAGE_INVALID_INDEX + "\n"
                + MESSAGE_INVALID_FORMAT));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, EditIncidentCommand.COMMAND_WORD + "-5",
                MESSAGE_INVALID_INDEX + "\n" + MESSAGE_INVALID_FORMAT);

        //zero index
        assertParseFailure(parser, EditIncidentCommand.COMMAND_WORD + "0",
                MESSAGE_INVALID_INDEX + "\n" + MESSAGE_INVALID_FORMAT);

        //invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string",
                MESSAGE_INVALID_INDEX + "\n" + MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string",
                 MESSAGE_IRRELEVANT_PREFIXES + "\n" + MESSAGE_INVALID_FORMAT);
    }


    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_ENTITY;

        String userInput = targetIndex.getOneBased() + DISTRICT_DESC + CALLER_DESC + DESCRIPTION_DESC;

        EditIncident editor = new EditIncidentBuilder().withDistrict(VALID_DISTRICT)
                .withCaller(VALID_CALLER)
                .withDesc(VALID_DESCRIPTION).build();

        EditIncidentCommand expectedCommand = new EditIncidentCommand(targetIndex, editor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_ENTITY;
        String userInput = targetIndex.getOneBased() + DISTRICT_DESC + DESCRIPTION_DESC;

        EditIncident editor = new EditIncidentBuilder().withDistrict(VALID_DISTRICT)
                .withDesc(VALID_DESCRIPTION).build();

        EditIncidentCommand expectedCommand = new EditIncidentCommand(targetIndex, editor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        //District
        Index targetIndex = INDEX_SECOND_ENTITY;
        String userInput = targetIndex.getOneBased() + DISTRICT_DESC;
        EditIncident editor = new EditIncidentBuilder().withDistrict(VALID_DISTRICT).build();
        EditIncidentCommand expectedCommand = new EditIncidentCommand(targetIndex, editor);
        assertParseSuccess(parser, userInput, expectedCommand);

        //CallerNumber
        userInput = targetIndex.getOneBased() + CALLER_DESC;
        editor = new EditIncidentBuilder().withCaller(VALID_CALLER).build();
        expectedCommand = new EditIncidentCommand(targetIndex, editor);
        assertParseSuccess(parser, userInput, expectedCommand);

        //Description
        userInput = targetIndex.getOneBased() + DESCRIPTION_DESC;
        editor = new EditIncidentBuilder().withDesc(VALID_DESCRIPTION).build();
        expectedCommand = new EditIncidentCommand(targetIndex, editor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidPrefix_failure() {
        //invalid prefix behind a valid prefix
        assertParseFailure(parser, "1" + DISTRICT_DESC + INVALID_PREFIX_DESC, MESSAGE_IRRELEVANT_PREFIXES);

        //invalid prefix as the first prefix: error thrown when parsing index
        assertParseFailure(parser, "1" + INVALID_PREFIX_DESC, MESSAGE_IRRELEVANT_PREFIXES
                + "\n" + MESSAGE_INVALID_FORMAT);

        //invalid prefix as first prefix followed by valid prefix: error thrown when parsing index
        assertParseFailure(parser, "1" + INVALID_PREFIX_DESC + DISTRICT_DESC, MESSAGE_IRRELEVANT_PREFIXES
                + "\n" + MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid district
        assertParseFailure(parser, " 1" + INVALID_DISTRICT_DESC, District.MESSAGE_CONSTRAINTS);

        // invalid CallerNumber
        assertParseFailure(parser, " 1" + INVALID_PHONE_DESC, CallerNumber.MESSAGE_CONSTRAINTS);

        //valid district followed by invalid phone
        assertParseFailure(parser, "1" + DISTRICT_DESC + INVALID_PHONE_DESC, CallerNumber.MESSAGE_CONSTRAINTS);

        // invalid phone followed by valid district
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + " " + PREFIX_DISTRICT + DEFAULT_DISTRICT,
                CallerNumber.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + INVALID_DISTRICT_DESC,
                CallerNumber.MESSAGE_CONSTRAINTS);
    }

}
