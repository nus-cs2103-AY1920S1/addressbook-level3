package mams.logic.commands;

import static mams.logic.parser.CliSyntax.PREFIX_REASON;

public abstract class ResolveCommand  extends Command {

    public static final String COMMAND_WORD_APPROVE_APPEAL = "approve";
    public static final String COMMAND_WORD_REJECT_APPEAL = "reject";
    public static final String MESSAGE_USAGE_APPROVE = COMMAND_WORD_APPROVE_APPEAL + ": approves the appeal selected "
            + "by the index number used in the displayed appeal list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REASON + "[REASON]\n"
            + "Example: " + COMMAND_WORD_APPROVE_APPEAL + " 1 "
            + PREFIX_REASON + "module quota exceeded.";

    public static final String MESSAGE_USAGE_REJECT = COMMAND_WORD_REJECT_APPEAL + ": rejects the appeal selected "
            + "by the index number used in the displayed appeal list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REASON + "[REASON]\n"
            + "Example: " + COMMAND_WORD_REJECT_APPEAL + " 1 "
            + PREFIX_REASON + "module quota exceeded.";


    public static final String MESSAGE_APPEAL_ALREADY_APPROVED = "The appeal was already resolved";

    public static final String MESSAGE_REJECT_ALREADY_REJECTED = "The appeal was already resolved";

}
