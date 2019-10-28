package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddProfilePictureCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.io.File;

import static seedu.address.commons.core.Messages.MESSAGE_FILE_DOES_NOT_EXIST;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_PATH;

public class AddProfilePictureCommandParser implements Parser<AddProfilePictureCommand> {

    public AddProfilePictureCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_FILE_PATH);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultiMap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddProfilePictureCommand.MESSAGE_USAGE), pe);
        }

        if (!argMultiMap.getValue(PREFIX_FILE_PATH).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddProfilePictureCommand.MESSAGE_USAGE));
        }

        File imgFile = new File(argMultiMap.getValue(PREFIX_FILE_PATH).get());

        if (!imgFile.exists()) {
            throw new ParseException(String.format(MESSAGE_FILE_DOES_NOT_EXIST, AddProfilePictureCommand.MESSAGE_USAGE));
        }

        return new AddProfilePictureCommand(index, imgFile);
    }
}
