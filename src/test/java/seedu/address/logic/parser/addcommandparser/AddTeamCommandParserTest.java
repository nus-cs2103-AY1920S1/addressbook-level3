package seedu.address.logic.parser.addcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LOCATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PROJECT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_ALFRED;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_BRUCE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ALFRED;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BRUCE;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PROJECT_NAME_DESC_ALFRED;
import static seedu.address.logic.commands.CommandTestUtil.PROJECT_NAME_DESC_BRUCE;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_ALFRED;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_BRUCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_BRUCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BRUCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_NAME_BRUCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_BRUCE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalTeams.BRUCE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.addcommand.AddTeamCommand;
import seedu.address.model.entity.Location;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.SubjectName;
import seedu.address.model.entity.Team;
import seedu.address.testutil.TeamBuilder;

class AddTeamCommandParserTest {

    private AddTeamCommandParser parser = new AddTeamCommandParser();

    @Test
    void parse_allFieldsPresent_success() {
        Team expectedTeam = new TeamBuilder(BRUCE).withScore(0).build();
        Team expectedTeam2 = new TeamBuilder(BRUCE).withScore(0).withId(2).build();
        Team expectedTeam3 = new TeamBuilder(BRUCE).withScore(0).withId(3).build();
        Team expectedTeam4 = new TeamBuilder(BRUCE).withScore(0).withId(4).build();
        Team expectedTeam5 = new TeamBuilder(BRUCE).withScore(0).withId(5).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BRUCE + LOCATION_DESC_BRUCE
                + PROJECT_NAME_DESC_BRUCE + SUBJECT_DESC_BRUCE,
                new AddTeamCommand(expectedTeam));

        // multiple names - last name accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_ALFRED + NAME_DESC_BRUCE
                + LOCATION_DESC_BRUCE + PROJECT_NAME_DESC_BRUCE + SUBJECT_DESC_BRUCE,
                new AddTeamCommand(expectedTeam2));

        // multiple locations - last location accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + LOCATION_DESC_ALFRED + NAME_DESC_BRUCE
                + LOCATION_DESC_BRUCE + PROJECT_NAME_DESC_BRUCE + SUBJECT_DESC_BRUCE,
                new AddTeamCommand(expectedTeam3));

        // multiple project names - last project name accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PROJECT_NAME_DESC_ALFRED + NAME_DESC_BRUCE
                + LOCATION_DESC_BRUCE + PROJECT_NAME_DESC_BRUCE + SUBJECT_DESC_BRUCE,
                new AddTeamCommand(expectedTeam4));

        // multiple subjects - last subject accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + SUBJECT_DESC_ALFRED + NAME_DESC_BRUCE
                + LOCATION_DESC_BRUCE + PROJECT_NAME_DESC_BRUCE + SUBJECT_DESC_BRUCE,
                new AddTeamCommand(expectedTeam5));
    }

    @Test
    void parse_compulsoryFieldsMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTeamCommand.MESSAGE_USAGE);

        // Missing name prefix
        assertParseFailure(parser, LOCATION_DESC_BRUCE + PROJECT_NAME_DESC_BRUCE
                + SUBJECT_DESC_BRUCE + VALID_NAME_BRUCE, expectedMessage);

        // Missing location prefix
        assertParseFailure(parser, NAME_DESC_BRUCE + VALID_LOCATION_BRUCE
                + PROJECT_NAME_DESC_BRUCE + SUBJECT_DESC_BRUCE, expectedMessage);

        // Missing project name prefix
        assertParseFailure(parser, NAME_DESC_BRUCE + VALID_PROJECT_NAME_BRUCE
                + LOCATION_DESC_BRUCE + SUBJECT_DESC_BRUCE, expectedMessage);

        // Missing subject prefix
        assertParseFailure(parser, NAME_DESC_BRUCE + VALID_SUBJECT_BRUCE
                + LOCATION_DESC_BRUCE + PROJECT_NAME_DESC_BRUCE, expectedMessage);

        // Missing all prefixes
        assertParseFailure(parser, VALID_NAME_BRUCE + VALID_PROJECT_NAME_BRUCE
                + VALID_LOCATION_BRUCE + VALID_SUBJECT_BRUCE, expectedMessage);

    }

    @Test
    void parse_invalidValue_failure() {
        // Invalid name
        assertParseFailure(parser, LOCATION_DESC_BRUCE + PROJECT_NAME_DESC_BRUCE
                + SUBJECT_DESC_BRUCE + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        // Invalid location
        assertParseFailure(parser, NAME_DESC_BRUCE + INVALID_LOCATION_DESC
                + PROJECT_NAME_DESC_BRUCE + SUBJECT_DESC_BRUCE,
                Location.MESSAGE_CONSTRAINTS_INVALID_TABLE_NUMBER);

        // Invalid project name
        assertParseFailure(parser, NAME_DESC_BRUCE + INVALID_PROJECT_NAME_DESC
                + LOCATION_DESC_BRUCE + SUBJECT_DESC_BRUCE, Name.MESSAGE_CONSTRAINTS);

        // Missing subject
        assertParseFailure(parser, NAME_DESC_BRUCE + INVALID_SUBJECT_DESC
                + LOCATION_DESC_BRUCE + PROJECT_NAME_DESC_BRUCE,
                SubjectName.MESSAGE_CONSTRAINTS);

        // Two invalid fields - first one reported
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_LOCATION_DESC
                + PROJECT_NAME_DESC_BRUCE + SUBJECT_DESC_BRUCE, Name.MESSAGE_CONSTRAINTS);

        // Non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BRUCE
                + LOCATION_DESC_BRUCE + PROJECT_NAME_DESC_BRUCE + SUBJECT_DESC_BRUCE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTeamCommand.MESSAGE_USAGE));
    }


}
