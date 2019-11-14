package seedu.address.logic.parser.deletecommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandAllocatorTestUtil.assertAllocatorFailure;
import static seedu.address.logic.parser.CommandAllocatorTestUtil.assertAllocatorSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.deletecommand.DeleteCommand;
import seedu.address.logic.commands.deletecommand.DeleteMentorCommand;
import seedu.address.logic.commands.deletecommand.DeleteParticipantCommand;
import seedu.address.logic.commands.deletecommand.DeleteTeamCommand;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.PrefixType;

class DeleteCommandAllocatorTest {

    private Id mentorId = new Id(PrefixType.M, 1);
    private Id participantId = new Id(PrefixType.P, 2);
    private Id teamId = new Id(PrefixType.T, 2);
    private DeleteCommandAllocator deleteCommandAllocator = new DeleteCommandAllocator();

    @Test
    void allocate_correctUserInput_success() {
        assertAllocatorSuccess(deleteCommandAllocator, "mentor M-1", new DeleteMentorCommand(mentorId));
        assertAllocatorSuccess(deleteCommandAllocator, "participant P-2",
                new DeleteParticipantCommand(participantId));
        assertAllocatorSuccess(deleteCommandAllocator, "team T-2", new DeleteTeamCommand(teamId));
    }

    @Test
    void allocate_missingUserArgument_failure() {
        // Entity name not mentioned - M-1 is the ID inputted by user
        assertAllocatorFailure(deleteCommandAllocator, "M-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));

        // Entity description fields not mentioned - Team is the entity but ID isn't mentioned
        assertAllocatorFailure(deleteCommandAllocator, "team",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTeamCommand.MESSAGE_USAGE));

        // User input is empty - entity nor its fields mentioned
        assertAllocatorFailure(deleteCommandAllocator, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
