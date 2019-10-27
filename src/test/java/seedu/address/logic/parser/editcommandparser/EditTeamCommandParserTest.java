package seedu.address.logic.parser.editcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_LOCATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PROJECT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_ALFRED;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_BRUCE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ALFRED;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PROJECT_NAME_DESC_ALFRED;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_ALFRED;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_ALFRED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_BRUCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALFRED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_NAME_ALFRED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_ALFRED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.editcommand.EditTeamCommand;
import seedu.address.logic.commands.editcommand.EditTeamCommand.EditTeamDescriptor;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Location;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.SubjectName;
import seedu.address.testutil.EditTeamDescriptorBuilder;
import seedu.address.testutil.TypicalIds;

class EditTeamCommandParserTest {


    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTeamCommand.MESSAGE_USAGE);

    private EditTeamCommandParser parser = new EditTeamCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, NAME_DESC_ALFRED, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "T-1", EditTeamCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "T--5" + NAME_DESC_ALFRED, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "T-0" + NAME_DESC_ALFRED, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "T-1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "T-1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "T-1" + INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS); // invalid name

        assertParseFailure(parser, "T-1" + INVALID_SUBJECT_DESC,
                SubjectName.MESSAGE_CONSTRAINTS); // invalid phone

        assertParseFailure(parser, "T-1" + INVALID_PROJECT_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS); // invalid project name

        assertParseFailure(parser, "T-1" + INVALID_LOCATION_DESC,
                Location.MESSAGE_CONSTRAINTS_INVALID_TABLE_NUMBER); // invalid location


        // invalid location followed by valid name
        assertParseFailure(parser, "T-1" + INVALID_LOCATION_DESC + NAME_DESC_ALFRED,
                Location.MESSAGE_CONSTRAINTS_INVALID_TABLE_NUMBER);

        // valid name followed by invalid name.
        assertParseFailure(parser, "T-1" + NAME_DESC_ALFRED + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "T-1" + INVALID_NAME_DESC + INVALID_LOCATION_DESC + INVALID_SUBJECT_DESC
                + INVALID_PROJECT_NAME_DESC, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Id targetId = TypicalIds.ID_FIRST_TEAM;

        String userInput = targetId.toString() + NAME_DESC_ALFRED
                + SUBJECT_DESC_ALFRED + LOCATION_DESC_ALFRED + PROJECT_NAME_DESC_ALFRED;

        EditTeamDescriptor descriptor = new EditTeamDescriptorBuilder().withName(VALID_NAME_ALFRED)
                .withSubject(VALID_SUBJECT_ALFRED).withLocation(VALID_LOCATION_ALFRED)
                .withProjectName(VALID_PROJECT_NAME_ALFRED).build();

        EditTeamCommand expectedCommand = new EditTeamCommand(targetId, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Id targetId = TypicalIds.ID_FIRST_TEAM;

        String userInput = targetId.toString() + NAME_DESC_ALFRED + LOCATION_DESC_ALFRED;

        EditTeamDescriptor descriptor = new EditTeamDescriptorBuilder().withLocation(VALID_LOCATION_ALFRED)
                .withName(VALID_NAME_ALFRED).build();

        EditTeamCommand expectedCommand = new EditTeamCommand(targetId, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Id targetId = TypicalIds.ID_FIRST_TEAM;

        String userInput = targetId.toString() + NAME_DESC_AMY;
        EditTeamDescriptor descriptor = new EditTeamDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditTeamCommand expectedCommand = new EditTeamCommand(targetId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // project name
        userInput = targetId.toString() + " pn/NewName";
        descriptor = new EditTeamDescriptorBuilder().withProjectName("NewName").build();
        expectedCommand = new EditTeamCommand(targetId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // location
        userInput = targetId.toString() + LOCATION_DESC_ALFRED;
        descriptor = new EditTeamDescriptorBuilder().withLocation(VALID_LOCATION_ALFRED).build();
        expectedCommand = new EditTeamCommand(targetId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Subject
        userInput = targetId.toString() + SUBJECT_DESC_AMY;
        descriptor = new EditTeamDescriptorBuilder().withSubject(VALID_SUBJECT_AMY).build();
        expectedCommand = new EditTeamCommand(targetId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Id targetId = TypicalIds.ID_FIRST_TEAM;

        String userInput = targetId.toString() + LOCATION_DESC_ALFRED + LOCATION_DESC_BRUCE + SUBJECT_DESC_AMY
                + SUBJECT_DESC_BOB;

        EditTeamDescriptor descriptor = new EditTeamDescriptorBuilder().withLocation(VALID_LOCATION_BRUCE)
                .withSubject(VALID_SUBJECT_BOB).build();

        EditTeamCommand expectedCommand = new EditTeamCommand(targetId, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        Id targetId = TypicalIds.ID_FIRST_TEAM;
        String userInput = targetId.toString() + INVALID_LOCATION_DESC + LOCATION_DESC_ALFRED;

        EditTeamDescriptor descriptor = new EditTeamDescriptorBuilder().withLocation(VALID_LOCATION_ALFRED).build();

        EditTeamCommand expectedCommand = new EditTeamCommand(targetId, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetId.toString() + NAME_DESC_ALFRED + INVALID_LOCATION_DESC + LOCATION_DESC_ALFRED
                + INVALID_SUBJECT_DESC + SUBJECT_DESC_BOB;

        descriptor = new EditTeamDescriptorBuilder().withName(VALID_NAME_ALFRED).withSubject(VALID_SUBJECT_BOB)
                .withLocation(VALID_LOCATION_ALFRED).build();

        expectedCommand = new EditTeamCommand(targetId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
