package mams.logic.commands;

/**
 * Parent class for all resolve appeal commands
 */
public abstract class ResolveCommand extends Command {
    public static final String COMMAND_WORD_APPROVE_APPEAL = "approve";
    public static final String COMMAND_WORD_REJECT_APPEAL = "reject";

    public static final String MESSAGE_APPEAL_ALREADY_APPROVED = "The appeal was already resolved";
    public static final String MESSAGE_REJECT_ALREADY_REJECTED = "The appeal was already resolved";
    public static final String MESSAGE_ONLY_ONE_ITEM_ALLOWED = "Please approve only one index at a time.";
    public static final String MESSAGE_ONLY_ARGUMENT_ALLOWED = "Please enter only one 'mass/' prefix at a time.";

}
