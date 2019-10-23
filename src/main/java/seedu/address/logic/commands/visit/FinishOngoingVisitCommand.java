package seedu.address.logic.commands.visit;

import static java.util.Objects.requireNonNull;

import java.util.Date;

import seedu.address.commons.util.VisitUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.MutatorCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.datetime.EndDateTime;
import seedu.address.model.visit.Visit;

/**
 * Mark the current on-going visit as finished and put it aside.
 */
public class FinishOngoingVisitCommand extends Command implements MutatorCommand {

    public static final String COMMAND_WORD = "visit-end";
    public static final String MESSAGE_SUCCESS = "Finished current visit!";
    public static final String MESSAGE_NO_ONGOING_VISIT = "There is no ongoing visit to finish.";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Visit ongoingVisit = VisitUtil.getOngoingVisitIfExists(model, MESSAGE_NO_ONGOING_VISIT);
        //Update the EndDateTime in the visit
        Visit finishedVisit = new Visit(ongoingVisit.getRemark(),
                ongoingVisit.getStartDateTime(),
                new EndDateTime(new Date()),
                ongoingVisit.getVisitTasks(),
                ongoingVisit.getPatient());
        model.updateOngoingVisit(finishedVisit);
        //Remove the reference as ongoing visit
        model.unsetOngoingVisit();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
