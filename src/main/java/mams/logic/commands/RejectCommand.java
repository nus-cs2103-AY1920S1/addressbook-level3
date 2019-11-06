package mams.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import mams.commons.core.Messages;
import mams.commons.core.index.Index;

import mams.logic.commands.exceptions.CommandException;

import mams.logic.history.FilterOnlyCommandHistory;
import mams.model.Model;
import mams.model.appeal.Appeal;

/**
 * Rejects a appeal in mams.
 */
public class RejectCommand extends Reject {

    private final Index index;
    private final String reason;

    public RejectCommand(Index index, String reason) {
        requireNonNull(index, reason);

        this.index = index;
        this.reason = reason;

    }

    @Override
    public CommandResult execute(Model model, FilterOnlyCommandHistory commandHistory) throws CommandException {
        List<Appeal> lastShownList = model.getFilteredAppealList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPEAL_DISPLAYED_INDEX);
        }

        Appeal rejectedAppeal;

        Appeal appealToReject = lastShownList.get(index.getZeroBased());
        if (appealToReject.isResolved() == false) {
            rejectedAppeal = new Appeal(appealToReject.getAppealId(),
                    appealToReject.getAppealType(),
                    appealToReject.getStudentId(),
                    appealToReject.getAcademicYear(),
                    appealToReject.getStudentWorkload(),
                    appealToReject.getAppealDescription(),
                    appealToReject.getPreviousModule(),
                    appealToReject.getNewModule(),
                    appealToReject.getModuleToAdd(),
                    appealToReject.getModuleToDrop(),
                    true,
                    "REJECTED",
                    reason);
            model.setAppeal(appealToReject, rejectedAppeal);
            model.updateFilteredAppealList(Model.PREDICATE_SHOW_ALL_APPEALS);
            return new CommandResult(generateSuccessMessage(appealToReject));
        } else {
            return new CommandResult(MESSAGE_REJECT_ALREADY_REJECTED);
        }

    }

    private String generateSuccessMessage(Appeal appealToReject) {
        return "Rejected " + appealToReject.getAppealId();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RejectCommand)) {
            return false;
        }

        // state check
        RejectCommand e = (RejectCommand) other;
        return index.equals(e.index)
                && reason.equals(e.reason);
    }
}
