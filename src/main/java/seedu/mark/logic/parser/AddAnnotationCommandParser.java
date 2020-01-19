package seedu.mark.logic.parser;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.commands.AddAnnotationCommand.MESSAGE_GENERAL_MUST_HAVE_NOTE;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_HIGHLIGHT;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_PARAGRAPH;

import seedu.mark.commons.core.index.Index;
import seedu.mark.commons.exceptions.IllegalValueException;
import seedu.mark.logic.commands.AddAnnotationCommand;
import seedu.mark.logic.parser.exceptions.ParseException;
import seedu.mark.model.annotation.AnnotationNote;
import seedu.mark.model.annotation.Highlight;
import seedu.mark.model.annotation.ParagraphIdentifier;

/**
 * Parses input arguments and creates new AddAnnotationCommand object.
 */
public class AddAnnotationCommandParser implements Parser<AddAnnotationCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddAnnotationCommand
     * and returns an AddAnnotationCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAnnotationCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PARAGRAPH, PREFIX_HIGHLIGHT, PREFIX_NOTE);

        Index index = parseIndex_handleException(argMultimap.getPreamble());
        handleAbsentParagraph(argMultimap);

        Highlight highlight = parseHighlight(argMultimap);
        AnnotationNote note = parseAnnotationNote(argMultimap);

        if (isAddGeneralNote(argMultimap.getValue(PREFIX_PARAGRAPH).get())) {
            if (note == null) {
                throw new ParseException(MESSAGE_GENERAL_MUST_HAVE_NOTE);
            }
            return new AddAnnotationCommand(index, highlight, note);
        }

        ParagraphIdentifier pid = ParserUtil.parseParagraphIdentifier(argMultimap.getValue(PREFIX_PARAGRAPH).get());


        return new AddAnnotationCommand(index, pid, note, highlight);
    }

    /**
     * Parses an index from input arguments and handles any exceptions.
     * @param preamble Input argument that is not flagged
     * @return An index
     * @throws ParseException if index argument is of an invalid format.
     */
    private Index parseIndex_handleException(String preamble) throws ParseException {
        try {
            return ParserUtil.parseIndex(preamble);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAnnotationCommand.MESSAGE_USAGE), pe);
        }
    }

    private void handleAbsentParagraph(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getValue(PREFIX_PARAGRAPH).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAnnotationCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses a highlight from input arguments.
     *
     * @param argMultimap The argument map that stores all input arguments
     * @return A highlight that is yellow or has the user-specified colour if any.
     * @throws ParseException if highlight colour input is invalid.
     */
    private Highlight parseHighlight(ArgumentMultimap argMultimap) throws ParseException {
        Highlight highlight = Highlight.YELLOW;
        if (argMultimap.getValue(PREFIX_HIGHLIGHT).isPresent()) {
            highlight = Highlight.strToHighlight(argMultimap.getValue(PREFIX_HIGHLIGHT).get());
        }
        return highlight;
    }

    /**
     * Parses an annotation note from input arguments.
     * @param argMultimap The argument map that stores all input arguments
     * @return An annotation note if any
     * @throws ParseException if note content is invalid.
     */
    private AnnotationNote parseAnnotationNote(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getValue(PREFIX_NOTE).isPresent()) {
            return null;
        }

        try {
            return AnnotationNote.makeNote(argMultimap.getValue(PREFIX_NOTE).get());
        } catch (IllegalValueException e) {
            throw new ParseException(e.getMessage());
        }
    }


    private boolean isAddGeneralNote(String arg) {
        return arg.trim().equalsIgnoreCase("null");
    }

}
