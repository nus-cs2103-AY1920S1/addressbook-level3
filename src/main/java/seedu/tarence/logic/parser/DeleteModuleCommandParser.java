package seedu.tarence.logic.parser;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;

import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.DeleteModuleCommand;
import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.module.ModCode;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteModuleCommandParser extends CommandParser<DeleteModuleCommand> {

    // TODO: implement optionalArgs
    private static final OptionalArgument[] optionalArgs = {};

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteModuleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                OptionalArgumentTokenizer.tokenize(args, optionalArgs,
                        PREFIX_MODULE);

        try {
            if (areAllPrefixesAbsent(argMultimap, PREFIX_MODULE)) {
                Index index = ParserUtil.parseIndex(args);
                return new DeleteModuleCommand(index);
            } else {
                ModCode modCode = ParserUtil.parseModCode(argMultimap.getValue(PREFIX_MODULE).get());
                return new DeleteModuleCommand(modCode);
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteModuleCommand.MESSAGE_USAGE), pe);
        }
    }
}
