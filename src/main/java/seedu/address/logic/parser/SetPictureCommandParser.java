package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PICTURE;

import java.io.File;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetPictureCommand;
import seedu.address.logic.commands.util.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SetPictureCommand object
 */
public class SetPictureCommandParser implements Parser<SetPictureCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetPictureCommand
     * and returns an SetPictureCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetPictureCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PICTURE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetPictureCommand.MESSAGE_USAGE), pe);
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_PICTURE).isPresent()) {

            File imageTest = new File(argMultimap.getValue(PREFIX_PICTURE).get());

            if (!imageTest.exists()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, "Make sure the picture exists "
                    + "and is in the same directory as TutorAid!"));
            }

            editPersonDescriptor.setPicture(ParserUtil.parsePicture(argMultimap.getValue(PREFIX_PICTURE).get()));
        } else {
            throw new ParseException(SetPictureCommand.MESSAGE_NOT_EDITED);
        }

        return new SetPictureCommand(index, editPersonDescriptor);
    }

}
