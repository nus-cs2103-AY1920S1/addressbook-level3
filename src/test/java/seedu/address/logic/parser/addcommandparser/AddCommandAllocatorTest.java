package seedu.address.logic.parser.addcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_BRUCE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BRUCE;
import static seedu.address.logic.commands.CommandTestUtil.ORGANIZATION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PROJECT_NAME_DESC_BRUCE;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_BRUCE;
import static seedu.address.logic.parser.CommandAllocatorTestUtil.assertAllocatorFailure;
import static seedu.address.logic.parser.CommandAllocatorTestUtil.assertAllocatorSuccess;
import static seedu.address.testutil.TypicalMentors.BOB;
import static seedu.address.testutil.TypicalTeams.BRUCE;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.addcommand.AddCommand;
import seedu.address.logic.commands.addcommand.AddMentorCommand;
import seedu.address.logic.commands.addcommand.AddParticipantCommand;
import seedu.address.logic.commands.addcommand.AddTeamCommand;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Team;
import seedu.address.testutil.MentorBuilder;
import seedu.address.testutil.ParticipantBuilder;
import seedu.address.testutil.TeamBuilder;
import seedu.address.testutil.TypicalParticipants;

class AddCommandAllocatorTest {

    private AddCommandAllocator addCommandAllocator = new AddCommandAllocator();

    @Disabled
    @Test
    void allocate_correctUserInput_success() {
        Mentor expectedMentor = new MentorBuilder(BOB).build();
        Participant expectedParticipant = new ParticipantBuilder(TypicalParticipants.BOB).build();
        Team expectedTeam = new TeamBuilder(BRUCE).withScore(0).build();

        assertAllocatorSuccess(addCommandAllocator, "mentor " + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + SUBJECT_DESC_BOB + ORGANIZATION_DESC_BOB, new AddMentorCommand(expectedMentor));

        assertAllocatorSuccess(addCommandAllocator, "participant " + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB, new AddParticipantCommand(expectedParticipant));

        assertAllocatorSuccess(addCommandAllocator, "team " + NAME_DESC_BRUCE + LOCATION_DESC_BRUCE
                + PROJECT_NAME_DESC_BRUCE + SUBJECT_DESC_BRUCE,
                new AddTeamCommand(expectedTeam));
    }


    @Test
    void allocate_missingUserArgument_failure() {
        // Entity name not mentioned
        assertAllocatorFailure(addCommandAllocator, NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + SUBJECT_DESC_BOB + ORGANIZATION_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // Entity description fields not mentioned
        assertAllocatorFailure(addCommandAllocator, "team",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTeamCommand.MESSAGE_USAGE));

        // User input is empty - entity nor its fields mentioned
        assertAllocatorFailure(addCommandAllocator, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

    }
}
