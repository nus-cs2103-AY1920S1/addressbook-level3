package seedu.exercise.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.logging.Logger;

import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.commands.SelectCommand;
import seedu.exercise.logic.parser.exceptions.ParseException;
import seedu.exercise.ui.ListResourceType;

//@@author weihaw08
/**
 * Parses input arguments and creates a new SelectCommand object.
 */
public class SelectCommandParser implements Parser<SelectCommand> {

    private final Logger logger = LogsCenter.getLogger(SelectCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns a SelectCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY, PREFIX_INDEX);

        if (!argMultimap.arePrefixesPresent(PREFIX_CATEGORY, PREFIX_INDEX)
            || !argMultimap.getPreamble().isEmpty()) {
            logger.info("Not all prefixes for select command are present.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        ListResourceType listResourceType = ParserUtil
            .parseListResourceType(argMultimap.getValue(PREFIX_CATEGORY).get());
        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        return new SelectCommand(index, listResourceType);
    }

}
