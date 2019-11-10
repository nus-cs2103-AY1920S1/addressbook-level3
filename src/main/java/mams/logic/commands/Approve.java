package mams.logic.commands;

import static mams.logic.parser.CliSyntax.PREFIX_APPEAL;
import static mams.logic.parser.CliSyntax.PREFIX_MASS_RESOLVE;
import static mams.logic.parser.CliSyntax.PREFIX_REASON;


/**
 * Approve parent class for approve commands
 */
public abstract class Approve extends ResolveCommand {

    public static final String MESSAGE_USAGE_APPROVE = COMMAND_WORD_APPROVE_APPEAL
            + ": approves individual or multiple appeals\n"
            + "To approve individually:\n"
            + "Parameters: KEYWORD " + PREFIX_APPEAL + "INDEX ["
            + PREFIX_REASON + "REASON]\n"
            + "Example: " + COMMAND_WORD_APPROVE_APPEAL + " " + PREFIX_APPEAL + "1 "
            + PREFIX_REASON + "module quota exceeded.\n"
            + "To approve multiple:\n"
            + "Parameters: KEYWORD " + PREFIX_MASS_RESOLVE + "[APPEALID] [APPEALID] ...\n"
            + "Example: " + COMMAND_WORD_APPROVE_APPEAL + " " + PREFIX_MASS_RESOLVE + "C000001 C000012 C000007 C000020";

    public static final String MESSAGE_APPROVE_FAIL = "Not able to approve appeal %s.";
}
