package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_NON_EXISTENT_MENTOR;
import static seedu.address.commons.core.Messages.MESSAGE_NON_EXISTENT_PARTICIPANT;
import static seedu.address.commons.core.Messages.MESSAGE_NON_EXISTENT_TEAM;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Team;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    private String inputString;
    private final Logger logger = LogsCenter.getLogger(Command.class);

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

    public void setCommandInputString(String inputString) {
        this.inputString = inputString;
    }

    public String getCommandInputString() {
        return this.inputString;
    }

    /**
     * Fetches the Team with the specified Id {@code id} from Model {@code model}.
     *
     * @return the team object with the specified Id.
     * @throws CommandException if the team with the specified Id does not exist.
     */
    public Team getTeamFromModel(Model model, Id id) throws CommandException {
        try {
            return model.getTeam(id);
        } catch (AlfredException ae) {
            logger.severe("Team with ID " + id + " not found");
            throw new CommandException(MESSAGE_NON_EXISTENT_TEAM);
        }
    }

    /**
     * Fetches the Mentor with the specified Id {@code id} from Model {@code model}.
     *
     * @return the mentor object with the specified Id.
     * @throws CommandException if the mentor with the specified Id does not exist.
     */
    public Mentor getMentorFromModel(Model model, Id id) throws CommandException {
        try {
            return model.getMentor(id);
        } catch (AlfredException ae) {
            logger.severe("Mentor with ID " + id + " not found");
            throw new CommandException(MESSAGE_NON_EXISTENT_MENTOR);
        }
    }

    /**
     * Fetches the Participant with the specified Id {@code id} from Model {@code model}.
     *
     * @return the participant object with the specified Id.
     * @throws CommandException if the participant with the specified Id does not exist.
     */
    public Participant getParticipantFromModel(Model model, Id id) throws CommandException {
        try {
            return model.getParticipant(id);
        } catch (AlfredException ae) {
            logger.severe("Participant with ID " + id + " not found");
            throw new CommandException(MESSAGE_NON_EXISTENT_PARTICIPANT);
        }
    }
}
