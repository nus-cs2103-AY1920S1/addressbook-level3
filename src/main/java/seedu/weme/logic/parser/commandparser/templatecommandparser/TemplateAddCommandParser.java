package seedu.weme.logic.parser.commandparser.templatecommandparser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_FILEPATH;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_NAME;

import seedu.weme.logic.commands.templatecommand.TemplateAddCommand;
import seedu.weme.logic.parser.Parser;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.logic.parser.util.ArgumentMultimap;
import seedu.weme.logic.parser.util.ArgumentTokenizer;
import seedu.weme.logic.parser.util.ParserUtil;
import seedu.weme.model.path.ImagePath;
import seedu.weme.model.template.Name;
import seedu.weme.model.template.Template;

/**
 * Parses input arguments and creates a new TemplateAddCommand object
 */
public class TemplateAddCommandParser implements Parser<TemplateAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TemplateAddCommand
     * and returns an TemplateAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TemplateAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_FILEPATH);

        if (!argMultimap.arePrefixesPresent(PREFIX_NAME, PREFIX_FILEPATH)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TemplateAddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        ImagePath imagePath = ParserUtil.parseFilePath(argMultimap.getValue(PREFIX_FILEPATH).get());

        Template template = new Template(name, imagePath);

        return new TemplateAddCommand(template);
    }

}

