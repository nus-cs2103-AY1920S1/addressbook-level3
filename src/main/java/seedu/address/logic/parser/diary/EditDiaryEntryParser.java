package seedu.address.logic.parser.diary;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.diary.EditDiaryEntryCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * {@link Parser} that parses user input to return a {@link EditDiaryEntryCommand}.
 */
public class EditDiaryEntryParser implements Parser<EditDiaryEntryCommand> {
    @Override
    public EditDiaryEntryCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_INDEX, PREFIX_DESCRIPTION);

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isEmpty()) {
            throw new ParseException(EditDiaryEntryCommand.MESSAGE_USAGE);
        }
        String description = argMultimap.getValue(PREFIX_DESCRIPTION).get();

        if (argMultimap.getValue(PREFIX_INDEX).isPresent()) {
            try {
                Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
                return new EditDiaryEntryCommand(description, index);
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditDiaryEntryCommand.MESSAGE_USAGE), pe);
            }
        }

        return new EditDiaryEntryCommand(description);
    }
}
