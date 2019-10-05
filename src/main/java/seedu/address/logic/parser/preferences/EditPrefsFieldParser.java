package seedu.address.logic.parser.preferences;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.preferences.EditPrefsFieldCommand;
import seedu.address.logic.commands.preferences.EditPrefsFieldCommand.EditPrefsDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_FILE_NOT_EXISTING;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PATH;
import static seedu.address.logic.parser.preferences.PrefsCliSyntax.PREFIX_DATA_FILE_PATH;
import static seedu.address.logic.parser.preferences.PrefsCliSyntax.PREFIX_GUI_LOCK;
import static seedu.address.logic.parser.preferences.PrefsCliSyntax.PREFIX_WINDOW_HEIGHT;
import static seedu.address.logic.parser.preferences.PrefsCliSyntax.PREFIX_WINDOW_WIDTH;
import static seedu.address.logic.parser.preferences.PrefsCliSyntax.PREFIX_WINDOW_XPOS;
import static seedu.address.logic.parser.preferences.PrefsCliSyntax.PREFIX_WINDOW_YPOS;

public class EditPrefsFieldParser implements Parser<EditPrefsFieldCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditPrefsFieldCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EditPrefsFieldCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_WINDOW_HEIGHT,
                        PREFIX_WINDOW_WIDTH,
                        PREFIX_WINDOW_XPOS,
                        PREFIX_WINDOW_YPOS,
                        PREFIX_DATA_FILE_PATH,
                        PREFIX_GUI_LOCK);

        Optional<Index> index;

        try {
            index = Optional.ofNullable(ParserUtil.parseIndex(argMultimap.getPreamble()));
        } catch (ParseException pe) {
            index = Optional.empty();
            //throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            //        EditTripFieldCommand.MESSAGE_USAGE), pe);
        }

        if (!index.isEmpty()) {
            //edit by field specified by index only
            throw new UnsupportedOperationException("Parsing edit trip by index not yet supported.");

        }
        //edit by prefixes
        EditPrefsDescriptor editPrefsDescriptor = new EditPrefsDescriptor();
        setPrefsDescriptor(argMultimap, editPrefsDescriptor);

        if (!editPrefsDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditPrefsFieldCommand.MESSAGE_NOT_EDITED
                    + "\n"
                    + EditPrefsFieldCommand.MESSAGE_USAGE);
        }

        return new EditPrefsFieldCommand(editPrefsDescriptor);
    }

    private void setPrefsDescriptor(ArgumentMultimap argMultimap, EditPrefsDescriptor editPrefsDescriptor) throws ParseException {
        try {
            argMultimap.getValue(PREFIX_WINDOW_HEIGHT)
                    .ifPresent(height -> {
                        double heightVal = Double.parseDouble(height);
                        if (heightVal <= 0) {
                            throw new NumberFormatException();
                        }
                        editPrefsDescriptor.setWindowHeight(heightVal);
                    });
            argMultimap.getValue(PREFIX_WINDOW_WIDTH)
                    .ifPresent(width -> {
                        double widthVal = Double.parseDouble(width);
                        if (widthVal <= 0) {
                            throw new NumberFormatException();
                        }
                        editPrefsDescriptor.setWindowWidth(widthVal);
                    });
            argMultimap.getValue(PREFIX_WINDOW_XPOS)
                    .ifPresent(xPos -> editPrefsDescriptor.setWindowXPos(Integer.parseInt(xPos)));
            argMultimap.getValue(PREFIX_WINDOW_YPOS)
                    .ifPresent(yPos -> editPrefsDescriptor.setWindowYPos(Integer.parseInt(yPos)));

            if (argMultimap.getValue(PREFIX_GUI_LOCK).isPresent()) {
                editPrefsDescriptor.setGuiLocked(
                        ParserUtil.parseBool(argMultimap.getValue(PREFIX_GUI_LOCK).get()));
            }
        } catch (NumberFormatException | ParseException ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPrefsFieldCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_DATA_FILE_PATH).isPresent()) {
            try {
                Path p = Paths.get(argMultimap.getValue(PREFIX_DATA_FILE_PATH).get());
                if (!Files.exists(p)) {
                    throw new ParseException(MESSAGE_FILE_NOT_EXISTING);
                }
            } catch (InvalidPathException ex) {
                throw new ParseException(MESSAGE_INVALID_PATH);
            }
        }
    }
}
