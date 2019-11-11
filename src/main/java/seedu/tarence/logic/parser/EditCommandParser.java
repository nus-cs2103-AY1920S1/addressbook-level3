package seedu.tarence.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MATNO;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_NUSID;

import java.util.Optional;

import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.EditCommand;
import seedu.tarence.logic.commands.EditCommand.EditStudentDescriptor;
import seedu.tarence.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_EMAIL, PREFIX_MATNO, PREFIX_NUSID);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditStudentDescriptor editStudentDescriptor = new EditStudentDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editStudentDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editStudentDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_MATNO).isPresent()) {
            editStudentDescriptor.setMatricNum(
                    Optional.of(ParserUtil.parseMatricNum(argMultimap.getValue(PREFIX_MATNO).get())));
        }
        if (argMultimap.getValue(PREFIX_NUSID).isPresent()) {
            editStudentDescriptor.setNusnetId(
                    Optional.of(ParserUtil.parseNusnetId(argMultimap.getValue(PREFIX_NUSID).get())));
        }

        if (!editStudentDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editStudentDescriptor);
    }

}
