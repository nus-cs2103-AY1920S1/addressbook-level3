package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_HIGHLIGHT;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_PARAGRAPH;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_TO_NEW_PARAGRAPH;

import seedu.mark.commons.core.index.Index;
import seedu.mark.commons.exceptions.IllegalValueException;
import seedu.mark.logic.commands.EditAnnotationCommand;
import seedu.mark.logic.parser.exceptions.ParseException;
import seedu.mark.model.annotation.AnnotationNote;
import seedu.mark.model.annotation.Highlight;
import seedu.mark.model.annotation.ParagraphIdentifier;

/**
 * Parses input arguments and creates new EditAnnotationCommand object.
 */
public class EditAnnotationCommandParser implements Parser<EditAnnotationCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditAnnotationCommand
     * and returns an EditAnnotationCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditAnnotationCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PARAGRAPH, PREFIX_HIGHLIGHT, PREFIX_NOTE,
                        PREFIX_TO_NEW_PARAGRAPH);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAnnotationCommand.MESSAGE_USAGE), pe);
        }

        if (!argMultimap.getValue(PREFIX_PARAGRAPH).isPresent()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAnnotationCommand.MESSAGE_USAGE));
        }
        ParagraphIdentifier pid = ParserUtil.parseParagraphIdentifier(argMultimap.getValue(PREFIX_PARAGRAPH).get());

        Highlight highlight = null;
        if (argMultimap.getValue(PREFIX_HIGHLIGHT).isPresent()) {
            highlight = Highlight.strToHighlight(argMultimap.getValue(PREFIX_HIGHLIGHT).get());
        }

        AnnotationNote note = null;
        if (argMultimap.getValue(PREFIX_NOTE).isPresent()) {
            try {
                note = AnnotationNote.makeNote(argMultimap.getValue(PREFIX_NOTE).get());
            } catch (IllegalValueException e) {
                throw new ParseException(e.getMessage());
            }
        }

        ParagraphIdentifier newPid = null;
        if (argMultimap.getValue(PREFIX_TO_NEW_PARAGRAPH).isPresent()) {
            try {
                newPid = ParserUtil.parseParagraphIdentifier(argMultimap.getValue(PREFIX_TO_NEW_PARAGRAPH).get());
            } catch (ParseException e) {
                throw e;
            }
        }

        return new EditAnnotationCommand(index, pid, newPid, note, highlight);
    }
}
