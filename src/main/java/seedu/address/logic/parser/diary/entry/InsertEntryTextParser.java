package seedu.address.logic.parser.diary.entry;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_FIELDS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.diary.entry.InsertEntryTextCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * {@link Parser} that parses user input to return a {@link InsertEntryTextCommand}.
 */
public class InsertEntryTextParser implements Parser<InsertEntryTextCommand> {

    @Override
    public InsertEntryTextCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_INDEX, PREFIX_DESCRIPTION);

        if (!ArgumentMultimap.arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_DESCRIPTION)) {
            throw new ParseException(
                    String.format(MESSAGE_MISSING_FIELDS, InsertEntryTextCommand.MESSAGE_USAGE));
        }
        String description = argMultimap.getValue(PREFIX_DESCRIPTION).get();

        try {
            Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());

            return new InsertEntryTextCommand(description, index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, InsertEntryTextCommand.MESSAGE_USAGE), pe);
        }
    }
}
