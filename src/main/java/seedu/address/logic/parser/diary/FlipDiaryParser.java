package seedu.address.logic.parser.diary;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.diary.FlipDiaryCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

public class FlipDiaryParser implements Parser<FlipDiaryCommand> {
    @Override
    public FlipDiaryCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FlipDiaryCommand.MESSAGE_USAGE), pe);
        }

        return new FlipDiaryCommand(index);
    }
}
