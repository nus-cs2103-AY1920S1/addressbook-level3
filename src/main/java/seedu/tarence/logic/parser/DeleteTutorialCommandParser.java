package seedu.tarence.logic.parser;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;

import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.DeleteTutorialCommand;
import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.tutorial.TutName;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteTutorialCommandParser extends CommandParser<DeleteTutorialCommand> {

    // TODO: implement optionalArgs
    private static final OptionalArgument[] optionalArgs = {};

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTutorialCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                OptionalArgumentTokenizer.tokenize(args, optionalArgs,
                        PREFIX_MODULE, PREFIX_TUTORIAL_NAME);

        try {
            if (areAllPrefixesAbsent(argMultimap, PREFIX_TUTORIAL_NAME)) {
                Index index = ParserUtil.parseIndex(args);
                return new DeleteTutorialCommand(index);
            } else {
                TutName tutName = ParserUtil.parseTutorialName(argMultimap.getValue(PREFIX_TUTORIAL_NAME).get());
                ModCode modCode;
                if (arePrefixesPresent(argMultimap, PREFIX_MODULE)) {
                    modCode = ParserUtil.parseModCode(argMultimap.getValue(PREFIX_MODULE).get());
                    return new DeleteTutorialCommand(modCode, tutName);
                } else {
                    return new DeleteTutorialCommand(tutName);
                }
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTutorialCommand.MESSAGE_USAGE), pe);
        }
    }
}
