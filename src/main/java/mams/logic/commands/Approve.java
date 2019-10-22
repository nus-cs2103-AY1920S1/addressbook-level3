package mams.logic.commands;

import static mams.logic.parser.CliSyntax.PREFIX_MASS_RESOLVE;
import static mams.logic.parser.CliSyntax.PREFIX_REASON;

/**
 * Approve parent class for approve commands
 */
public abstract class Approve extends ResolveCommand {

    public static final String MESSAGE_USAGE_APPROVE = COMMAND_WORD_APPROVE_APPEAL
            + ": approves individual or multiple appeals\n"
            + "To approve individually:\n "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REASON + "[REASON]\n"
            + "Example: " + COMMAND_WORD_APPROVE_APPEAL + " 1 "
            + PREFIX_REASON + "module quota exceeded."
            + "To approve multiple appeals:\n"
            + PREFIX_MASS_RESOLVE + "[APPEALIDS.....]"
            + "Example: " + COMMAND_WORD_APPROVE_APPEAL + "C000001 C000012 C000007 C000020";
}
