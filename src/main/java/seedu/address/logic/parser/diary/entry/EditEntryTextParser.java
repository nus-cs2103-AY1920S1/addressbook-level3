package seedu.address.logic.parser.diary.entry;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_FIELDS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.diary.entry.EditEntryTextCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * {@link Parser} that parses user input to return a {@link EditEntryTextCommand}.
 */
public class EditEntryTextParser implements Parser<EditEntryTextCommand> {
    @Override
    public EditEntryTextCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_INDEX, PREFIX_DESCRIPTION);

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_MISSING_FIELDS, EditEntryTextCommand.MESSAGE_USAGE));
        }
        String description = argMultimap.getValue(PREFIX_DESCRIPTION).get();

        if (argMultimap.getValue(PREFIX_INDEX).isPresent()) {
            try {
                Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
                return new EditEntryTextCommand(description, index);
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEntryTextCommand.MESSAGE_USAGE), pe);
            }
        }

        return new EditEntryTextCommand(description);
    }
}
