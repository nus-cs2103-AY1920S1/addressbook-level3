package seedu.address.logic.commands.visit;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.VisitUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.MutatorCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.visit.Visit;

/**
 * Show the current on-going visit.
 */
public class ShowOngoingVisitCommand extends Command implements MutatorCommand {

    public static final String COMMAND_WORD = "visit-now-show";
    public static final String MESSAGE_SUCCESS = "Showed current visit:%1$s. Current patient: %2$s";
    public static final String MESSAGE_NO_ONGOING_VISIT = "There is no ongoing visit to show.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Visit visit = VisitUtil.ensureOngoingVisitExistsAndGet(model, MESSAGE_NO_ONGOING_VISIT);

        //Todo: Display UI for ongoing visit

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                visit, visit.getPatient()));
    }
}
