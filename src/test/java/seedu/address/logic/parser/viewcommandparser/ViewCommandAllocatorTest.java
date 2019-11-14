package seedu.address.logic.parser.viewcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandAllocatorTestUtil.assertAllocatorFailure;
import static seedu.address.logic.parser.CommandAllocatorTestUtil.assertAllocatorSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.viewcommand.ViewCommand;
import seedu.address.logic.commands.viewcommand.ViewMentorCommand;
import seedu.address.logic.commands.viewcommand.ViewParticipantCommand;
import seedu.address.logic.commands.viewcommand.ViewTeamCommand;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.PrefixType;

class ViewCommandAllocatorTest {

    private Id mentorId = new Id(PrefixType.M, 1);
    private Id participantId = new Id(PrefixType.P, 2);
    private Id teamId = new Id(PrefixType.T, 2);
    private ViewCommandAllocator viewCommandAllocator = new ViewCommandAllocator();

    @Test
    void allocate_correctUserInput_success() {
        assertAllocatorSuccess(viewCommandAllocator, "mentor M-1", new ViewMentorCommand(mentorId));
        assertAllocatorSuccess(viewCommandAllocator, "participant P-2",
                new ViewParticipantCommand(participantId));
        assertAllocatorSuccess(viewCommandAllocator, "team T-2", new ViewTeamCommand(teamId));
    }

    @Test
    void allocate_missingUserArgument_failure() {
        // Entity name not mentioned - M-1 is the ID inputted by user
        assertAllocatorFailure(viewCommandAllocator, "M-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));

        // Entity description fields not mentioned - Team is the entity but ID isn't mentioned
        assertAllocatorFailure(viewCommandAllocator, "team",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewTeamCommand.MESSAGE_USAGE));

        // User input is empty - entity nor its fields mentioned
        assertAllocatorFailure(viewCommandAllocator, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }
}
