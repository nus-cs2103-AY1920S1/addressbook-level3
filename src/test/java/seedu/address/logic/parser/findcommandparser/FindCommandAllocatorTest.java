package seedu.address.logic.parser.findcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandAllocatorTestUtil.assertAllocatorFailure;
import static seedu.address.logic.parser.CommandAllocatorTestUtil.assertAllocatorSuccess;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.findcommand.FindCommand;
import seedu.address.logic.commands.findcommand.FindMentorCommand;
import seedu.address.logic.commands.findcommand.FindParticipantCommand;
import seedu.address.logic.commands.findcommand.FindTeamCommand;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.PrefixType;

public class FindCommandAllocatorTest {
    private Id mentorId = new Id(PrefixType.M, 1);
    private Id participantId = new Id(PrefixType.P, 2);
    private Id teamId = new Id(PrefixType.T, 2);
    private FindCommandAllocator findCommandAllocator = new FindCommandAllocator();

    @Test
    void allocate_correctUserInput_success() {
        assertAllocatorSuccess(findCommandAllocator, "mentor n/Damith",
                new FindMentorCommand(Optional.of("Damith"), Optional.empty(), Optional.empty(), Optional.empty()));
        assertAllocatorSuccess(findCommandAllocator, "participant e/dog@gmail.com",
                new FindParticipantCommand(Optional.empty(), Optional.of("dog@gmail.com"), Optional.empty()));
        assertAllocatorSuccess(findCommandAllocator, "team pn/djfnswdjf",
                new FindTeamCommand(Optional.empty(), Optional.of("djfnswdjf")));
    }

    @Test
    void allocate_missingUserArgument_failure() {
        // Entity name not mentioned - M-1 is the ID inputted by user
        assertAllocatorFailure(findCommandAllocator, "M-1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // Entity description fields not mentioned - Team is the entity but ID isn't mentioned
        assertAllocatorFailure(findCommandAllocator, "team",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTeamCommand.MESSAGE_USAGE));

        // User input is empty - entity nor its fields mentioned
        assertAllocatorFailure(findCommandAllocator, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }
}
