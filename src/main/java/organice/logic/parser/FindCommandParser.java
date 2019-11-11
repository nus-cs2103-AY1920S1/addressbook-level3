package organice.logic.parser;

import static organice.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static organice.commons.util.PersonAttributeCheckUtil.checkValidityAttributes;
import static organice.logic.parser.CliSyntax.PREFIX_AGE;
import static organice.logic.parser.CliSyntax.PREFIX_BLOOD_TYPE;
import static organice.logic.parser.CliSyntax.PREFIX_DOCTOR_IN_CHARGE;
import static organice.logic.parser.CliSyntax.PREFIX_NAME;
import static organice.logic.parser.CliSyntax.PREFIX_NRIC;
import static organice.logic.parser.CliSyntax.PREFIX_ORGAN;
import static organice.logic.parser.CliSyntax.PREFIX_ORGAN_EXPIRY_DATE;
import static organice.logic.parser.CliSyntax.PREFIX_PHONE;
import static organice.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static organice.logic.parser.CliSyntax.PREFIX_TISSUE_TYPE;
import static organice.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.logging.Logger;

import organice.commons.core.LogsCenter;
import organice.logic.commands.FindCommand;
import organice.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExactFindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    private static final Logger logger = LogsCenter.getLogger(FindCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the ExactFindCommand
     * and returns a ExactFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        logger.info("Attempting to parse FindCommand with args: " + args);
        args = args.replaceAll("\n", " ").replaceAll("\\s+", " ");
        String trimmedArgs = args.trim();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_NRIC, PREFIX_PHONE,
                PREFIX_TYPE, PREFIX_AGE, PREFIX_PRIORITY, PREFIX_BLOOD_TYPE, PREFIX_DOCTOR_IN_CHARGE,
                PREFIX_TISSUE_TYPE, PREFIX_ORGAN_EXPIRY_DATE, PREFIX_ORGAN);

        try {
            checkValidityAttributes(argMultimap);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }

        if (trimmedArgs.isEmpty() || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        return new FindCommand(argMultimap);
    }

}
