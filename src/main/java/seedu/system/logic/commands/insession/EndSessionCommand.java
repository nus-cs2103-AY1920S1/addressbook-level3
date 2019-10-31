package seedu.system.logic.commands.insession;

import static java.util.Objects.requireNonNull;

import seedu.system.logic.commands.Command;
import seedu.system.logic.commands.CommandResult;
import seedu.system.logic.commands.exceptions.CommandException;
import seedu.system.logic.parser.exceptions.ParseException;
import seedu.system.model.Model;
import seedu.system.model.competition.Competition;

/**
 * Ends the session, and goes out of session.
 */
public class EndSessionCommand extends Command {

    public static final String COMMAND_WORD = "endSession";
    public static final String MESSAGE_SUCCESS = " session has ended.";

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException, ParseException {
        requireNonNull(model);
        Competition compInSession = model.getOngoingCompetition();
        model.endSession();
        return new CommandResult(compInSession + MESSAGE_SUCCESS);
    }
}
