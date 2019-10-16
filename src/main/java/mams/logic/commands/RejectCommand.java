package mams.logic.commands;

import static java.util.Objects.requireNonNull;

import static mams.logic.parser.CliSyntax.PREFIX_REASON;

import java.util.List;

import mams.commons.core.Messages;
import mams.commons.core.index.Index;

import mams.logic.commands.exceptions.CommandException;

import mams.model.Model;
import mams.model.appeal.Appeal;

/**
 * Edits the details of an existing student in MAMS.
 */
public class RejectCommand extends Command {

    public static final String COMMAND_WORD = "reject";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": rejects the appeal selected "
            + "by the index number used in the displayed appeal list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REASON + "[REASON]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REASON + "module quota exceeded.";

    public static final String MESSAGE_REJECT_APPEAL_SUCCESS = "Rejected appeal: %1$s";
    public static final String MESSAGE_REjECT_UNSUCCESSFUL = "At least one field to edit must be provided.";
    public static final String MESSAGE_REJECT_ALREADY_REJECTED = "The appeal was already resolved";
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Reason: %2$s";

    private final Index index;
    private final String reason;

    public RejectCommand(Index index, String reason) {
        requireNonNull(index, reason);

        this.index = index;
        this.reason = reason;

    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
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
                    appealToReject.getModule_to_add(),
                    appealToReject.getModule_to_drop(),
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
