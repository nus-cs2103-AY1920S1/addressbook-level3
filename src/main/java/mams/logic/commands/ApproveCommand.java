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
 * Approves a appeal in mams.
 */
public class ApproveCommand extends Command {

    public static final String COMMAND_WORD = "approve";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": approves the appeal selected "
            + "by the index number used in the displayed appeal list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REASON + "[REASON]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REASON + "module quota exceeded.";

    public static final String MESSAGE_APPROVE_APPEAL_SUCCESS = "Approved appeal: %1$s";
    public static final String MESSAGE_APPROVE_UNSUCCESSFUL = "At least one field to edit must be provided.";
    public static final String MESSAGE_APPEAL_ALREADY_APPROVED = "The appeal was already resolved";
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Reason: %2$s";

    private final Index index;
    private final String reason;

    public ApproveCommand(Index index, String reason) {
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

        Appeal approvedAppeal;

        Appeal appealToApprove = lastShownList.get(index.getZeroBased());
        if (appealToApprove.isResolved() == false) {
            approvedAppeal = new Appeal(appealToApprove.getAppealId(),
                    appealToApprove.getAppealType(),
                    appealToApprove.getStudentId(),
                    appealToApprove.getAcademicYear(),
                    appealToApprove.getStudentWorkload(),
                    appealToApprove.getAppealDescription(),
                    appealToApprove.getPreviousModule(),
                    appealToApprove.getNewModule(),
                    appealToApprove.getModule_to_add(),
                    appealToApprove.getModule_to_drop(),
                    true,
                    "APPROVED",
                    reason);
            model.setAppeal(appealToApprove, approvedAppeal);
            model.updateFilteredAppealList(Model.PREDICATE_SHOW_ALL_APPEALS);
            return new CommandResult(generateSuccessMessage(approvedAppeal));
        } else {
            return new CommandResult(MESSAGE_APPEAL_ALREADY_APPROVED);
        }

    }

    private String generateSuccessMessage(Appeal appealToApprove) {
        return "Approved " + appealToApprove.getAppealId();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ApproveCommand)) {
            return false;
        }

        // state check
        ApproveCommand e = (ApproveCommand) other;
        return index.equals(e.index)
                && reason.equals(e.reason);
    }
}
