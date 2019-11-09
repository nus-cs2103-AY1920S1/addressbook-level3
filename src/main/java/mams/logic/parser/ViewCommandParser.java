package mams.logic.parser;

import static java.util.Objects.requireNonNull;

import static mams.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static mams.logic.parser.CliSyntax.PREFIX_APPEAL;
import static mams.logic.parser.CliSyntax.PREFIX_MODULE;
import static mams.logic.parser.CliSyntax.PREFIX_STUDENT;

import mams.logic.commands.ViewCommand;
import mams.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a ViewCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public ViewCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_APPEAL, PREFIX_MODULE, PREFIX_STUDENT);

        if (argMultimap.areAllPrefixesAbsent(PREFIX_APPEAL, PREFIX_MODULE, PREFIX_STUDENT)
                || !argMultimap.getPreamble().isBlank()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        try {
            ViewCommand.ViewCommandParameters parameters = new ViewCommand.ViewCommandParameters();
            if (argMultimap.getValue(PREFIX_APPEAL).isPresent()) {
                parameters.setAppealIndex(ParserUtil.parseIndex(argMultimap.getValue(PREFIX_APPEAL).get()));
            }

            if (argMultimap.getValue(PREFIX_MODULE).isPresent()) {
                parameters.setModuleIndex(ParserUtil.parseIndex(argMultimap.getValue(PREFIX_MODULE).get()));
            }

            if (argMultimap.getValue(PREFIX_STUDENT).isPresent()) {
                parameters.setStudentIndex(ParserUtil.parseIndex(argMultimap.getValue(PREFIX_STUDENT).get()));
            }

            // by this point, invalid parameters should have thrown ParseException in the earlier checks
            assert parameters.isAtLeastOneParameterPresent() : "Assertion failed: ViewCommandParameter has no params"
                    + "even after parsing";
            return new ViewCommand(parameters);
        } catch (ParseException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE), e);
        }

    }
}
