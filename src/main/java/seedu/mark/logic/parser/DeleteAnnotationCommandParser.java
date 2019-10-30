package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_HIGHLIGHT;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_PARAGRAPH;

import seedu.mark.commons.core.index.Index;
import seedu.mark.logic.commands.DeleteAnnotationAllCommand;
import seedu.mark.logic.commands.DeleteAnnotationClearAllCommand;
import seedu.mark.logic.commands.DeleteAnnotationCommand;
import seedu.mark.logic.commands.DeleteAnnotationHighlightCommand;
import seedu.mark.logic.commands.DeleteAnnotationNoteCommand;
import seedu.mark.logic.parser.exceptions.ParseException;
import seedu.mark.model.annotation.ParagraphIdentifier;

/**
 * Parses input arguments and creates new DeleteAnnotationCommand object.
 */
public class DeleteAnnotationCommandParser implements Parser<DeleteAnnotationCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteAnnotationCommand
     * and returns a DeleteAnnotationCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteAnnotationCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PARAGRAPH, PREFIX_HIGHLIGHT, PREFIX_NOTE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAnnotationCommand.MESSAGE_USAGE), pe);
        }


        if (!argMultimap.getValue(PREFIX_PARAGRAPH).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteAnnotationCommand.MESSAGE_USAGE));
        }

        if (isClearAll(argMultimap.getValue(PREFIX_PARAGRAPH).get())) {
            return new DeleteAnnotationClearAllCommand(index);
        }

        ParagraphIdentifier pid = ParserUtil.parseParagraphIdentifier(argMultimap.getValue(PREFIX_PARAGRAPH).get());

        boolean isKeepHighlight = false;
        if (argMultimap.getValue(PREFIX_HIGHLIGHT).isPresent()) {
            isKeepHighlight = ParserUtil.strToBool(argMultimap.getValue(PREFIX_HIGHLIGHT).get());
        }

        boolean isKeepNote = false;
        if (argMultimap.getValue(PREFIX_NOTE).isPresent()) {
            isKeepNote = ParserUtil.strToBool(argMultimap.getValue(PREFIX_NOTE).get());
        }

        DeleteAnnotationCommand cmd;
        if (isKeepHighlight) {
            if (isKeepNote) {
                throw new ParseException(DeleteAnnotationCommand.MESSAGE_NOTHING_TO_DO);
            } else {
                cmd = new DeleteAnnotationNoteCommand(index, pid);
            }
        } else {
            if (isKeepNote) {
                cmd = new DeleteAnnotationHighlightCommand(index, pid);
            } else {
                cmd = new DeleteAnnotationAllCommand(index, pid);
            }
        }

        return cmd;
    }

    private boolean isClearAll(String arg) {
        return arg.trim().equalsIgnoreCase("all");
    }

}
