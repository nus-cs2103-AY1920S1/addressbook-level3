package seedu.weme.logic.parser.commandparser.createcommandparser;

import static java.util.Objects.requireNonNull;
import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_COLOR;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_SIZE;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_STYLE;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_TEXT;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_X_COORDINATE;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_Y_COORDINATE;

import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.createcommand.TextEditCommand;
import seedu.weme.logic.commands.createcommand.TextEditCommand.EditMemeTextDescriptor;
import seedu.weme.logic.parser.Parser;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.logic.parser.util.ArgumentMultimap;
import seedu.weme.logic.parser.util.ArgumentTokenizer;
import seedu.weme.logic.parser.util.ParserUtil;
import seedu.weme.model.template.MemeTextStyle;

/**
 * Parses input arguments and creates a new TextEditCommand object
 */
public class TextEditCommandParser implements Parser<TextEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TextEditCommand
     * and returns an TextEditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public TextEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
            PREFIX_TEXT, PREFIX_X_COORDINATE, PREFIX_Y_COORDINATE, PREFIX_COLOR, PREFIX_STYLE, PREFIX_SIZE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TextEditCommand.MESSAGE_USAGE), pe);
        }

        EditMemeTextDescriptor editMemeDescriptor = new EditMemeTextDescriptor();
        if (argMultimap.getValue(PREFIX_TEXT).isPresent()) {
            editMemeDescriptor.setText(argMultimap.getValue(PREFIX_TEXT).get());
        }
        if (argMultimap.getValue(PREFIX_X_COORDINATE).isPresent()) {
            editMemeDescriptor.setX(ParserUtil.parseCoordinate(argMultimap.getValue(PREFIX_X_COORDINATE).get()));
        }
        if (argMultimap.getValue(PREFIX_Y_COORDINATE).isPresent()) {
            editMemeDescriptor.setY(ParserUtil.parseCoordinate(argMultimap.getValue(PREFIX_Y_COORDINATE).get()));
        }
        if (argMultimap.getValue(PREFIX_COLOR).isPresent()) {
            editMemeDescriptor.setColor(ParserUtil.parseMemeTextColor(argMultimap.getValue(PREFIX_COLOR).get()));
        }
        if (argMultimap.getValue(PREFIX_SIZE).isPresent()) {
            editMemeDescriptor.setSize(ParserUtil.parseMemeTextSize(argMultimap.getValue(PREFIX_SIZE).get()));
        }
        if (argMultimap.getValue(PREFIX_STYLE).isPresent()) {
            editMemeDescriptor.setStyle(
                MemeTextStyle.combine(ParserUtil.parseMemeTextStyles(argMultimap.getAllValues(PREFIX_STYLE))));
        }

        if (!editMemeDescriptor.isAnyFieldEdited()) {
            throw new ParseException(TextEditCommand.MESSAGE_NOT_EDITED);
        }

        return new TextEditCommand(index, editMemeDescriptor);
    }

}
