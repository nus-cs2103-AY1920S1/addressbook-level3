package mams.logic.commands;

import static mams.logic.parser.CliSyntax.PREFIX_APPEAL;
import static mams.logic.parser.CliSyntax.PREFIX_MASS_RESOLVE;
import static mams.logic.parser.CliSyntax.PREFIX_REASON;

/**
 * Reject parent class for reject commands
 */
public abstract class Reject extends ResolveCommand {

    public static final String MESSAGE_USAGE_REJECT = COMMAND_WORD_REJECT_APPEAL
            + ": rejects individual or multiple appeals\n"
            + "To reject individually:\n"
            + "Parameters: KEYWORD " + PREFIX_APPEAL + "INDEX ["
            + PREFIX_REASON + "REASON]\n"
            + "Example: " + COMMAND_WORD_REJECT_APPEAL + " " + PREFIX_APPEAL + "1 "
            + PREFIX_REASON + "module quota exceeded.\n"
            + "To reject multiple:\n"
            + "Parameters: KEYWORDS " + PREFIX_MASS_RESOLVE + "[APPEALID] [APPEALID] ...\n"
            + "Example: " + COMMAND_WORD_REJECT_APPEAL + " " + PREFIX_MASS_RESOLVE + "C000001 C000012 C000007 C000020";
}
