package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.AddCheatSheetCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.cheatsheet.CheatSheet;
import seedu.address.model.cheatsheet.Content;
import seedu.address.model.cheatsheet.Title;
import seedu.address.model.note.Note;
import seedu.address.model.note.NoteContainsTagPredicate;
import seedu.address.model.tag.Tag;


/**
 * Parses input arguments and creates a new AddCheatSheetCommand object
 */
public class AddCheatSheetCommandParser implements Parser<AddCheatSheetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCheatsheetCommand
     * and returns an AddCheatsheetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCheatSheetCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCheatSheetCommand.MESSAGE_USAGE));
        }

        Title title = CheatsheetParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Set<Content> contentList = new HashSet<>();

        CheatSheet cheatsheet = new CheatSheet(title, contentList, tagList);

        return new AddCheatSheetCommand(cheatsheet);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

