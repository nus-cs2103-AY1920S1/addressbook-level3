package mams.logic.commands;

import static mams.logic.parser.CliSyntax.PREFIX_MASS_RESOLVE;
import static mams.logic.parser.CliSyntax.PREFIX_REASON;

/**
 * Reject parent class for reject commands
 */
public abstract class Reject extends ResolveCommand {

    public static final String MESSAGE_USAGE_REJECT = COMMAND_WORD_REJECT_APPEAL
            + ": rejects individual or multiple appeals\n"
            + "To reject individually:\n "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REASON + "[REASON]\n"
            + "Example: " + COMMAND_WORD_REJECT_APPEAL + " 1 "
            + PREFIX_REASON + "module quota exceeded."
            + "To reject multiple appeals:\n"
            + PREFIX_MASS_RESOLVE + "[APPEALIDS.....]"
            + "Example: " + COMMAND_WORD_REJECT_APPEAL + "C000001 C000012 C000007 C000020";
}
