package seedu.address.logic.commands.visit;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.VisitUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.MutatorCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Cancel the ongoing visit (if any) and erase data related to the visit.
 */
public class CancelOngoingVisitCommand extends Command implements MutatorCommand {

    public static final String COMMAND_WORD = "visit-cancel";
    public static final String MESSAGE_SUCCESS = "Cancelled the current visit!";
    public static final String MESSAGE_NO_ONGOING_VISIT = "There is no ongoing visit to cancel.";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        VisitUtil.getOngoingVisitIfExists(model, MESSAGE_NO_ONGOING_VISIT);
        model.cancelOngoingVisit();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
