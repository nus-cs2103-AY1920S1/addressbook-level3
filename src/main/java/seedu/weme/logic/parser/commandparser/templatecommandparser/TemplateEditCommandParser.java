package seedu.weme.logic.parser.commandparser.templatecommandparser;

import static java.util.Objects.requireNonNull;
import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_NAME;

import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.templatecommand.TemplateEditCommand;
import seedu.weme.logic.parser.Parser;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.logic.parser.util.ArgumentMultimap;
import seedu.weme.logic.parser.util.ArgumentTokenizer;
import seedu.weme.logic.parser.util.ParserUtil;
import seedu.weme.model.template.Name;

/**
 * Parses input arguments and creates a new TemplateEditCommand object
 */
public class TemplateEditCommandParser implements Parser<TemplateEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TemplateEditCommand
     * and returns an TemplateEditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TemplateEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TemplateEditCommand.MESSAGE_USAGE), pe);
        }

        if (!argMultimap.arePrefixesPresent(PREFIX_NAME) || argMultimap.getValue(PREFIX_NAME).get().isEmpty()) {
            throw new ParseException(TemplateEditCommand.MESSAGE_NOT_EDITED);
        }

        Name editedName = new Name(argMultimap.getValue(PREFIX_NAME).get());
        return new TemplateEditCommand(index, editedName);
    }

}
